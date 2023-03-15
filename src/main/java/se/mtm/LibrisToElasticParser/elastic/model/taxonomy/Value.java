
package se.mtm.LibrisToElasticParser.elastic.model.taxonomy;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Value {

    @Expose
    private List<String> input;

    public Value(List<String> input) {
        this.input = input;
    }
}
