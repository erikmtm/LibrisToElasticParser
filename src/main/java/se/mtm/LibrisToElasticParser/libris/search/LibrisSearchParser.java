/*
 * Copyright (c) 2023. Myndigheten för tillgängliga medier (MTM)
 */
package se.mtm.LibrisToElasticParser.libris.search;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import se.mtm.LibrisToElasticParser.libris.model.*;
import se.mtm.LibrisToElasticParser.libris.model.Contributor.ContributorData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses a search result from Libris XL
 */
public class LibrisSearchParser {

    /**
     * Parses a search result page in Json from Libris XL
     */

    //TODO: create custom deserializer to replace the individual deserializers.
    public static SearchResult parseSearchResult(String json) throws IllegalStateException {
        Type agentListType = new TypeToken<List<Agent>>() {}.getType();
        Type roleListType = new TypeToken<List<Role>>() {}.getType();
        Type countryListType = new TypeToken<List<Country>>() {}.getType();
        Type placeListType = new TypeToken<List<Place>>() {}.getType();
        Type stringListType = new TypeToken<List<String>>() {}.getType();
        Type translationOfListType = new TypeToken<List<TranslationOf>>() {}.getType();
        Type languageListType = new TypeToken<List<Language>>() {}.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(agentListType, new AgentDeserializer())
                .registerTypeAdapter(roleListType, new RoleDeserializer())
                .registerTypeAdapter(countryListType, new CountryDeserializer())
                .registerTypeAdapter(placeListType, new PlaceDeserializer())
                .registerTypeAdapter(stringListType, new StringDeserializer())
                .registerTypeAdapter(translationOfListType, new TranslationOfDeserializer())
                .registerTypeAdapter(languageListType, new LanguageDeserializer())
                .create();
            SearchResult searchResult = gson.fromJson(json, SearchResult.class);
        return searchResult;
    }

    public static Data parseGraph(String json) throws IllegalStateException {
        Type agentListType = new TypeToken<List<Agent>>() {}.getType();
        Type roleListType = new TypeToken<List<Role>>() {}.getType();
        Type countryListType = new TypeToken<List<Country>>() {}.getType();
        Type placeListType = new TypeToken<List<Place>>() {}.getType();
        Type stringListType = new TypeToken<List<String>>() {}.getType();
        Type translationOfListType = new TypeToken<List<TranslationOf>>() {}.getType();
        Type languageListType = new TypeToken<List<Language>>() {}.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(agentListType, new AgentDeserializer())
                .registerTypeAdapter(roleListType, new RoleDeserializer())
                .registerTypeAdapter(countryListType, new CountryDeserializer())
                .registerTypeAdapter(placeListType, new PlaceDeserializer())
                .registerTypeAdapter(stringListType, new StringDeserializer())
                .registerTypeAdapter(translationOfListType, new TranslationOfDeserializer())
                .registerTypeAdapter(languageListType, new LanguageDeserializer())
                .create();
        Data item = gson.fromJson(json, Data.class);
        return item;
    }
    public static ContributorData parseContributor(String json) throws IllegalStateException {
        Type agentListType = new TypeToken<List<Agent>>() {}.getType();
        Type roleListType = new TypeToken<List<Role>>() {}.getType();
        Type countryListType = new TypeToken<List<Country>>() {}.getType();
        Type placeListType = new TypeToken<List<Place>>() {}.getType();
        Type stringListType = new TypeToken<List<String>>() {}.getType();
        Type translationOfListType = new TypeToken<List<TranslationOf>>() {}.getType();
        Type languageListType = new TypeToken<List<Language>>() {}.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(agentListType, new AgentDeserializer())
                .registerTypeAdapter(roleListType, new RoleDeserializer())
                .registerTypeAdapter(countryListType, new CountryDeserializer())
                .registerTypeAdapter(placeListType, new PlaceDeserializer())
                .registerTypeAdapter(stringListType, new StringDeserializer())
                .registerTypeAdapter(translationOfListType, new TranslationOfDeserializer())
                .registerTypeAdapter(languageListType, new LanguageDeserializer())
                .create();
        ContributorData item = gson.fromJson(json, ContributorData.class);
        return item;
    }

