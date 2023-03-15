package se.mtm.LibrisToElasticParser.elastic.conversion;


import se.mtm.LibrisToElasticParser.elastic.model.opds.Properties;
import se.mtm.LibrisToElasticParser.elastic.model.opds.*;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Subject;
import se.mtm.LibrisToElasticParser.libris.model.*;

import java.io.IOException;
import java.util.*;

import static se.mtm.LibrisToElasticParser.elastic.conversion.ConvertAuthor.createRel;

public class CreateOpdsItem {
    static String language = null;
    static List<String> mainLanguage = new ArrayList<>(){};
    static String libraryId = null;
    static String parsedIsbn = null;
    static String format = null;
    static Boolean primaryContributor;
    static Boolean narratorContributor;
    static Boolean editorContributor;
    static Boolean translatorContributor;

    static String primaryAuthor;
    static String narrator;
    static String translator;
    static String editor;

    public static OpdsItem opds(Data librisItems, OpdsType type) throws IOException, InterruptedException {
        var opdsItem = new OpdsItem();
        for (Graph librisItem : librisItems.graph) {
            if (librisItem.created != null) {
                opdsItem.AddedYear = Long.valueOf(librisItem.created.substring(0, 4));
                opdsItem.CreatedDate = librisItem.created;
                opdsItem.LastUpdate = librisItem.modified;
                opdsItem.PublishedDate = librisItem.created;
            }
            break;
        }
        for (Graph librisItem : librisItems.graph) {
            if (librisItem.type != null) {
                if (Objects.deepEquals(librisItem.type.get(0), "Electronic") || Objects.deepEquals(librisItem.type.get(0), "Tactile") || Objects.deepEquals(librisItem.type.get(0), "SoundRecording") || Objects.deepEquals(librisItem.type.get(0), "Multimedia")) {//TODO: Använd rätt objekt för hämtningen, Multimedia och Audio behövs nog inte, ska undersökas.
                    parsedIsbn = librisItem.getParsedIsbn();
                    if (opdsItem.Isbn == null) opdsItem.Isbn = getIsbn(librisItem);
                    opdsItem.Classification = getClassification(librisItem);
                    libraryId = setIdsAndGetLibraryId(librisItem, type, opdsItem);
                    String finalLibraryId = libraryId;
                    format = getFormat(librisItem);
                    opdsItem.LibraryIds =
                            new ArrayList<>() {
                                {
                                    if (librisItem.identifiedBy != null) {
                                        add(finalLibraryId);
                                    }

                                    if (librisItem.identifiedBy != null) {
                                        add("merkur:libraryid:" + finalLibraryId);
                                    }
                                }
                            };
                    opdsItem.Title = getTitles(librisItem, "main");
                    opdsItem.Subtitle = getTitles(librisItem, "sub");
                    //TODO: Check that we get the different Contributors in a safe way. Add Editors also?
                    //opdsItem.Authors = ConvertAuthor.getAuthor(librisItem);
                    // opdsItem.narrators = ConvertNarrator.getNarrators(librisItem);
                    // opdsItem.translators = ConvertTranslator.getTranslators(librisItem);
                    ConvertAuthor convertAuthor = new ConvertAuthor();
                    opdsItem.Authors = convertAuthor.getAuthor(librisItem);
                    List<Taxonomy> taxonomyList = new ArrayList<>();
                    for (Author a : opdsItem.Authors) {
                        Taxonomy taxonomyItem = new Taxonomy();
                        if (Objects.equals(a.Type, "author")) {
                            opdsItem.author_sort = a.name;
                        }
                        taxonomyItem.Id = a.Id;
                        taxonomyItem.Name = a.name;
                        taxonomyItem.Type = a.Type;
                        taxonomyItem.Identifer = a.identifier;
                        taxonomyItem.Ids = a.Ids;
                        taxonomyList.add(taxonomyItem);
                    }
                    opdsItem.Taxonomy = taxonomyList;
                    opdsItem.AvailableFormats = getAvailableFormats(librisItem);
                    opdsItem.MediaCategories = getMediaCategories(librisItem);
                    setDescription(librisItem, opdsItem);
                    break;
                }
            }
        }
        for (Graph librisItem : librisItems.graph) {
            if (opdsItem.Title == null && librisItem.hasTitle != null && librisItem.hasTitle.get(0).mainTitle != null) {
                opdsItem.Title = getTitles(librisItem, "main");
                opdsItem.Subtitle = getTitles(librisItem, "sub");
                //TODO: Check part titles (deltitlar). Add to main or treat differently?
            }
            if (librisItem.graph != null) {
                for (Graph g : librisItem.graph) {
                    for (String t : g.type) {
                        if (Objects.equals(t, "Language")) {
                            opdsItem.LanguageCodes = getLanguageCodes(g);
                            opdsItem.MainLanguage = getLanguageCodes(g);
                        }
                    }
                }
            }
            opdsItem.PublicationCategories = new ArrayList<>() {
                {
                    add(fictionOrNonFiction(librisItem));
                }
            };

            opdsItem.AccessRights = AccessRights.getAccessRights(opdsItem);
            if (opdsItem.LanguageCodes != null && opdsItem.PublicationCategories != null && opdsItem.Title != null && !opdsItem.PublicationCategories.isEmpty() && !opdsItem.LanguageCodes.isEmpty()) break;
            // opdsItem.Subjects = ConvertSubject.getSubjects(librisItem);  //TODO: Fix Subjects to Data.json format, check with Mats how it's done now, perhaps?
        }
        for (Graph librisItem : librisItems.graph) {
            if (opdsItem.Authors != null) {
                opdsItem.Index = ConvertIndex.getIndex(opdsItem, librisItem);
                if (opdsItem.TitleAuthor == null && opdsItem.Authors.size() > 0) {
                    opdsItem.TitleAuthor = opdsItem.Title + " - " + opdsItem.author_sort;
                }
                Author searchAuthor = null;
                for(Author a: opdsItem.Authors) {
                    if (Objects.equals(a.Type, "author")) {
                        searchAuthor = a;
                        break;
                    }
                }
                opdsItem.SearchResultItem = ConvertSearchResultItem.getSearchResultItem(librisItem, opdsItem, parsedIsbn, libraryId, searchAuthor, language, format);
                Properties properties = new Properties();
                List<String> rel = createRel();
                Author itemAuthor = null;
                for(Author a: opdsItem.Authors) {
                    if (Objects.equals(a.Type, "author")) {
                        itemAuthor = a;
                        break;
                    }
                }
                opdsItem.Items = ConvertItem.getItem(librisItem, opdsItem, parsedIsbn, libraryId, itemAuthor, properties, rel, language, format);
                break;
            }

        }
        //TODO: Below hard-coded values should be checked if they need to be hard-coded, probably not in all cases. Revision is Nota-specific, probably not needed by us, check if we can remove it.
        opdsItem.Revision = 0L;
        opdsItem.score_boost = (double) 0;
        opdsItem.SiblingEntityGroupsIsDirty = false;
        opdsItem.HasSample = true;
        opdsItem.IsPdf = false;
        opdsItem.IsPeriodical = false;
        opdsItem.IsNew = true;
        opdsItem.LastMaxPopularityRank = 1L;
        opdsItem.SeriesIds = null; //TODO: Work on series, can be picked up from Libris notes?
        opdsItem.SeriesPosition = null;
        opdsItem.SiblingEntityGroups = null;
        opdsItem.ParentEntityGroup = null;
        opdsItem.PopularityRank = 1L;
        opdsItem.UnderProduction = false; //TODO: This can also be checked, if I'm not mistaken.
        opdsItem.PublishedYear = opdsItem.OriginalPublishedYear; //TODO: This can't be right, right?

        if (libraryId != null) {
            opdsItem.Id = libraryId;
        }

        opdsItem.AgeGroup = Collections.singletonList(11L);
        Subject subject = new Subject();
        subject.name = "Fantasy";
        List<Subject> subjects = new ArrayList<>() {};
        subjects.add(subject);
        opdsItem.Subjects = subjects;

        //TODO: "New" finns som alternativ för materialcode, vad annars? Vad används den till?
        opdsItem.MaterialCode = Collections.singletonList("New");

        if (opdsItem.TitleSort == null) {
            opdsItem.TitleSort = opdsItem.Title; //TODO: Should be different then just title?
        }
        return opdsItem;
    }

