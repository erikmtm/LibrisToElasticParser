
package se.mtm.LibrisToElasticParser.libris.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Publication {

    @Expose
    public List<Agent> agent;
    @Expose
    public List<Country> country;
    @Expose
    public List<Place> place;
    @SerializedName("@type")
    public String type;
    @Expose
    public String year;
}
