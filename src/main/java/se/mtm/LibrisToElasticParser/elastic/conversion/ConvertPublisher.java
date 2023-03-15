package se.mtm.LibrisToElasticParser.elastic.conversion;

import se.mtm.LibrisToElasticParser.libris.model.Contribution;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Item;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Link;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Properties;
import se.mtm.LibrisToElasticParser.elastic.model.opds.Publisher;

import java.util.ArrayList;
import java.util.List;

public class ConvertPublisher {

    Publisher getPublisher(List<String> rel, Properties properties, Item notaItemItem) {
        Publisher publisher = new Publisher();
        publisher.name = "Publisher missing";
        publisher.identifier = "merkur:publisher:" + publisher.name;

        List<Link> pubLinks = new ArrayList<>();
        Link pubLink = new Link();
        pubLink.href = "/opds2/search/merkur%3Apublisher%3A" + publisher.name + "/info.json";
        properties.mXIdentifier = "merkur:publisher-info:" + publisher.name;
        pubLink.properties = properties;
        pubLink.rel = rel;
        pubLink.title = publisher.name;
        pubLink.type = "application/opds\\u002Bjson";
        pubLinks.add(pubLink);

        publisher.links = pubLinks;

        return publisher;
    }
}
