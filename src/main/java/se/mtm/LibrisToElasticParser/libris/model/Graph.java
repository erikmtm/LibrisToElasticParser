
package se.mtm.LibrisToElasticParser.libris.model;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import se.mtm.LibrisToElasticParser.libris.util.Isbn;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Graph {

    @SerializedName("seriesMembership")
    @Expose
    public List<SeriesMembership> seriesMembership;

    @SerializedName("@id")
    @Expose
    public String id;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("@type")
    @Expose
    public List<String> type;
    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("identifiedBy")
    @Expose
    public List<IdentifiedBy> identifiedBy;
    @SerializedName("hasTitle")
    @Expose
    public List<HasTitle> hasTitle;
    @SerializedName("instanceOf")
    @Expose
    public InstanceOf instanceOf;
    @SerializedName("responsibilityStatement")
    @Expose
    public String responsibilityStatement;
    @SerializedName("created")
    @Expose
    public String created;
    @SerializedName("modified")
    @Expose
    public String modified;
    @SerializedName("hasNote")
    @Expose
    public List<HasNote> hasNote;
    @SerializedName("@graph")
    @Expose
    public List<Graph> graph;
    @SerializedName("summary")
    @Expose
    public List<Summary> summary;
    @SerializedName("descriptionLastModifier")
    @Expose
    public DescriptionLastModifier descriptionLastModifier;
    @SerializedName("familyName")
    @Expose
    public String familyName;
    @SerializedName("givenName")
    @Expose
    public String givenName;
    @SerializedName("name")
    @Expose
    public String name;

    /**
     * Gets isbn from hasNote list in libris item
     */
    public String getParsedIsbn() {
        Isbn isbnClass = new Isbn();
        String isbn = null;

        if (hasNote != null) {

            for (HasNote note : hasNote) {
                if (note.label != null) {
                    var rawText = note.label.toString();
                    var parsedIsbn = isbnClass.extractFrom(rawText);

                    if (parsedIsbn != null) {
                        isbn = parsedIsbn;
                        continue;
                    }
                }
            }
        }

        return isbn;
    }
}
