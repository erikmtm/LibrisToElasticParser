package se.mtm.LibrisToElasticParser.elastic.conversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.mtm.LibrisToElasticParser.controllers.ElasticCrudController;
import se.mtm.LibrisToElasticParser.elastic.model.taxonomy.Taxonomy;
import se.mtm.LibrisToElasticParser.libris.model.*;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Author;
import se.mtm.LibrisToElasticParser.libris.model.Contributor.ContributorData;
import se.mtm.LibrisToElasticParser.service.ElasticOperationsImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConvertAuthor {
    private static final Logger logger = LogManager.getLogger(ConvertAuthor.class);

    public List<Author> getAuthor(Graph item) throws IOException, InterruptedException {
        List<Author> authors = getContributors(item);
        return authors;
    }

    private static List<Author> getContributors(Graph item) throws IOException, InterruptedException {
        List<Author> authors = new ArrayList<>();
        if (item.instanceOf.contribution == null) {
            logger.info("Saknar Contributors: " + item.id);
        } else {
            for (Contribution c : item.instanceOf.contribution) {
                Author author = new Author();
                if (c != null) {
                    if (c.type == "PrimaryContribution") {
                        if (c.agent.get(0).id != null) {
                            author.Id = getControlNumberFromId(c.agent.get(0).id);
                            if (c.agent.get(0).familyName == null && c.agent.get(0).givenName == null && c.agent.get(0).name == null) {
                                ContributorData primaryContributorData = LibrisToElasticBooksConverter.getContributorDataByControlNumber(removeITFromId(c.agent.get(0).id) + "/data.jsonld");
                                author.name = primaryContributorData.graph.get(1).givenName + " " + primaryContributorData.graph.get(1).familyName;
                                if (primaryContributorData.graph.get(1).givenName == null && primaryContributorData.graph.get(1).familyName == null) {
                                    author.name = primaryContributorData.graph.get(1).name;
                                }
                            }
                        } else {
                            author.name = c.agent.get(0).givenName + " " + c.agent.get(0).familyName;
                            if (c.agent.get(0).givenName == null && c.agent.get(0).familyName == null) {
                                author.name = c.agent.get(0).name;
                            }
                        }
                        if (author.Id == null) {
                                author.Id = java.util.UUID.randomUUID().toString();
                        }
                        author.identifier = "merkur:author:" + author.Id;
                        List<String> ids = new ArrayList<>();
                        ids.add(author.Id);
                        ids.add(author.identifier);
                        author.Ids = ids;
                        author.Type = getTypeFromRoleId(c.role.get(0).id);
                    } else {
                        if (c.agent == null) {
                            logger.info("Här saknas det en agent!!!::: " + item.identifiedBy.get(0).value);
                        }
                        if (c.agent != null) {
                            if (c.agent.get(0).id != null) {
                                author.Id = getControlNumberFromId(c.agent.get(0).id);
                                if (c.agent.get(0).familyName == null && c.agent.get(0).givenName == null && c.agent.get(0).name == null) {
                                    ContributorData primaryContributorData = LibrisToElasticBooksConverter.getContributorDataByControlNumber(removeITFromId(c.agent.get(0).id) + "/data.jsonld");
                                    author.name = primaryContributorData.graph.get(1).givenName + " " + primaryContributorData.graph.get(1).familyName;
                                    if (primaryContributorData.graph.get(1).givenName == null && primaryContributorData.graph.get(1).familyName == null) {
                                        author.name = primaryContributorData.graph.get(1).name;
                                    }
                                }
                            } else {
                                author.name = c.agent.get(0).givenName + " " + c.agent.get(0).familyName;
                                if (c.agent.get(0).givenName == null && c.agent.get(0).familyName == null) {
                                    author.name = c.agent.get(0).name;
                                }
                            }
                        }
                        if (author.Id == null) {
                                author.Id = java.util.UUID.randomUUID().toString();
                        }
                        author.identifier = "merkur:author:" + author.Id;
                        List<String> ids = new ArrayList<>();
                        ids.add(author.Id);
                        ids.add(author.identifier);
                        author.Ids = ids;
                        if (c.role != null) {
                            if (c.role.get(0).id != null) {
                                author.Type = getTypeFromRoleId(c.role.get(0).id);
                            } else logger.info("Role missing ID in:" + item.id);
                        } else {
                            logger.info("Role missing in: " + item.id);
                        }
                    }
                }
                authors.add(author);
            }
        }
        return authors;
    }

    private static String getTypeFromRoleId(String id) {
        String removeHttp = id.replace("https://id.kb.se/relator/", "");
        return removeHttp;
    }


    private static String getControlNumberFromId(String id) {
        String removeHttp = id.replace("https://libris.kb.se/", "");
        String controlNumber = removeHttp.replace("#it", "");
        return controlNumber;
    }
    private static String removeITFromId(String id) {
        String controlNumber = id.replace("#it", "");
        return controlNumber;
    }

    static List<String> createRel() {
        List<String> rel = new ArrayList<>();
        rel.add("https://20.166.250.209:5001/docs/rel/search-result");
        rel.add("search");
        return rel;
    }

    public static String getAuthorName(Graph item) {
        String name = null;
        String editor = null;
        if (item.instanceOf.contribution != null) {
            for (Contribution c : item.instanceOf.contribution) {
                if (c.type.contains("PrimaryContribution") && c.agent != null) {
                    Agent a = c.agent.get(0);
                        if (a.givenName != null && a.familyName != null) {
                            name = a.givenName + " " + a.familyName;
                        } else if (a.name != null) {
                            name = a.name;
                        } else if (a.givenName == null && a.familyName == null && a.name == null) {
                            name = item.responsibilityStatement;
                        }
                }
                if (c.type.contains("Contribution") && c.agent != null) {
                        if (c.role != null) {
                            Role r = c.role.get(0); //Måste alltid get:a 0, finns aldrig mer än en?
                            Agent a = c.agent.get(0);
                            if (r.prefLabelByLang != null && r.prefLabelByLang.en.contains("Editor")) {
                                if (a.givenName != null && a.familyName != null) {
                                    editor = a.givenName + " " + a.familyName;
                                } else if (a.name != null) {
                                    editor = a.name;
                                } else if (a.givenName == null && a.familyName == null && a.name == null) {
                                    editor = item.responsibilityStatement;
                                }
                            }
                        }
                }
            }
        }
        if (name == null && editor != null) {
            name = "Red. " + editor;
        }
        if (name == null) {
            name = "Namn Saknas";
        }
        return name;
    }
}
