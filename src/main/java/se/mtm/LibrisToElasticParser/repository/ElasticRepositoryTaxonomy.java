package se.mtm.LibrisToElasticParser.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import se.mtm.LibrisToElasticParser.elastic.model.taxonomy.Taxonomy;

@Repository
public interface ElasticRepositoryTaxonomy extends ElasticsearchRepository<Taxonomy, String> {
    Taxonomy findByValue(String name);
    Taxonomy findOneById(String id);
}
