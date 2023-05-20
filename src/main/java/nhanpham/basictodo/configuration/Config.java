package nhanpham.basictodo.configuration;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class Config extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "todo-api-app";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create();
    }
}
