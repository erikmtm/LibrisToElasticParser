package se.mtm.LibrisToElasticParser.elastic.conversion;

import se.mtm.LibrisToElasticParser.libris.model.Contribution;
import se.mtm.LibrisToElasticParser.libris.model.Graph;
import se.mtm.LibrisToElasticParser.libris.model.Language;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Author;
import se.mtm.LibrisToElasticParser.elastic.model.opds.OpdsItem;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Properties;

import java.util.Arrays;
import java.util.List;

import static se.mtm.LibrisToElasticParser.elastic.conversion.LibrisToElasticBooksConverter.getTargetAudience;

public class ConvertItem {

    static List<se.mtm.LibrisToElasticParser.elastic.model.opds.Item> getItem(Graph item, OpdsItem opdsItem, String parsedIsbn, String libraryId, Author author, Properties properties, List<String> rel, String language, String format) {
        se.mtm.LibrisToElasticParser.elastic.model.opds.Item elasticItem = new se.mtm.LibrisToElasticParser.elastic.model.opds.Item();
        ConvertPublisher convertPublisher = new ConvertPublisher();
        elasticItem.type = "http://schema.org/Book";

        elasticItem.title = opdsItem.Title;
        elasticItem.subtitle = opdsItem.Subtitle;

        //TODO: Fix getContribution with regards to using data.jsonld instead of full search.
        // Contribution publisher = librisToElasticBooksConverter.getContribution("Publisher", item);
        elasticItem.publisher = convertPublisher.getPublisher(rel, properties, elasticItem);



        elasticItem.description = opdsItem.Description;
        elasticItem.author = author;
        elasticItem.identifier = "merkur:libraryid:" + libraryId;
        elasticItem.language = language;
        elasticItem.modified = opdsItem.LastUpdate;
        elasticItem.published = opdsItem.PublishedDate;
        elasticItem.xEntityGroupIdentifier = opdsItem.EntityGroupIdentifier;
        elasticItem.xAddedYear = Long.parseLong(String.valueOf(opdsItem.AddedYear)); //TODO: When was it actually added? hard-coded for now
        elasticItem.xTitleItemId = 717112L; //TODO: This needs to be worked out and done properly.
        if (item.graph != null && item.graph.get(0).descriptionLastModifier != null) {
            elasticItem.xEdition = item.graph.get(0).descriptionLastModifier.name; // TODO: Should not be hardcoded.
        } else {
            elasticItem.xEdition = "2009";
        }
        elasticItem.xBookWebHref = "https://nota.dk/bibliotek/bogid/1728740"; //TODO: Again, not hard-coded.
        if (ConvertBelongsTo.getSeriesName(item) != null) { //TODO: Should this also be added to SearchResultsItem?
            elasticItem.belongsTo = ConvertBelongsTo.getBelongsto(item);
        }
        if (parsedIsbn != null) {
            elasticItem.xCoverHref = "https://www.syndetics.com/index.aspx?isbn=" + parsedIsbn + "/lC.JPG&client=Talboks&type=L12";
        } else { // TODO: Improve error handling when placeholder cover has been added. Ie: point to placeholder instead.
            elasticItem.xCoverHref = "https://www.syndetics.com/index.aspx?isbn=" + "00000" + "/lC.JPG&client=Talboks&type=L12";
        }
        String mediaFormat = "";
        if (format == "E-textbok") {
            elasticItem.xIsEbook = true;
            mediaFormat = "EBook";
        }
        if (format == "Talbok med text") {
            elasticItem.xIsAudiobook = true;
            elasticItem.xHasText  = true;
            mediaFormat = "Audio";
        }
        if (format == "Talbok") {
            elasticItem.xIsAudiobook  = true;
            mediaFormat = "Audio";
        }
        if (opdsItem.Isbn != null && !opdsItem.Isbn.isEmpty()) {
            elasticItem.xIsbn = opdsItem.Isbn.get(0); //TODO: Make sure this returns the correct isbn (or all?)
        } else {
            System.out.println("ISBN SAKNAS FÖR: " + opdsItem.Id);
        }
        elasticItem.xLibraryId = libraryId;
        elasticItem.xPublished = opdsItem.PublishedDate;
        elasticItem.xTargetAudience = getTargetAudience(item);

        elasticItem.xMediaCategory = mediaFormat;
        elasticItem.xHasSample  = true;
        return Arrays.asList(elasticItem);
    }

    public String getLanguage(Graph item) {
        if (item.instanceOf.language != null) {
            for (Language l : item.instanceOf.language) {
                if (l.code == "swe") {
                    return "sv";
                }
                if (l.code == "eng") {
                    return "en";
                }
            }
        }
        return null;
    }
}
