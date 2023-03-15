
package se.mtm.LibrisToElasticParser.libris.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Contribution {

    @Expose
    public List<Agent> agent;
    @Expose
    public List<Role> role;
    @SerializedName("@type")
    public String type;
}
