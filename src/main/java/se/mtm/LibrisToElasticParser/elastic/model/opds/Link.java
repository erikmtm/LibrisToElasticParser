
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Link {

    @Field(name = "href")
    public String href;
    @Field(name = "properties")
    public Properties properties;
    @Field(name = "rel")
    public List<String> rel;
    @Field(name = "title")
    public String title;
    @Field(name = "type")
    public String type;
}
