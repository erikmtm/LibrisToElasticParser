package se.mtm.LibrisToElasticParser.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import se.mtm.LibrisToElasticParser.elastic.model.opds.OpdsItemEntity;

@Repository
public interface ElasticRepositoryEntity extends ElasticsearchRepository<OpdsItemEntity, String> {
}