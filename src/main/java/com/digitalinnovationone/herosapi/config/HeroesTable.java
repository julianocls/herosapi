package com.digitalinnovationone.herosapi.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.digitalinnovationone.herosapi.constrans.HeroesConstant;

import javax.security.auth.kerberos.KeyTab;
import java.util.Arrays;

public class HeroesTable {

    public static void main(String[] args) throws Exception {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(HeroesConstant.ENDPOINT_DYNAMO, HeroesConstant.REGION_DYNAMO))
                .build();

        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "Heroes_Table";

        try {
            System.out.println("Criando tabela, aguarde...");
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(new KeySchemaElement("id", KeyType.HASH)
                    ),

                    Arrays.asList(new AttributeDefinition("id", ScalarAttributeType.S)
                    ),

                    new ProvisionedThroughput(5L, 5L)
            );
            table.waitForActive();
            System.out.println("Successo " + table.getDescription().getTableStatus());

        } catch (Exception e) {
            System.err.println("Não foi possível criar a tabela");
            System.err.println(e.getMessage());
        }

    }

}

