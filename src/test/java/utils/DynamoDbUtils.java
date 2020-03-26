package utils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnConsumedCapacity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class DynamoDbUtils {

    public final Logger LOG = LoggerFactory.getLogger(DynamoDbUtils.class);
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    DynamoDB dynamoDB = new DynamoDB(client);

    public Item getDynamoDataByKey(String tableName, String keyName, String keyValue) {


        try {
            LOG.info("Dynamo table : " + tableName);
            LOG.info("Dynamo key - value : " + keyName + " - " + keyValue);
            Table table = dynamoDB.getTable(tableName);
            Item dynamoData = table.getItem(keyName, keyValue);
            LOG.info("Current dynamo data : " + dynamoData.toJSONPretty());
            return dynamoData;
        } catch (Exception e) {
            LOG.error("Get Dynamo data error : " + e);
            return null;
        }
    }

    public Item getDynamoDataByAnyKeyValue(String tableName, String tableIndexName, String keyName, String keyValue) {
        Item lastDynamoIndex = null;
        try {
            LOG.info("Dynamo table : " + tableName);
            LOG.info("Dynamo key - value : " + keyName + " - " + keyValue);
            Table table = dynamoDB.getTable(tableName);
            QuerySpec querySpec = new QuerySpec()
                    .withConsistentRead(false)
                    .withScanIndexForward(true)
                    .withReturnConsumedCapacity(ReturnConsumedCapacity.TOTAL);

            Index index = table.getIndex(tableIndexName);

            querySpec.withKeyConditionExpression(keyName + " = :v_custid")
                    .withValueMap(new ValueMap()
                            .withString(":v_custid", keyValue));

            ItemCollection<QueryOutcome> items = index.query(querySpec);
            Iterator<Item> iterator = items.iterator();

            while (iterator.hasNext()) {
                lastDynamoIndex = iterator.next();
            }
            LOG.info("Dynamo Data : " + lastDynamoIndex);
            return lastDynamoIndex;
        } catch (Exception e) {
            LOG.error("Get Dynamo data error : " + e);
            return null;
        }

    }
}
