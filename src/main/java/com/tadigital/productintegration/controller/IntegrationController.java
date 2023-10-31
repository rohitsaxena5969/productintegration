package com.tadigital.productintegration.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegrationController {

    @Value("${json.price.value.path}")
    private String priceValuePath;

    @Value("${json.price.currency.path}")
    private String priceCurrencyPath;

    @Value("${json.id.path}")
    private String idPath;

    @Value("${json.name.path}")
    private String namePath;

    @Value("${json.category.path}")
    private String categoryPath;

    @Value("${json.inStock.path}")
    private String inStockPath;

    @Value("${json.manufacturer.path}")
    private String manufacturerPath;

    @Value("${json.tags.path}")
    private String tagsPath;

    @RequestMapping("/")
    public String hello()
    {
        return "Hello User";
    }

    @PostMapping("/api/product")
    public ResponseEntity<String> createProduct(@RequestBody JsonNode productsNode) {

        for (JsonNode productNode : productsNode) {
            System.out.println("ID: " + readJsonValue(productNode, idPath));
            System.out.println("Name: " + readJsonValue(productNode, namePath));
            System.out.println("Category: " + readJsonValue(productNode, categoryPath));
            System.out.println("Price Value: " + readJsonValue(productNode, priceValuePath));
            System.out.println("Price Currency: " + readJsonValue(productNode, priceCurrencyPath));
            System.out.println("In Stock: " + readJsonValue(productNode, inStockPath));
            System.out.println("Manufacturer: " + readJsonValue(productNode, manufacturerPath));
            System.out.println("Tags: " + readJsonValue(productNode, tagsPath));
        }
        return ResponseEntity.ok("Products received: " + productsNode);
    }

    private JsonNode readJsonValue(JsonNode jsonNode, String jsonPath) {
        String[] pathElements = jsonPath.split("\\.");
        JsonNode tempNode = jsonNode;
        for (String element : pathElements) {
            tempNode = tempNode.get(element);
            if (tempNode == null) {
                break;
            }
        }
        return tempNode;
    }
}
