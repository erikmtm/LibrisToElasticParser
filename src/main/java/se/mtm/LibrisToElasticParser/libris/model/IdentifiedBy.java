
package se.mtm.LibrisToElasticParser.libris.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class IdentifiedBy {

    @SerializedName("@type")
    @Expose
    public String type;

    @SerializedName("value")
    @Expose
    public String value;
}
