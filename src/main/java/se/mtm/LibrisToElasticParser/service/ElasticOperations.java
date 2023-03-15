package se.mtm.LibrisToElasticParser.service;

import se.mtm.LibrisToElasticParser.controllers.requestModel.DateTime;
import se.mtm.LibrisToElasticParser.elastic.model.opds.OpdsItem;
import se.mtm.LibrisToElasticParser.elastic.model.taxonomy.Taxonomy;

import java.io.IOException;
import java.util.List;

public interface ElasticOperations {
    OpdsItem create(OpdsItem opdsItem);

    OpdsItem retrieve(String id);

    String delete(String id);

    List<OpdsItem> addAll() throws IOException, InterruptedException;

    OpdsItem addOne(String id) throws IOException, InterruptedException;

    void addFromDate(DateTime dateTime);

    void demo() throws IOException, InterruptedException;

    Boolean existsById(String id);

    void saveTaxonomy(Taxonomy taxonomy);

    Taxonomy findByValue(String name);
}