    private static class AgentDeserializer implements JsonDeserializer<List<Agent>> {
        @Override
        public List<Agent> deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context)
                throws JsonParseException {
            List<Agent> vals = new ArrayList<Agent>();
            if (json.isJsonArray()) {
                for (JsonElement e : json.getAsJsonArray()) {
                    vals.add((Agent) context.deserialize(e, Agent.class));
                }
            } else if (json.isJsonObject()) {
                vals.add((Agent) context.deserialize(json, Agent.class));
            } else {
                throw new RuntimeException("Unexpected JSON type: " + json.getClass());
            }
            return vals;
        }
    }
    private static class RoleDeserializer implements JsonDeserializer<List<Role>> {
        @Override
        public List<Role> deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context)
                throws JsonParseException {
            List<Role> vals = new ArrayList<Role>();
            if (json.isJsonArray()) {
                for (JsonElement e : json.getAsJsonArray()) {
                    vals.add((Role) context.deserialize(e, Role.class));
                }
            } else if (json.isJsonObject()) {
                vals.add((Role) context.deserialize(json, Role.class));
            } else {
                throw new RuntimeException("Unexpected JSON type: " + json.getClass());
            }
            return vals;
        }
    }

    private static class CountryDeserializer implements JsonDeserializer<List<Country>> {
        @Override
        public List<Country> deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context)
                throws JsonParseException {
            List<Country> vals = new ArrayList<Country>();
            if (json.isJsonArray()) {
                for (JsonElement e : json.getAsJsonArray()) {
                    vals.add((Country) context.deserialize(e, Country.class));
                }
            } else if (json.isJsonObject()) {
                vals.add((Country) context.deserialize(json, Country.class));
            } else {
                throw new RuntimeException("Unexpected JSON type: " + json.getClass());
            }
            return vals;
        }
    }
    private static class PlaceDeserializer implements JsonDeserializer<List<Place>> {
        @Override
        public List<Place> deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context)
                throws JsonParseException {
            List<Place> vals = new ArrayList<Place>();
            if (json.isJsonArray()) {
                for (JsonElement e : json.getAsJsonArray()) {
                    vals.add((Place) context.deserialize(e, Place.class));
                }
            } else if (json.isJsonObject()) {
                vals.add((Place) context.deserialize(json, Place.class));
            } else {
                throw new RuntimeException("Unexpected JSON type: " + json.getClass());
            }
            return vals;
        }
    }
    private static class StringDeserializer implements JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context)
                throws JsonParseException {
            List<String> vals = new ArrayList<String>();
            if (json.isJsonArray()) {
                for (JsonElement e : json.getAsJsonArray()) {
                    vals.add((String) context.deserialize(e, String.class));
                }
            } else if (json.isJsonObject()) {
                vals.add((String) context.deserialize(json, String.class));
            } else if (json.isJsonPrimitive()) {
                vals.add((String) context.deserialize(json, String.class));
            }else {
                throw new RuntimeException("Unexpected JSON type: " + json.getClass());
            }
            return vals;
        }
    }

    private static class TranslationOfDeserializer implements JsonDeserializer<List<TranslationOf>> {
        @Override
        public List<TranslationOf> deserialize(JsonElement json, Type typeOfT,
                                          JsonDeserializationContext context)
                throws JsonParseException {
            List<TranslationOf> vals = new ArrayList<TranslationOf>();
            if (json.isJsonArray()) {
                for (JsonElement e : json.getAsJsonArray()) {
                    vals.add((TranslationOf) context.deserialize(e, TranslationOf.class));
                }
            } else if (json.isJsonObject()) {
                vals.add((TranslationOf) context.deserialize(json, TranslationOf.class));
            } else {
                throw new RuntimeException("Unexpected JSON type: " + json.getClass());
            }
            return vals;
        }
    }

    private static class LanguageDeserializer implements JsonDeserializer<List<Language>> {
        @Override
        public List<Language> deserialize(JsonElement json, Type typeOfT,
                                               JsonDeserializationContext context)
                throws JsonParseException {
            List<Language> vals = new ArrayList<Language>();
            if (json.isJsonArray()) {
                for (JsonElement e : json.getAsJsonArray()) {
                    vals.add((Language) context.deserialize(e, Language.class));
                }
            } else if (json.isJsonObject()) {
                vals.add((Language) context.deserialize(json, Language.class));
            } else {
                throw new RuntimeException("Unexpected JSON type: " + json.getClass());
            }
            return vals;
        }
    }

}
