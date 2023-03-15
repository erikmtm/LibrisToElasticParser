package se.mtm.LibrisToElasticParser.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import se.mtm.LibrisToElasticParser.elastic.model.opds.OpdsItem;

@Repository
public interface ElasticRepository extends ElasticsearchRepository<OpdsItem, String> {
    boolean existsByControlnumber(String c);
}
