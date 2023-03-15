
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Translator {

    @Field(name = "identifier")
    public String identifier;
    @Field(name = "links")
    public List<Link> links;
    @Field(name = "name")
    public String name;
}