    private static List<String> getIsbn(Graph librisItem) {
        List<String> isbn = new ArrayList<>();
        if (librisItem.identifiedBy != null) {
            for (IdentifiedBy i : librisItem.identifiedBy) {
                if (i.type.equals("ISBN")) {
                    isbn.add(i.value);
                }
            }
        } else isbn.add("ISBN missing");
        return isbn;
    }

    public static List<String> getLanguageCodes(Graph g) {
        List<String> languageCodes = new ArrayList<>();
        if (Objects.equals(g.code, "swe")) {
            languageCodes.add("sv");
            languageCodes.add("swe");
            mainLanguage.add("sv");
            mainLanguage.add("swe");
            language = "sv";
        }
        if (Objects.equals(g.code, "eng")) {
            languageCodes.add("en");
            languageCodes.add("eng");
            if (mainLanguage.isEmpty()) {
                mainLanguage.add("en");
                mainLanguage.add("eng");
            }
                language = "en";
        }
        return languageCodes;
    }
    private static String fictionOrNonFiction(Graph item) {
        if (item.instanceOf != null) {
            if (item.instanceOf.genreForm != null) {
                for (GenreForm g : item.instanceOf.genreForm) {
                    if (g.prefLabelByLang != null && g.prefLabelByLang.en != null) {
                        if (g.prefLabelByLang.en.contains("Fiction")) {
                            return "Fiction";
                        } else if (g.prefLabelByLang.en.contains("Not fiction")) {
                            return "Non-fiction";
                        }
                    }
                }
            }
        }
        return "Fiction";
    }
    private static List<String> getMediaCategories(Graph librisItem) {
        List<String> mediaCategories = new ArrayList<>() {};
        if (getFormat(librisItem) == "E-textbok" || getFormat(librisItem).equals("Talbok med text"))
        {
            mediaCategories.add("Ebook");
        }
        if (getFormat(librisItem).equals("Talbok med text") || getFormat(librisItem).equals("Talbok"))
        {
            mediaCategories.add("Audio");
        }
        return mediaCategories;
    }

