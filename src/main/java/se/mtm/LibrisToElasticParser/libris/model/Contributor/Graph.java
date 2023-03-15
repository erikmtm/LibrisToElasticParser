
package se.mtm.LibrisToElasticParser.libris.model.Contributor;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import se.mtm.LibrisToElasticParser.libris.model.Contributor.TEST.IdentifiedBy;
import se.mtm.LibrisToElasticParser.libris.model.Contributor.TEST.Nationality;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Graph {

    @Expose
    public List<String> cataloguersNote;
    @Expose
    public String controlNumber;
    @Expose
    public String created;
    @Expose
    public List<DescriptionConvention> descriptionConventions;
    @Expose
    public String generationDate;
    @SerializedName("@id")
    public String id;
//    @Expose
//    public MainEntity mainEntity;
    @Expose
    public String modified;
    @Expose
    public String recordStatus;
    @Expose
    public List<SameA> sameAs;
    @SerializedName("@type")
    public String type;
    @Expose
    public List<String> description;
    @Expose
    public String familyName;
    @Expose
    public String givenName;
    @Expose
    public String name;
    @Expose
    public List<IdentifiedBy> identifiedBy;
    @Expose
    public String lifeSpan;
}
