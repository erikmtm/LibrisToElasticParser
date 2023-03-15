
package se.mtm.LibrisToElasticParser.libris.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Meta {

    @SerializedName("@id")
    @Expose
    public String id;
    @SerializedName("@type")
    @Expose
    public String type;
    @SerializedName("controlNumber")
    @Expose
    public String controlNumber;
    @SerializedName("created")
    @Expose
    public String created;
    @SerializedName("modified")
    @Expose
    public String modified;
    @SerializedName("descriptionLastModifier")
    @Expose
    public DescriptionLastModifier descriptionLastModifier;
    @SerializedName("encodingLevel")
    @Expose
    public String encodingLevel;
}
