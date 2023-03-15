
package se.mtm.LibrisToElasticParser.libris.model.Contributor.TEST;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Data {

    @Expose
    private List<String> description;
    @Expose
    private String familyName;
    @Expose
    private String givenName;
    @SerializedName("@id")
    private String id;
    @Expose
    private List<IdentifiedBy> identifiedBy;
    @Expose
    private String lifeSpan;
    @Expose
    private List<Nationality> nationality;
    @Expose
    private List<SameA> sameAs;
    @SerializedName("@type")
    private String type;

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<IdentifiedBy> getIdentifiedBy() {
        return identifiedBy;
    }

    public void setIdentifiedBy(List<IdentifiedBy> identifiedBy) {
        this.identifiedBy = identifiedBy;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public List<Nationality> getNationality() {
        return nationality;
    }

    public void setNationality(List<Nationality> nationality) {
        this.nationality = nationality;
    }

    public List<SameA> getSameAs() {
        return sameAs;
    }

    public void setSameAs(List<SameA> sameAs) {
        this.sameAs = sameAs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
