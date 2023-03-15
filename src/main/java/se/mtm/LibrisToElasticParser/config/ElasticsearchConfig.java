package se.mtm.LibrisToElasticParser.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportOptions;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class ElasticsearchConfig {

    private final ElasticsearchProperties elasticsearchProperties;
    final CredentialsProvider credentialsProvider =
            new BasicCredentialsProvider();

    public ElasticsearchConfig(ElasticsearchProperties elasticsearchProperties) {
        this.elasticsearchProperties = elasticsearchProperties;
    }

    @Bean
    public RestClient elasticsearchClient() {
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword()));
        return RestClient.builder(new HttpHost("merkur-elastic-dev.es.northeurope.azure.elastic-cloud.com", 9243, "https"))
                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                            @Override
                            public HttpAsyncClientBuilder customizeHttpClient(
                                    HttpAsyncClientBuilder httpClientBuilder) {
                                return httpClientBuilder
                                        .setDefaultCredentialsProvider(credentialsProvider);
                            }
                        }).build();
//    String hostname = "merkur-elastic-dev.es.northeurope.azure.elastic-cloud.com";
//    String apiKeyId = "g72AFeZtTAyIuRJAbXbcyw";
//    String apiKeySecret = "aFFHVXlvWUJpeExsQUxIZWNqXzU6ZzcyQUZlWnRUQXlJdVJKQWJYYmN5dw==";
//    String apiKeyAuth =
//            Base64.getEncoder().encodeToString(
//                    (apiKeyId + ":" + apiKeySecret)
//                            .getBytes(StandardCharsets.UTF_8));
//
//
//    RestClientBuilder builder = RestClient.builder(
//                    new HttpHost(hostname, 9243, "https"))
//            .setRequestConfigCallback(
//                    new RestClientBuilder.RequestConfigCallback() {
//                        @Override
//                        public RequestConfig.Builder customizeRequestConfig(
//                                RequestConfig.Builder requestConfigBuilder) {
//                            return requestConfigBuilder
//                                    .setConnectTimeout(5000)
//                                    .setSocketTimeout(60000);
//                        }
//                    });
//
//
//    Header[] defaultHeaders =
//            new Header[]{new BasicHeader("Authorization",
//                    "ApiKey " + apiKeyAuth)};
//    builder.setDefaultHeaders(defaultHeaders);
//    return builder.build();
    }
    @Bean
    public ElasticsearchTransport elasticsearchTransport() {
        return new RestClientTransport(elasticsearchClient(), new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient getElasticsearchClient(){
        ElasticsearchClient client = new ElasticsearchClient(elasticsearchTransport());
        return client;
    }

}