package com.tadigital.productintegration.service;

import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.product.*;
import com.commercetools.api.models.product_type.ProductTypeResourceIdentifierBuilder;
import com.commercetools.sync.commons.exceptions.SyncException;
import com.commercetools.sync.products.ProductSync;
import com.commercetools.sync.products.ProductSyncOptions;
import com.commercetools.sync.products.ProductSyncOptionsBuilder;
import com.commercetools.sync.products.helpers.ProductSyncStatistics;
import com.fasterxml.jackson.databind.JsonNode;
import com.tadigital.productintegration.client.CommercetoolsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Service
public class ProductSyncService {

    @Autowired
    private CommercetoolsClient commercetoolsClient;

    public void syncProduct(JsonNode productNode) {

        final ProductVariantDraft productVariantDraft = ProductVariantDraftBuilder.of().key(UUID.randomUUID().toString())
                .sku(UUID.randomUUID().toString()).build();

        final ProductDraft productDraft =
                ProductDraftBuilder
                        .of()
                        .productType(ProductTypeResourceIdentifierBuilder.of().key("main").build())
                        .name(LocalizedString.ofEnglish("test_product_name"))
                        .slug(LocalizedString.ofEnglish(UUID.randomUUID().toString()))
                        .masterVariant(productVariantDraft)
                        .key(UUID.randomUUID().toString())
                        .build();

        final Logger logger = LoggerFactory.getLogger(ProductSync.class);
        final ProductSyncOptions productSyncOptions = ProductSyncOptionsBuilder
                .of(commercetoolsClient.getApiRoot())
                .errorCallback((syncException, draft, productProjection, updateActions) ->
                        logger.error(String.valueOf(new SyncException("My customized message")), syncException)).build();

        logger.info("Syncing product...");
        final ProductSync productSync = new ProductSync(productSyncOptions);
        CompletionStage<ProductSyncStatistics> syncStatisticsStage = productSync.sync(Collections.singletonList(productDraft));

        logger.info(syncStatisticsStage.toString());
    }
}
