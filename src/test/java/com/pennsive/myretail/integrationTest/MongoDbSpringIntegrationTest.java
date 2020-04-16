package com.pennsive.myretail.integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

@DataMongoTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
public class MongoDbSpringIntegrationTest {
    @DisplayName("given object to save"
        + " when save object using MongoDB template"
        + " then object is saved")
    @Test
    public void test(@Autowired MongoTemplate mongoTemplate) {
        DBObject objectToSave = BasicDBObjectBuilder.start()
            .add("key", "value")
            .get();
 
        mongoTemplate.save(objectToSave, "collection");
        
        List<DBObject> objects = mongoTemplate.findAll(DBObject.class, "collection");
        assertEquals(objects.size(), 1);
        assertEquals("value", objects.get(0).get("key"));
    }
}
