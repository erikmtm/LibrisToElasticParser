package se.mtm.LibrisToElasticParser.elastic.conversion;

import se.mtm.LibrisToElasticParser.libris.model.Graph;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Subject;

import java.util.ArrayList;
import java.util.List;

public class ConvertSubject {
    //TODO: Hantera controlNumber när det är en sträng. Skapa upp nytt Id när controlNumber skapas? Alternativt behålla 404.
    static List<Subject> getSubjects(Graph librisItem) {
        List<Subject> subjects = new ArrayList<>() {};


        if (librisItem.instanceOf != null && librisItem.instanceOf.subject != null) {
            for (se.mtm.LibrisToElasticParser.libris.model.Subject s : librisItem.instanceOf.subject) {
                if (s != null && s.type != null && s.type.contains("Topic")) {
                    Subject subject = new Subject();
                    if (s.meta != null && isNumeric(s.meta.controlNumber)) {
                        subject.id = Long.valueOf(s.meta.controlNumber);
                    } else {
                        subject.id = 404L;
                    }
                    subject.name = s.prefLabel;
                    subject.type = "subject";
                    List<String> iDs = new ArrayList<>() {
                        {
                            add(String.valueOf(subject.id));
                            add("merkur:subject:" + subject.id);
                        }
                    };
                    subject.ids = iDs;
                    subject.identifier = "merkur:subject:" + subject.id;

                    subjects.add(subject);
                }
            }
        }
        return subjects;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
