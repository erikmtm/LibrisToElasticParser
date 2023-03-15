
package se.mtm.LibrisToElasticParser.libris.model.Contributor;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SourceConsulted {

    @Expose
    private String citationNote;
    @Expose
    private String label;
    @SerializedName("@type")
    private String type;

    public String getCitationNote() {
        return citationNote;
    }

    public void setCitationNote(String citationNote) {
        this.citationNote = citationNote;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
