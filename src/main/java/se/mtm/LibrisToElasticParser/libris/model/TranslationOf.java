
package se.mtm.LibrisToElasticParser.libris.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class TranslationOf {

    @SerializedName("@type")
    @Expose
    public String type;
    @SerializedName("language")
    @Expose
    public List<Language> language = null;
}
