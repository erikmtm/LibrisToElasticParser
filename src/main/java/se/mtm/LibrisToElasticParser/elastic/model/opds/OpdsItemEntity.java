package se.mtm.LibrisToElasticParser.elastic.model.opds;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Document(indexName = "opds-test", createIndex = true)
@Setting(settingPath = "/settings/opdsSettings.json")
@Mapping(mappingPath = "/mappings/opds-1.0.0.json")
public class OpdsItemEntity {

    @Id
    @Field("Id")
    public String Id;
    @Field(name = "AccessRights")
    public AccessRights AccessRights;
    @Field(name = "AddedYear")
    public Long AddedYear;
    @Field(name = "AgeGroup")
    public List<Long> AgeGroup;
    @Field(name = "author_sort")
    public String author_sort;
    @Field(name = "Authors")
    public List<Author> Authors;
    @Field(name = "AvailableFormats")
    public List<String> AvailableFormats;
    @Field(name = "Classification")
    public List<String> Classification;
    @Field(name = "ControlNumber")
    public String ControlNumber;
    @Field(name = "CreatedDate")
    public String CreatedDate;
    @Field(name = "Description")
    public String Description;
    @Field(name = "EntityGroupIdentifier")
    public String EntityGroupIdentifier;
    @Field(name = "HasSample")
    public Boolean HasSample;
    @Field(name = "Index")
    public Index Index;
    @Field(name = "IsNew")
    public Boolean IsNew;
    @Field(name = "IsPdf")
    public Boolean IsPdf;
    @Field(name = "IsPeriodical")
    public Boolean IsPeriodical;
    @Field(name = "Isbn")
    public List<String> Isbn;
    @Field(name = "Items")
    public List<Item> Items;
    @Field(name = "LanguageCodes")
    public List<String> LanguageCodes;
    @Field(name = "LastMaxPopularityRank")
    public Long LastMaxPopularityRank;
    @Field(name = "LastUpdate")
    public String LastUpdate;
    @Field(name = "LibraryIds")
    public List<String> LibraryIds;
    @Field(name = "MainLanguage")
    public List<String> MainLanguage;
    @Field(name = "MaterialCode")
    public List<String> MaterialCode;
    @Field(name = "MediaCategories")
    public List<String> MediaCategories;
    @Field(name = "OriginalPublishedYear")
    public Long OriginalPublishedYear;
    @Field(name = "ParentEntityGroup")
    public String ParentEntityGroup;
    @Field(name = "PopularityRank")
    public Long PopularityRank;
    @Field(name = "PublicationCategories")
    public List<String> PublicationCategories;
    @Field(name = "PublishedDate")
    public String PublishedDate;
    @Field(name = "PublishedYear")
    public Long PublishedYear;
    @Field(name = "Revision")
    public Long Revision;
    @Field(name = "score_boost")
    public Double score_boost;
    @Field(name = "SearchResultItem")
    public SearchResultItem SearchResultItem;
    @Field(name = "SeriesIds")
    public List<String> SeriesIds;
    @Field(name = "SeriesPosition")
    public Long SeriesPosition;
    //    @Field(name = "SeriesTotalCount")
//    public Long SeriesTotalCount;
    @Field(name = "SiblingEntityGroups")
    public List<SiblingEntityGroup> SiblingEntityGroups;
    @Field(name = "SiblingEntityGroupsIsDirty")
    public Boolean SiblingEntityGroupsIsDirty;
    @Field(name = "Subjects")
    public List<Subject> Subjects;
    @Field(name = "Suggest")
    public Suggest Suggest;
    @Field(name = "TargetAudience")
    public String TargetAudience;
    @Field(name = "Title")
    public String Title;
    @Field(name = "Subtitle")
    public String Subtitle;
    @Field(name = "TitleAuthor")
    public String TitleAuthor;
    @Field(name = "TitleSort")
    public String TitleSort;
    @Field(name = "UnderProduction")
    public Boolean UnderProduction;
    @Field(name = "narrators")
    public List<Narrator> narrators;
    @Field(name = "Translators")
    public List<Translator> translators;
}
