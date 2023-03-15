
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Subject {

    @Field(name = "Id")
    public Long id;
    @Field(name = "Identifier")
    public String identifier;
    @Field(name = "Ids")
    public List<String> ids;
    @Field(name = "links")
    public List<Link> links;
    @Field(name = "name")
    public String name;
    @Field(name = "Type")
    public String type;
}
