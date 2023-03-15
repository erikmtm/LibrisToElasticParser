
package se.mtm.LibrisToElasticParser.libris.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DescriptionLanguage {

    @SerializedName("@id")
    private String id;

    public String getId() {
        return id;
    }

    public static class Builder {

        private String id;

        public DescriptionLanguage.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public DescriptionLanguage build() {
            DescriptionLanguage descriptionLanguage = new DescriptionLanguage();
            descriptionLanguage.id = id;
            return descriptionLanguage;
        }

    }

}
