/*
 * Copyright (c) 2023. Myndigheten för tillgängliga medier (MTM)
 */

package se.mtm.LibrisToElasticParser.elastic.conversion;

import se.mtm.LibrisToElasticParser.libris.model.*;
import se.mtm.LibrisToElasticParser.libris.model.Contributor.ContributorData;
import se.mtm.LibrisToElasticParser.libris.search.LibrisSearchParser;

import java.io.IOException;
import java.net.http.HttpResponse;

import static se.mtm.LibrisToElasticParser.http.HttpClient.httpGet;


/**
 * Creates Nota/Elasticbooks datasets from lists of libris items
 */
public class LibrisToElasticBooksConverter {

    /**
     * Creates an OPDS dataset from a list of Libris items
     */
//    public static OpdsDataSet createOpdsDataSet(List<Graph> librisItems) {
//
//        List<OpdsItem> opds = new ArrayList<>();
//        List<OpdsItem> opdsSingles = new ArrayList<>();
//        OpdsDataSet opdsDataSet = new OpdsDataSet(opds, opdsSingles);
//        for (Graph librisItem : librisItems) {
//            opds.add(CreateOpdsItem.opds(librisItem, OpdsType.opds));
//            opdsSingles.add(CreateOpdsItem.opds(librisItem, OpdsType.opds_single));
//        }
//        return opdsDataSet;
//    }

    //TODO: "Juvenile" shouldn't return "Children", but rather "Barn och Ungdom" or similar. Needs a do-over due to data.json, returns Adult every time now.
    public static String getTargetAudience(Graph item) {
    String targetAudience = "Adult";
//    if (item.instanceOf.intendedAudience != null) {
//        switch (item.instanceOf.intendedAudience.get(0).prefLabelByLang.en) {
//            case "Adult": targetAudience = "Adult";
//            break;
//            case "Juvenile": targetAudience = "Children";
//            break;
//        }
//    }
        return targetAudience;
    }

    public Data getOpdsItemByControlNumber(String i) throws IOException, InterruptedException {
        HttpResponse<String> response = httpGet(i);
        Data graphList = LibrisSearchParser.parseGraph(response.body());
        return graphList;
    }
    public static ContributorData getContributorDataByControlNumber(String i) throws IOException, InterruptedException {
        HttpResponse<String> response = httpGet(i);
        ContributorData graphList = LibrisSearchParser.parseContributor(response.body());
        return graphList;
    }

    //    public static String getFormat(Item item) {
//
//        String format = "";
//        if (Objects.equals(item.type, "Electronic") && Objects.equals(item.instanceOf.type, "Multimedia"))
//        {
//            format = "Talbok med text";
//        }
//        else if (Objects.equals(item.instanceOf.type, "Audio"))
//        {
//            format = "Talbok";
//        }
//        else if (Objects.equals(item.type, "Electronic") && item.instanceOf.type.equals("Text"))
//        {
//            format = "E-textbok";
//        }
//        else if (Objects.equals(item.type, "Tactile"))
//        {
//            format = "Punktskrift";
//        }
//        return format;
//    }
     public Contribution getContribution(String type, Graph librisItem) {
        Contribution contribution = new Contribution();
        if (librisItem.instanceOf.contribution != null) {
            for (Contribution c : librisItem.instanceOf.contribution) {
                if (c.role != null) {
                    for (Role r : c.role) {
                        if (r.prefLabelByLang != null && r.prefLabelByLang.en != null) {
                            if (r.prefLabelByLang.en.equals(type)) {
                                contribution = c;
                            }
                        }
                    }
                }
            }
        }
        return contribution;
    }
}
