
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Author {
    @Id
    public String Id;
    public String identifier;
    public List<String> Ids;
    public List<Link> links;
    public String name;
    public String Type;
}
