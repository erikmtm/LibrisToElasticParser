package se.mtm.LibrisToElasticParser.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import se.mtm.LibrisToElasticParser.controllers.requestModel.DateTime;
import se.mtm.LibrisToElasticParser.elastic.conversion.CreateOpdsItem;
import se.mtm.LibrisToElasticParser.elastic.conversion.LibrisToElasticBooksConverter;
import se.mtm.LibrisToElasticParser.elastic.conversion.OpdsType;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Author;
import se.mtm.LibrisToElasticParser.elastic.model.opds.OpdsItem;
import se.mtm.LibrisToElasticParser.elastic.model.opds.OpdsItemEntity;
import se.mtm.LibrisToElasticParser.elastic.model.taxonomy.Suggest;
import se.mtm.LibrisToElasticParser.elastic.model.taxonomy.Value;
import se.mtm.LibrisToElasticParser.elastic.model.taxonomy.Taxonomy;
import se.mtm.LibrisToElasticParser.libris.model.Data;
import se.mtm.LibrisToElasticParser.libris.search.LibrisSearchParser;
import se.mtm.LibrisToElasticParser.libris.search.LibrisXl;
import se.mtm.LibrisToElasticParser.media.DemoMediaNumbers;
import se.mtm.LibrisToElasticParser.repository.ElasticRepository;
import se.mtm.LibrisToElasticParser.repository.ElasticRepositoryEntity;
import se.mtm.LibrisToElasticParser.repository.ElasticRepositoryTaxonomy;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static se.mtm.LibrisToElasticParser.http.HttpClient.httpGet;

@Service
public class ElasticOperationsImpl implements ElasticOperations {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Autowired
    private ElasticRepository elasticRepository;
    @Autowired
    private ElasticRepositoryEntity elasticRepositoryEntity;
    @Autowired
    private ElasticRepositoryTaxonomy elasticRepositoryTaxonomy;

    private static final Logger logger = LogManager.getLogger(ElasticOperationsImpl.class);
    final BlockingQueue<String> controlNumbers = new LinkedBlockingDeque<>();
    final ExecutorService producers = Executors.newFixedThreadPool(10);
    final ExecutorService transfer = Executors.newFixedThreadPool(1);
    // final ExecutorService consumers = Executors.newFixedThreadPool(8);
    final ExecutorService consumersSingle = Executors.newFixedThreadPool(10);
    // final BlockingQueue<OpdsItemEntity> blockingQueue = new LinkedBlockingDeque<>(10);
    final BlockingQueue<OpdsItem> blockingQueueSingle = new LinkedBlockingDeque<>(10);
    final BlockingQueue<List<OpdsItem>> elasticQueueSingle = new LinkedBlockingDeque<>(10);
    List<String> controlNumberslist = new ArrayList<>();
    List<String> missingControlNumberslist = new ArrayList<>();

    public ElasticOperationsImpl(List<String> controlNumberslist) {
        this.controlNumberslist = controlNumberslist;
    }


    @Override
    public OpdsItem create(OpdsItem opdsItem) {
        return elasticsearchOperations.save(opdsItem);
    }

    @Override
    public OpdsItem retrieve(String id) {
        return elasticsearchOperations.get(id, OpdsItem.class);
    }

    @Override
    public String delete(String id) {
        elasticRepository.deleteById(id); //Ska köra sökning på Id, mot repot? Äh, funktionen behövs kanske inte ändå.
        return "Done";
    }


    /*
    addAll starts ten producers that parses data.jsonld files based on the controlnumbers in controlNumbers
    The produces OpdsItems and OpdsItemEntitys are added to blockingQueue and blockingQueueSingle respectively.
    When the queues are full the ExecutorService transfer will drain them and send the results to the respective consumer,
    where the repsoitory bulk saves them to elastic.
    */
    @Override
    public List<OpdsItem> addAll() throws IOException, InterruptedException {
        controlNumberslist = LibrisXl.getAllMtmPublications();
        System.out.println("Antal i controlNumberslist: " + controlNumberslist.size());
        controlNumbers.addAll(controlNumberslist);
        System.out.println("Antal i controlNumbers: " + controlNumbers.size());
            for (int i = 0; i < 10; i++) {
                producers.execute(new producerThread());
            }
            for (int i = 0; i < 10; i++) {
                consumersSingle.execute(new consumerThread());
            }
            transfer.execute(new transferThread());
            producers.shutdown();
            transfer.shutdown();
        return null;
    }

