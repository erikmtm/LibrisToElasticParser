package se.mtm.LibrisToElasticParser.elastic.model.opds;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Taxonomy {

    @org.springframework.data.annotation.Id
    public String Id;
    public String Name;
    public String Type;
    public String Identifer;
    public List<String> Ids;

}
