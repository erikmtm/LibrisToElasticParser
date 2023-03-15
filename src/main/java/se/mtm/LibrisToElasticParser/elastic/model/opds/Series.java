
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import org.springframework.data.elasticsearch.annotations.Field;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Series {

    @Field(name = "identifier")
    public String mIdentifier;
    @Field(name = "links")
    public List<Link> mLinks;
    @Field(name = "name")
    public String name;
    @Field(name = "position")
    public Long mPosition;
    @Field(name = "x-of-total")
    public Long mXOfTotal;
}
