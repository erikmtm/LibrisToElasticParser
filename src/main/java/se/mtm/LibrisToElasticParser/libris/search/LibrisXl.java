/*
 * Copyright (c) 2023. Myndigheten för tillgängliga medier (MTM)
 */
package se.mtm.LibrisToElasticParser.libris.search;

import se.mtm.LibrisToElasticParser.libris.model.Item;
import se.mtm.LibrisToElasticParser.libris.model.SearchResult;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static se.mtm.LibrisToElasticParser.http.HttpClient.httpGet;
import static se.mtm.LibrisToElasticParser.libris.search.LibrisSearchParser.parseSearchResult;


/**
 * Functionality for fetching publications from Libris using the LibrisXL API
 */
public class LibrisXl {

    private static final int pageSize = 200;
    private static final String librisBaseUrl = "https://libris.kb.se";
    private static final String mtmLibraryAndLimit = "@reverse.itemOf.heldBy.@id=https://libris.kb.se/library/Mtm&_limit=" + pageSize;

    /**
     * Gets all MTM publications in Libris (expect more than 160000 entries)
     */
    public static List<String> getAllMtmPublications() throws IOException, InterruptedException {
        int currentYear = Year.now().getValue();
        var publications = new ArrayList<String>();
        List<String> items = null;
        for (int year = 2023; year <= currentYear; year++) {
            items = LibrisXl.getMtmPublicationsForYear(year);
            publications.addAll(items);
        }
        return publications;
    }


    /**
     * Gets all MTM publications in Libris for a specific year. Returns a list of the items.
     */
    public static List<String> getMtmPublicationsForYear(int year) throws IOException, InterruptedException {

        String searchUrl = librisBaseUrl + "/find?" + mtmLibraryAndLimit + "&matches-meta.created=" + year;

        return getPublications(searchUrl);
    }

    public static List<String> getMtmPublicationsForModified(String date) throws IOException, InterruptedException {

        String searchUrl = librisBaseUrl + "/find?" + mtmLibraryAndLimit + "&min-meta.modified=" + date;
        return getPublications(searchUrl);
    }

    /**
     * Gets all publications using the provided search url as entry point. Will loop if search result contains a 'next'
     * url.
     */
    public static List<String> getPublications(String rootSearchUrl) throws IOException, InterruptedException {

        var next = rootSearchUrl;
        var ids = new ArrayList<String>();
        while (next != null) {
            HttpResponse<String> response = httpGet(next);
            SearchResult searchResult = parseSearchResult(response.body());
            if (searchResult.items != null) {
                for (Item i  : searchResult.items) {
                    ids.add(removeITFromId(i.id) + "/data.jsonld");
                }
            }
            if (searchResult.next != null) {
                next = librisBaseUrl + searchResult.next.id;
            } else {
                next = null;
            }
        }
        return ids;
    }

    private static String removeITFromId(String id) {
        String controlNumber = id.replace("#it", "");
        return controlNumber;
    }
    /**
     * Gets all MTM publications in Libris from a list of media numbers.
     */
    public static List<String> getMtmPublicationsFromMediaNumbers(List<String> mediaNumbers) throws IOException, InterruptedException {

        String orSeparatedMediaNumbers = String.join("%7C", mediaNumbers);

        String searchUrl = librisBaseUrl + "/find?q=" + orSeparatedMediaNumbers + "&" + mtmLibraryAndLimit;

        return getPublications(searchUrl);
    }
}
