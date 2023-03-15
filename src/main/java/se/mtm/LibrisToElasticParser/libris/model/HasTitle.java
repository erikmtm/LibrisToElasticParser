
package se.mtm.LibrisToElasticParser.libris.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class HasTitle {

    @Expose
    public String mainTitle;
    @Expose
    public List<String> subtitle;
    @SerializedName("marc:nonfilingChars")
    public String marcNonfilingChars;
    @SerializedName("@type")
    public String type;

}
