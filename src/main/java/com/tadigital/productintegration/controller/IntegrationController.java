package com.tadigital.productintegration.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.tadigital.productintegration.service.ProductSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegrationController {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationController.class);

    @Autowired
    private ProductSyncService productSyncService;

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

        logger.info("Received products: {}", productsNode.toString());

        for (JsonNode productNode : productsNode) {
            logger.info("ID: {}", readJsonValue(productNode, idPath));
            logger.info("Name: {}", readJsonValue(productNode, namePath));
            logger.info("Category: {}", readJsonValue(productNode, categoryPath));
            logger.info("Price Value: {}", readJsonValue(productNode, priceValuePath));
            logger.info("Price Currency: {}", readJsonValue(productNode, priceCurrencyPath));
            logger.info("In Stock: {}", readJsonValue(productNode, inStockPath));
            logger.info("Manufacturer: {}", readJsonValue(productNode, manufacturerPath));
            logger.info("Tags: {}", readJsonValue(productNode, tagsPath));

            productSyncService.syncProduct(productNode);
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
