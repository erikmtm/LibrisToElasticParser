
package se.mtm.LibrisToElasticParser.libris.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class InSeries {

    @Expose
    public List<IdentifiedBy> identifiedBy;
    @Expose
    public InSeries inSeries;
    @Expose
    public InstanceOf instanceOf;
    @SerializedName("@type")
    public String type;
}
