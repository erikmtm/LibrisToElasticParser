
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SiblingEntityGroup {

    @Field(name = "EntityGroupIdentifier")
    public String mEntityGroupIdentifier;
    @Field(name = "Languages")
    public List<String> mLanguages;
    @Field(name = "MediaCategories")
    public List<String> mMediaCategories;
}
