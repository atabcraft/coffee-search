package com.example.search.coffee.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import static java.util.Arrays.asList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.transport.Netty4Plugin;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.scheduling.annotation.Async;

@Configuration
public class ElasticsearchConfig implements DisposableBean {

    private Node node;

   
    private static class MyNode extends Node {
    public MyNode(Settings preparedSettings, Collection<Class<? extends Plugin>> classpathPlugins) {
        super(InternalSettingsPreparer.prepareEnvironment(preparedSettings, null), classpathPlugins);
       }
    }
    
    
    public Settings elasticsearchSettings(){
        return Settings.builder()
                .put("transport.type", "netty4")
                .put("http.type", "netty4")
                .put("http.enabled", "true")
                .put("path.home", "target/elasticsearch")
                .put("path.logs", "target/elasticsearch/logs")
                .put("path.data", "target/elasticsearch/data")
                .put("cluster.name", "elastictest")
                .build();
    }
 
    @Bean
    public Client client() {
        Settings settings = elasticsearchSettings();
        try {
            
            TransportClient client = new PreBuiltTransportClient(elasticsearchSettings());
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
            return client;

        } catch (UnknownHostException ex) {
            Logger.getLogger(ElasticsearchConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
        return null;
    }
 
    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(
                
                client());
    }
    
    
    @Bean
    @Async
    public Node elasticSearchTestNode() throws NodeValidationException {
        Node node = new MyNode(
                elasticsearchSettings(),
                asList(Netty4Plugin.class));
        this.node = node; 
        node.start();
        return node;
    }
    
    @Override
    public void destroy() throws Exception {
        node.close();
    }
    
    
}