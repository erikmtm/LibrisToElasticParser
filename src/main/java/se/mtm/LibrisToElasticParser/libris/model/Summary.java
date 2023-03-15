
package se.mtm.LibrisToElasticParser.libris.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Summary {

    @SerializedName("@type")
    @Expose
    public String type;
    @SerializedName("label")
    @Expose
    public List<String> label;
}
