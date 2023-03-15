
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Suggest {

    @Field(name = "Title")
    private Title mTitle;

    public Title getTitle() {
        return mTitle;
    }

    public void setTitle(Title title) {
        mTitle = title;
    }

}