    private static List<String> getClassification (Graph item) {
        List<String> classifications = new ArrayList<>() {};
        if (item.instanceOf.classification != null) {
            for (Classification c : item.instanceOf.classification) {
                if (c.code != null) {
                    for (String s : c.code) {
                        classifications.add(s);
                    }
                }
            }
        }

        if (item.instanceOf.classification != null) {
            for (Classification c : item.instanceOf.classification) {
                if (c.code != null) {
                    for (String s : c.code) {
                        classifications.add(s);
                    }
                }
            }
        }
        return classifications;
    }

    private static String setIdsAndGetLibraryId(Graph item, OpdsType type, OpdsItem opdsItem) {

        var libraryId = " ";

        switch (type) {
            case opds:
                libraryId = handleOpdsIds(item, opdsItem, libraryId);
                break;

            case opds_single:
                libraryId = handleOpdsSingleIds(item, opdsItem, libraryId);
                break;
        }

        return libraryId;
    }

    private static String handleOpdsSingleIds(Graph item, OpdsItem opdsItem, String libraryId) {
        if (item.identifiedBy != null) {
            for (IdentifiedBy i : item.identifiedBy) {
                if (i.type.equals("Identifier")) {
                    libraryId = i.value;
                    opdsItem.Id = i.value;
                    opdsItem.EntityGroupIdentifier = "717112" + "-" + i.value + "-sv";
                }
            }
        }
        return libraryId;
    }

    private static String handleOpdsIds(Graph item, OpdsItem opdsItem, String libraryId) {
        if (item.identifiedBy != null) {
            for (IdentifiedBy i : item.identifiedBy) {
                if (i.type.equals("Identifier")) {
                    libraryId = i.value;
                    opdsItem.Id = "717112" + "-" + i.value + "-sv";
                    opdsItem.EntityGroupIdentifier = "717112" + "-" + i.value + "-sv";
                }
            }
        }
        return libraryId;
    }


    private static String getTitles(Graph librisItem, String mainOrSubtitle) {
        String title;
        if (librisItem.hasTitle != null) {
            title = null;
            switch (mainOrSubtitle) {
                case ("main") -> {
                    for (HasTitle i : librisItem.hasTitle) {
                        if (i.mainTitle != null && Objects.equals(i.type, "Title")) {
                            title = i.mainTitle;
                        }
                    }
                }
                case ("sub") -> {
                    for (HasTitle i : librisItem.hasTitle) {
                        if (i.subtitle != null) {
                            //TODO: flera subtitles? undersök om det finns några såna cases.
                            title = i.subtitle.get(0).toString();
                        } else {
                            title = null;
                        }
                    }
                }
            }
        } else {
            title = "Titel saknas";
        }
        return title;
    }

    private static List<String> getAvailableFormats(Graph item) {
        List<String> availableFormats = new ArrayList<>();
        if (Objects.equals(getFormat(item), "Talbok med text")) {
            availableFormats.add("AudioBook");
            availableFormats.add("WithText");
        }
        if (Objects.equals(getFormat(item), "Talbok")) {
            availableFormats.add("AudioBook");
            availableFormats.add("WithoutText");
        }
        if (Objects.equals(getFormat(item), "E-textbok")) {
            availableFormats.add("EBook");
            availableFormats.add("EBookSTD");
        }
        if (Objects.equals(getFormat(item), "Punktskrift")) {
            availableFormats.add("Braille");
        }
        // availableFormats.add("EPUB");
        // availableFormats.add("PDF");
        return availableFormats;
    }
    public static String getFormat(Graph item) {

        String format = "";
        if (Objects.equals(item.type, "Electronic") || Objects.equals(item.instanceOf.type, "Multimedia"))
        {
            format = "Talbok med text";
        }
        else if (Objects.equals(item.instanceOf.type, "Audio"))
        {
            format = "Talbok";
        }
        else if (Objects.equals(item.type, "Electronic") && item.instanceOf.type.equals("Text"))
        {
            format = "E-textbok";
        }
        else if (Objects.equals(item.type, "Tactile"))
        {
            format = "Punktskrift";
        }
        return format;
    }

    private static void setDescription(Graph librisItem, OpdsItem opdsItem) {
        if (librisItem.summary != null) {
            for (Summary i : librisItem.summary) {
                if (i.type.equals("Summary") && i.label != null) {
                    for (String s : i.label) {
                        if (opdsItem.Description != null) {
                            opdsItem.Description = opdsItem.Description + " " + s;
                        } else {
                            opdsItem.Description = s;
                        }
                    }
                }
            }
        }
    }
}
