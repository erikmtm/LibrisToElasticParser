package se.mtm.LibrisToElasticParser.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.mtm.LibrisToElasticParser.controllers.requestModel.DateTime;
import se.mtm.LibrisToElasticParser.elastic.model.taxonomy.Taxonomy;
import se.mtm.LibrisToElasticParser.repository.ElasticRepositoryTaxonomy;
import se.mtm.LibrisToElasticParser.service.ElasticOperations;

import java.io.IOException;

@RestController
@RequestMapping("api/elastic")
public class ElasticCrudController {

    @Autowired
    private ElasticOperations elasticOperations;
    @Autowired
    private ElasticRepositoryTaxonomy elasticRepositoryTaxonomy;

    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable String id) {
        elasticOperations.delete(id);
        return "Done";
    }

    @PutMapping(value = "/create/{id}")
    public String create(@PathVariable String id) throws IOException, InterruptedException {
        elasticOperations.addOne("https://libris.kb.se/" + id + "/data.jsonld");
        return "Done";
    }

    @PutMapping(value = "/demo")
    public String demo() throws IOException, InterruptedException {
        elasticOperations.demo();
        return "Injection of all publications into Elastsearch finished.";
    }

    @PutMapping(value = "/all")
    public String all() throws IOException, InterruptedException {
        elasticOperations.addAll();
        return "Injection of all publications into Elastsearch finished.";
    }

    @PutMapping(value = "/modified")
    public String modified(@PathVariable DateTime dateTime) throws IOException, InterruptedException {
        elasticOperations.addFromDate(dateTime);
        return "Injection of all publications into Elastsearch finished.";
    }
}
