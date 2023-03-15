package se.mtm.LibrisToElasticParser.elastic.conversion;

import se.mtm.LibrisToElasticParser.libris.model.Graph;
import se.mtm.LibrisToElasticParser.libris.model.Contribution;
import se.mtm.LibrisToElasticParser.libris.model.Role;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Translator;

import java.util.ArrayList;
import java.util.List;

public class ConvertTranslator {

    static List<Translator> getTranslators(Graph item) {
        final List<Translator> translators = new ArrayList<>() {};
        if (item.instanceOf.contribution != null) {
            for (Contribution c : item.instanceOf.contribution) {
                if (c.type.equals("Contribution")) {
                    if (c.role != null) {
                        Role r = c.role.get(0);
                        if (r != null && r.id != null && r.id.contains("translator")) {
                            Translator translator = new Translator();
                            if (c.agent.get(0).givenName != null && c.agent.get(0).familyName != null) {
                                translator.name = c.agent.get(0).givenName + " " + c.agent.get(0).familyName;
                            } else if (c.agent.get(0).name != null) {
                                translator.name = c.agent.get(0).name;
                            } else {
                                translator.name = "Name missing";
                            }

                            if (c.agent.get(0).meta != null && c.agent.get(0).meta.controlNumber != null) {
                                translator.identifier = "merkur:translator:" + c.agent.get(0).meta.controlNumber;
                            } else {
                                translator.identifier = "404";
                            }

                            // translator.setLinks();
                            translators.add(translator);
                        }
                    }
                }

            }
        }
        return translators;
    }
}
