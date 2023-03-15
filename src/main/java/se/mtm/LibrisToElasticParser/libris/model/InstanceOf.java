
package se.mtm.LibrisToElasticParser.libris.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class InstanceOf {

    @Expose
    public List<Classification> classification;
    @Expose
    public List<Contribution> contribution;
    @Expose
    public List<GenreForm> genreForm;
    @Expose
    public List<IntendedAudience> intendedAudience;
    @Expose
    public List<IdentifiedBy> identifiedBy;
    @Expose
    public List<Language> language;
    @SerializedName("hasTitle")
    @Expose
    public List<HasTitle> hasTitle = null;
    @Expose
    public List<Subject> subject;
    @SerializedName("@type")
    public String type;
}
