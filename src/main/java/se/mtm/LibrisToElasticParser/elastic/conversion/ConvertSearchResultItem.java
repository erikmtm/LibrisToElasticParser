package se.mtm.LibrisToElasticParser.elastic.conversion;

import se.mtm.LibrisToElasticParser.libris.model.Graph;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Author;
import se.mtm.LibrisToElasticParser.elastic.model.opds.OpdsItem;
import se.mtm.LibrisToElasticParser.elastic.model.opds.SearchResultItem;

import java.util.Objects;

import static se.mtm.LibrisToElasticParser.elastic.conversion.LibrisToElasticBooksConverter.*;

public class ConvertSearchResultItem {

    public static SearchResultItem getSearchResultItem(Graph item, OpdsItem opdsItem, String parsedIsbn, String libraryId, Author author, String language, String format) {
        SearchResultItem searchResultItem = new SearchResultItem();

        searchResultItem.author = author;
        searchResultItem.title = opdsItem.Title;
        searchResultItem.subtitle = opdsItem.Subtitle;
        searchResultItem.identifier = "merkur:libraryid:" + libraryId;
        searchResultItem.xBookWebHref = "https://nota.dk/bibliotek/bogid/1728740";

        if (parsedIsbn != null) {
            searchResultItem.xCoverHref = "https://www.syndetics.com/index.aspx?isbn=" + parsedIsbn + "/lC.JPG&client=Talboks&type=L12";
        } else { // TODO: Improve error handling when placeholder cover has been added. Ie: point to placeholder instead.
            searchResultItem.xCoverHref = "https://www.syndetics.com/index.aspx?isbn=" + "00000" + "/lC.JPG&client=Talboks&type=L12";
        }

        searchResultItem.xPublished = opdsItem.PublishedDate;
        searchResultItem.xHasSample = true;
        if (Objects.equals(format, "E-textbok")) {searchResultItem.xIsEbook = true;}
        if (Objects.equals(format, "Talbok med text")) {
            searchResultItem.xIsAudiobook = true;
            searchResultItem.xHasText = true;
        }
        if (format == "Talbok") {
            searchResultItem.xIsAudiobook = true;
        }
        searchResultItem.xLibraryId = libraryId;
        searchResultItem.xTargetAudience = getTargetAudience(item);
        searchResultItem.language = language;
        searchResultItem.type = "http://schema.org/Book";
        searchResultItem.modified = opdsItem.LastUpdate;
        searchResultItem.xEntityGroupIdentifier = opdsItem.EntityGroupIdentifier;
        return searchResultItem;
    }
}
