package org.handnotes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://root:example@0.0.0.0:27017/handnotes?authSource=admin")); // to change
    }

}
