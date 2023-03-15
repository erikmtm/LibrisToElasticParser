
package se.mtm.LibrisToElasticParser.elastic.model.taxonomy;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "taxonomy-test", createIndex = true)
@Setting(settingPath = "/settings/taxonomy-1.0.0.json")
@Mapping(mappingPath = "/mappings/taxonomy-1.0.0.json")
public class Taxonomy {

    @Field(name = "DocCount")
    public Long docCount;
    @Id
    @Field(name = "Id")
    public String id;
    @Field(name = "ScoreBoostSum")
    public Double scoreBoostSum;
    @Field(name = "Suggest")
    public Suggest suggest;
    @Field(name = "Value")
    public String value;
    @Field(name = "Vocab")
    public String vocab;

    public Taxonomy(Long docCount, String id, Double scoreBoostSum, Suggest suggest, String value, String vocab) {
        this.docCount = docCount;
        this.id = id;
        this.scoreBoostSum = scoreBoostSum;
        this.suggest = suggest;
        this.value = value;
        this.vocab = vocab;
    }

    public Taxonomy() {

    }
}
