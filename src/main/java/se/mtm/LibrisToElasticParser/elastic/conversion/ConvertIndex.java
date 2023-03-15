package se.mtm.LibrisToElasticParser.elastic.conversion;

import se.mtm.LibrisToElasticParser.libris.model.Graph;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Index;
import se.mtm.LibrisToElasticParser.elastic.model.opds.OpdsItem;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertIndex {
    static Index getIndex(OpdsItem opdsItem, Graph librisItem) {
        Index index = new Index();
        List<String> subjects = new ArrayList<>() {};
        String author = null;
        if (opdsItem.Authors.size() > 0) author = opdsItem.Authors.get(0).name;
        if (opdsItem.Subjects != null) {
            for (Subject s : opdsItem.Subjects) {
                subjects.add(s.name);
            }
        } else {
            subjects.add(author);
        }
        if (subjects.size() > 0) index.em = subjects;
        if (author != null) {
            List<String> authorSplit = List.of(author.split("\\s+"));
            index.fo = authorSplit;
            index.ne = Arrays.asList("b\\u00F8");
        } else {
            author = "Namn saknas";
            List<String> authorSplit = List.of(author.split("\\s+"));
            index.fo = authorSplit;
            index.ne = Arrays.asList("b\\u00F8");
        }
        return index;
    }
}
