
package se.mtm.LibrisToElasticParser.elastic.model.opds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Item {

    public Author author;
    public BelongsTo belongsTo;
    public String description;
    public String identifier;
    public String language;
    public String modified;
    public String published;
    public Publisher publisher;
    public List<Subject> subject;
    public String title;
    public String subtitle;
    public Translator translator;
    @Field(name = "@type")
    public String type;
    @Field(name = "x-added-year")
    public Long xAddedYear;
    @Field(name = "x-book-web-href")
    public String xBookWebHref;
    @Field(name = "x-cover-href")
    public String xCoverHref;
    @Field(name = "x-edition")
    public String xEdition;
    @Field(name = "x-entity-group-identifier")
    public String xEntityGroupIdentifier;
    @Field(name = "x-is-ebook")
    public Boolean xIsEbook;
    @Field(name = "x-is-audio-book")
    public Boolean xIsAudiobook;
    @Field(name = "x-has-sample")
    public Boolean xHasSample;
    @Field(name = "x-has-text")
    public Boolean xHasText;
    @Field(name = "x-isbn")
    public String xIsbn;
    @Field(name = "x-isbn10")
    public String xIsbn10;
    @Field(name = "x-isbn13")
    public String xIsbn13;
    @Field(name = "x-library-Id")
    public String xLibraryId;
    @Field(name = "x-media-category")
    public String xMediaCategory;
    @Field(name = "x-published")
    public String xPublished;
    @Field(name = "x-target-audience")
    public String xTargetAudience;
    @Field(name = "x-title-item-Id")
    public Long xTitleItemId;
}
