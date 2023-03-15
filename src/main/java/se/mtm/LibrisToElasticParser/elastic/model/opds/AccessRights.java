
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AccessRights {

    private List<String> DOWNLOAD;
    private Boolean FreeAccess;
    private List<String> PREVIEW;
    private List<String> VIEW;
    private List<String> SAMPLE;

    public static AccessRights getAccessRights(OpdsItem opdsItem) {
        AccessRights accessRights = new AccessRights();
        accessRights.FreeAccess = true;
        accessRights.DOWNLOAD = Arrays.asList("Sps", "TalkBkDo");

        accessRights.PREVIEW = Arrays.asList("TalkBkPreview");
        accessRights.VIEW = Arrays.asList("Anonymous", "AphasCd", "BrailleBk", "BrMusic", "Bux-members-admin", "EBookCd", "EBookDo", "Sps", "TalkBkCd", "TalkBkDo", "Internal");
        accessRights.SAMPLE = Arrays.asList("TalkBkPreview", "TalkBkDo");

        return accessRights;
    }

}
