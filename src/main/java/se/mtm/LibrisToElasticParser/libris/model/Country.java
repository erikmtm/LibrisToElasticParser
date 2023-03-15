
package se.mtm.LibrisToElasticParser.libris.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Country {

    @SerializedName("@id")
    private String id;

    public String getId() {
        return id;
    }

    public static class Builder {

        private String id;

        public Country.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Country build() {
            Country country = new Country();
            country.id = id;
            return country;
        }

    }

}
