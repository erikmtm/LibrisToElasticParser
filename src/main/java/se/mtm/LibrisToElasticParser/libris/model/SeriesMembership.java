
package se.mtm.LibrisToElasticParser.libris.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SeriesMembership {

    @Expose
    public List<String> seriesStatement;
    @SerializedName("@type")
    public String type;
    @SerializedName("seriesEnumeration")
    public List<String> seriesEnumeration;
    @SerializedName("inSeries")
    public InSeries inSeries;

}