    private void checkAllExists() {
        for (String c : controlNumberslist) {
            if (!elasticRepository.existsByControlnumber(c)) { missingControlNumberslist.add(c); }
        }
        logger.info("Det saknas " + missingControlNumberslist.size() + " stycken böcker.");
        for (String d : missingControlNumberslist) {
            logger.info("Denna bok saknas: " + d);
        }
    }

    private OpdsItemEntity createOpdsItemEntity(OpdsItem opdsItemSingle) {
        OpdsItemEntity opdsItemEntity = new OpdsItemEntity();
        opdsItemEntity.Id = "717112" + "-" + opdsItemSingle.Id + "-sv";
        opdsItemEntity.AccessRights = opdsItemSingle.AccessRights;
        opdsItemEntity.AddedYear = opdsItemSingle.AddedYear;
        opdsItemEntity.AgeGroup = opdsItemSingle.AgeGroup;
        opdsItemEntity.Authors = opdsItemSingle.Authors;
        opdsItemEntity.author_sort = opdsItemSingle.author_sort;
        opdsItemEntity.AvailableFormats = opdsItemSingle.AvailableFormats;
        opdsItemEntity.Classification = opdsItemSingle.Classification;
        opdsItemEntity.ControlNumber = opdsItemSingle.controlnumber;
        opdsItemEntity.CreatedDate = opdsItemSingle.CreatedDate;
        opdsItemEntity.Description = opdsItemSingle.Description;
        opdsItemEntity.EntityGroupIdentifier = opdsItemEntity.Id;
        opdsItemEntity.HasSample = opdsItemSingle.HasSample;
        opdsItemEntity.Index = opdsItemSingle.Index;
        opdsItemEntity.Isbn = opdsItemSingle.Isbn;
        opdsItemEntity.IsNew = opdsItemSingle.IsNew;
        opdsItemEntity.IsPdf = opdsItemSingle.IsPdf;
        opdsItemEntity.IsPeriodical = opdsItemSingle.IsPeriodical;
        opdsItemEntity.LanguageCodes = opdsItemSingle.LanguageCodes;
        opdsItemEntity.LastUpdate = opdsItemSingle.LastUpdate;
        opdsItemEntity.LibraryIds = opdsItemSingle.LibraryIds;
        opdsItemEntity.LastMaxPopularityRank = opdsItemSingle.LastMaxPopularityRank;
        opdsItemEntity.MainLanguage = opdsItemSingle.MainLanguage;
        opdsItemEntity.MaterialCode = opdsItemSingle.MaterialCode;
        opdsItemEntity.MediaCategories = opdsItemSingle.MediaCategories;
        opdsItemEntity.OriginalPublishedYear = opdsItemSingle.OriginalPublishedYear;
        opdsItemEntity.ParentEntityGroup = opdsItemSingle.ParentEntityGroup;
        opdsItemEntity.PopularityRank =opdsItemSingle.PopularityRank;
        opdsItemEntity.PublicationCategories = opdsItemSingle.PublicationCategories;
        opdsItemEntity.PublishedDate = opdsItemSingle.PublishedDate;
        opdsItemEntity.PublishedYear = opdsItemSingle.PublishedYear;
        opdsItemEntity.Revision = opdsItemSingle.Revision;
        opdsItemEntity.score_boost = opdsItemSingle.score_boost;
        opdsItemEntity.SearchResultItem = opdsItemSingle.SearchResultItem;
        opdsItemEntity.SeriesIds = opdsItemSingle.SeriesIds;
        opdsItemEntity.SiblingEntityGroups = opdsItemSingle.SiblingEntityGroups;
        opdsItemEntity.Subjects = opdsItemSingle.Subjects;
        opdsItemEntity.SeriesPosition = opdsItemSingle.SeriesPosition;
        opdsItemEntity.SiblingEntityGroupsIsDirty = opdsItemSingle.SiblingEntityGroupsIsDirty;
        opdsItemEntity.Subtitle = opdsItemSingle.Subtitle;
        opdsItemEntity.TargetAudience = opdsItemSingle.TargetAudience;
        opdsItemEntity.TitleAuthor = opdsItemSingle.TitleAuthor;
        opdsItemEntity.TitleSort = opdsItemSingle.TitleSort;
        opdsItemEntity.Title = opdsItemSingle.Title;
        opdsItemEntity.UnderProduction = opdsItemSingle.UnderProduction;
        opdsItemEntity.Items = opdsItemSingle.Items;
        return opdsItemEntity;
    }

    public Data getOpdsItemByControlNumber(String i) throws IOException, InterruptedException {
        HttpResponse<String> response = httpGet(i);
        Data graphList = LibrisSearchParser.parseGraph(response.body());
        return graphList;
    }

