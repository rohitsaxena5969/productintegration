package com.tadigital.productintegration.client;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.sync.commons.utils.ClientConfigurationUtils;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;
import io.vrap.rmf.base.client.oauth2.ClientCredentialsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommercetoolsClient {

    private static final Logger logger = LoggerFactory.getLogger(CommercetoolsClient.class);
    private final ProjectApiRoot apiRoot;

    public CommercetoolsClient(
            @Value("${commercetools.clientId}") String clientId,
            @Value("${commercetools.clientSecret}") String clientSecret,
            @Value("${commercetools.scopes}") String scopes,
            @Value("${commercetools.projectKey}") String projectKey,
            @Value("${commercetools.authUrl}") String authUrl,
            @Value("${commercetools.apiUrl}") String apiUrl
    ) {
        logger.info("Initializing Commercetools Client...");
        final ClientCredentials clientCredentials = new ClientCredentialsBuilder()
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .withScopes(scopes)
                .build();
        this.apiRoot = ClientConfigurationUtils.createClient(projectKey, clientCredentials, authUrl, apiUrl);
        logger.info("Commercetools Client initialized.");
    }
}
