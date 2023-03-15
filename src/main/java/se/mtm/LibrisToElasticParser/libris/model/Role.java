
package se.mtm.LibrisToElasticParser.libris.model;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Role {

    @SerializedName("@type")
    @Expose
    public String type;
    @SerializedName("@id")
    @Expose
    public String id;
    @SerializedName("prefLabelByLang")
    @Expose
    public PrefLabelByLang prefLabelByLang;
    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("code")
    @Expose
    public String code;
}