    @Override
    public OpdsItem addOne(String id) throws IOException, InterruptedException {
        Data graphList = getOpdsItemByControlNumber(id);
        if (graphList != null) {
            OpdsItem opdsItem = CreateOpdsItem.opds(graphList, OpdsType.opds_single);
            System.out.println("opdsitem.Id = " + opdsItem.Id);
            List<OpdsItem> opdsItemList = new ArrayList<>();
            opdsItemList.add(opdsItem);
            elasticRepository.saveAll(opdsItemList);
        }
        return null;
    }

    private void createOrUpdateTaxonomy(OpdsItem opdsItem) {
        Boolean exists = false;
        if (opdsItem.Authors != null) {
            for (Author a : opdsItem.Authors) {
                if (a.Id != null) {
                    exists = existsById(a.Id);
                }
                if (exists) {
                    Taxonomy taxonomy = elasticRepositoryTaxonomy.findOneById(a.Id);
                    taxonomy.docCount = taxonomy.docCount + 1;
                    saveTaxonomy(taxonomy);
                }
                if (!exists && a.name != null) {
                    Taxonomy taxonomy = new Taxonomy(1L, a.Id, 0.0, suggestFor(a.name), a.name, a.Type);
                    saveTaxonomy(taxonomy);
                }
            }
        }
    }

    private static Suggest suggestFor(String name) {
        List<String> suggest = new ArrayList<>();
        suggest.add(name);
        String[] strings = name.split(" ");
        for (String word : strings) {
            suggest.add(word);
        }
        Value value = new Value(suggest);
        Suggest suggestObj = new Suggest();
        suggestObj.value = value;
        return suggestObj;
    }

    @Override
    public void addFromDate(DateTime dateTime) {

    }

    @Override
    public void demo() throws IOException, InterruptedException {
        List<String> controlNumberslist = LibrisXl.getMtmPublicationsFromMediaNumbers(DemoMediaNumbers.get());
        for (String controlnumber : controlNumberslist) {
            Data graphList = getOpdsItemByControlNumber(controlnumber);
            OpdsItem opdsItemSingle = CreateOpdsItem.opds(graphList, OpdsType.opds_single);
            createOrUpdateTaxonomy(opdsItemSingle);
            OpdsItemEntity opdsItemEntity = createOpdsItemEntity(opdsItemSingle);
            elasticRepository.save(opdsItemSingle);
            elasticRepositoryEntity.save(opdsItemEntity);
        }
    }

    @Override
    public Boolean existsById(String id) {
        Boolean exists = elasticRepositoryTaxonomy.existsById(id);
        return exists;
    }

    @Override
    public void saveTaxonomy(Taxonomy taxonomy) {
        elasticRepositoryTaxonomy.save(taxonomy);
    }

    @Override
    public Taxonomy findByValue(String name) {
        return elasticRepositoryTaxonomy.findByValue(name);
    }

    public class producerThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(20);
                    String controlnumber = controlNumbers.take();
                    OpdsItem opdsItemSingle = CreateOpdsItem.opds(getOpdsItemByControlNumber(controlnumber), OpdsType.opds_single);
                    // OpdsItemEntity opdsItemEntity = createOpdsItemEntity(opdsItemSingle);
                    opdsItemSingle.controlnumber = controlnumber;
                    // blockingQueue.put(opdsItemEntity);
                    blockingQueueSingle.put(opdsItemSingle);
                    createOrUpdateTaxonomy(opdsItemSingle);
                } catch (Exception ex) {
                    logger.error("Fel i producer: " + ex);
                }
            }
        }
    }

    public class transferThread implements Runnable {
        List<OpdsItem> opdsSingleList = new ArrayList<>();
        @Override
        public void run() {
            while (!controlNumbers.isEmpty()) {
                try {
                    Thread.sleep(100);
                    if (blockingQueueSingle.remainingCapacity() == 0) {
                        opdsSingleList.clear();
                        blockingQueueSingle.drainTo(opdsSingleList);
                        elasticQueueSingle.add(opdsSingleList);
                        System.out.println("....");
                        System.out.println("Batch of " + opdsSingleList.size() + " uploaded to elastic, " + controlNumbers.size() + " remaining.");
                        System.out.println("....");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            checkAllExists();
        }
    }
    public class consumerThread implements Runnable {

        @Override
        public void run() {
            while(true) {
                try {
                    System.out.println("New batch uploading in thread " + Thread.currentThread().getId());
                    elasticRepository.saveAll(elasticQueueSingle.take());
                } catch (Exception e) {
                    logger.error("Bulk-save interrupted med fel: " + e);
                }
                if (elasticQueueSingle.isEmpty() && controlNumbers.isEmpty()) break;
            }
        }
    }
}