
package se.mtm.LibrisToElasticParser.libris.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class HasNote {

    @Expose
    public List<String> label;
    @SerializedName("@type")
    public String type;
}
