
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Narrator {

    @Field(name = "id")
    public String Id;
    @Field(name = "identifier")
    public String identifier;
    @Field(name = "Ids")
    public List<String> ids;
    @Field(name = "name")
    public String name;
    @Field(name = "type")
    public String type;
}
