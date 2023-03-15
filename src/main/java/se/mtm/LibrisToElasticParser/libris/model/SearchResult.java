
package se.mtm.LibrisToElasticParser.libris.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class SearchResult {

 /*   @SerializedName("@type")
    @Expose
    public String type;
    @SerializedName("@id")
    @Expose
    public String id;
    @SerializedName("itemOffset")
    @Expose
    public Integer itemOffset;
    @SerializedName("itemsPerPage")
    @Expose
    public Integer itemsPerPage;
    @SerializedName("totalItems")
    @Expose
    public Integer totalItems;
    @SerializedName("first")
    @Expose
    public First first;
    @SerializedName("last")
    @Expose
    public Last last; */
    @SerializedName("next")
    @Expose
    public Next next;
    @SerializedName("items")
    @Expose
    public List<Item> items = null;
/*    @SerializedName("maxItems")
    @Expose
    public String maxItems;
    @SerializedName("@context")
    @Expose
    public String context;*/
}
