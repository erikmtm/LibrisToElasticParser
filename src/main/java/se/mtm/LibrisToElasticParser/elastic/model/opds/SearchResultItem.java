
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SearchResultItem {

    @Field(name = "author")
    public Author author;
    @Field(name = "belongsTo")
    public BelongsTo belongsTo;
    @Field(name = "identifier")
    public String identifier;
    @Field(name = "language")
    public String language;
    @Field(name = "modified")
    public String modified;
    @Field(name = "title")
    public String title;
    @Field(name = "subtitle")
    public String subtitle;
    @Field(name = "@type")
    public String type;
    @Field(name = "x-book-web-href")
    public String xBookWebHref;
    @Field(name = "x-cover-href")
    public String xCoverHref;
    @Field(name = "x-entity-group-identifier")
    public String xEntityGroupIdentifier;
    @Field(name = "x-has-sample")
    public Boolean xHasSample;
    @Field(name = "x-is-ebook")
    public Boolean xIsEbook;
    @Field(name = "x-is-audio-book")
    public Boolean xIsAudiobook;
    @Field(name = "x-library-Id")
    public String xLibraryId;
    @Field(name = "x-published")
    public String xPublished;
    @Field(name = "x-target-audience")
    public String xTargetAudience;
    @Field(name = "x-has-text")
    public Boolean xHasText;

}
