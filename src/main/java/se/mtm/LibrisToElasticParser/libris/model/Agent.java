
package se.mtm.LibrisToElasticParser.libris.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Agent {

    @SerializedName("@id")
    @Expose
    public String id;
    @SerializedName("@type")
    @Expose
    public String type;
    @SerializedName("familyName")
    @Expose
    public String familyName;
    @SerializedName("givenName")
    @Expose
    public String givenName;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("label")
    @Expose
    public List<String> label;
}
