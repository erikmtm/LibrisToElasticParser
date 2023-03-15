package se.mtm.LibrisToElasticParser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "se.mtm.LibrisToElasticParser.repository")
@SpringBootApplication
public class LibrisToElasticParserApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibrisToElasticParserApplication.class, args);
	}

}
