package se.mtm.LibrisToElasticParser.elastic.conversion;

import se.mtm.LibrisToElasticParser.libris.model.Graph;
import se.mtm.LibrisToElasticParser.libris.model.Contribution;
import se.mtm.LibrisToElasticParser.libris.model.Role;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Narrator;

import java.util.ArrayList;
import java.util.List;

public class ConvertNarrator {

    static List<Narrator> getNarrators(Graph item) {
        final List<Narrator> narrators = new ArrayList<>() {};
        if (item.instanceOf.contribution != null) {
            for (Contribution c : item.instanceOf.contribution) {
                if (c.type.equals("Contribution")) {
                    if (c.role != null) {
                        Role r = c.role.get(0);
                        if (r != null && r.id != null && r.id.contains("narrator")) {
                            Narrator narrator = new Narrator();
                            if (c.agent != null) {
                                if (c.agent.get(0).givenName != null && c.agent.get(0).familyName != null) {
                                    narrator.name = c.agent.get(0).givenName + " " + c.agent.get(0).familyName;
                                } else if (c.agent.get(0).name != null) {
                                    narrator.name = c.agent.get(0).name;
                                } else {
                                    narrator.name = "Name missing";
                                }
                                if (c.agent.get(0).meta != null && c.agent.get(0).meta.controlNumber != null) {
                                    narrator.Id = c.agent.get(0).meta.controlNumber;
                                } else {
                                    narrator.Id = "404";
                                }
                            }
                            narrator.ids  = new ArrayList<>() {{
                                add(narrator.Id);
                                add("merkur:narrator:" + narrator.Id);
                            }};
                            narrator.identifier = "merkur:narrator:" + narrator.Id;
                            narrator.type = "narrator";
                            narrators.add(narrator);
                        }
                    }

                }
            }
        }
        return narrators;
    }
}
