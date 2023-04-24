/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.carbon.identity.api.server.application.management.v1.core.functions.application;

import org.apache.commons.collections.CollectionUtils;
import org.wso2.carbon.identity.api.server.application.management.v1.AdditionalSpProperty;
import org.wso2.carbon.identity.api.server.application.management.v1.AdvancedApplicationConfiguration;
import org.wso2.carbon.identity.api.server.application.management.v1.Certificate;
import org.wso2.carbon.identity.api.server.application.management.v1.ExternalizedConsentPageConfiguration;
import org.wso2.carbon.identity.api.server.application.management.v1.core.functions.UpdateFunction;
import org.wso2.carbon.identity.application.common.model.ExternalizedConsentPageConfig;
import org.wso2.carbon.identity.application.common.model.LocalAndOutboundAuthenticationConfig;
import org.wso2.carbon.identity.application.common.model.ServiceProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.wso2.carbon.identity.api.server.application.management.common.ApplicationManagementConstants.ErrorMessage.ADDITIONAL_SP_PROP_NOT_SUPPORTED;
import static org.wso2.carbon.identity.api.server.application.management.v1.core.functions.Utils.buildBadRequestError;
import static org.wso2.carbon.identity.api.server.application.management.v1.core.functions.Utils.setIfNotNull;

/**
 * Updates the advanced application configurations defined by the API model in the Service Provider model.
 */
public class UpdateAdvancedConfigurations implements UpdateFunction<ServiceProvider, AdvancedApplicationConfiguration> {

    public static final String TYPE_JWKS = "JWKS";
    public static final String TYPE_PEM = "PEM";

    @Override
    public void apply(ServiceProvider serviceProvider,
                      AdvancedApplicationConfiguration advancedConfigurations) {

        if (advancedConfigurations != null) {
            handleAdditionalSpProperties(advancedConfigurations.getAdditionalSpProperties());
            setIfNotNull(advancedConfigurations.getSaas(), serviceProvider::setSaasApp);
            setIfNotNull(advancedConfigurations.getDiscoverableByEndUsers(), serviceProvider::setDiscoverable);

            LocalAndOutboundAuthenticationConfig config = getLocalAndOutboundConfig(serviceProvider);
            setIfNotNull(advancedConfigurations.getSkipLoginConsent(), config::setSkipConsent);
            setIfNotNull(advancedConfigurations.getSkipLogoutConsent(), config::setSkipLogoutConsent);
            setIfNotNull(advancedConfigurations.getReturnAuthenticatedIdpList(),
                    config::setAlwaysSendBackAuthenticatedListOfIdPs);
            setIfNotNull(advancedConfigurations.getEnableAuthorization(), config::setEnableAuthorization);

            updateExternalizedConsentPage(advancedConfigurations.getExternalizedConsentPage(), config);
            updateCertificate(advancedConfigurations.getCertificate(), serviceProvider);
        }
    }

    private LocalAndOutboundAuthenticationConfig getLocalAndOutboundConfig(ServiceProvider application) {

        if (application.getLocalAndOutBoundAuthenticationConfig() == null) {
            application.setLocalAndOutBoundAuthenticationConfig(new LocalAndOutboundAuthenticationConfig());
        }

        return application.getLocalAndOutBoundAuthenticationConfig();
    }

    private void updateCertificate(Certificate certificate, ServiceProvider serviceProvider) {

        if (certificate != null) {
            if (TYPE_PEM.equals(certificate.getType())) {
                setIfNotNull(certificate.getValue(), serviceProvider::setCertificateContent);
                serviceProvider.setJwksUri(null);
            } else if (TYPE_JWKS.equals(certificate.getType())) {
                setIfNotNull(certificate.getValue(), serviceProvider::setJwksUri);
                serviceProvider.setCertificateContent(null);
            }
        }
    }

    private void updateExternalizedConsentPage(ExternalizedConsentPageConfiguration externalizedConsentPageApiModel,
                                                 LocalAndOutboundAuthenticationConfig config) {

        ExternalizedConsentPageConfig externalConsentManagementConfig = getExternalizedConsentPageConfig(config);
        if (externalizedConsentPageApiModel != null) {
            if (!externalizedConsentPageApiModel.getConsentPageUrl().isEmpty()) {
                validateConsentPageUrl(externalizedConsentPageApiModel.getConsentPageUrl());
            }
            setIfNotNull(externalizedConsentPageApiModel.getEnabled(), externalConsentManagementConfig::setEnabled);
            setIfNotNull(externalizedConsentPageApiModel.getConsentPageUrl(),
                    externalConsentManagementConfig::setConsentPageUrl);
        }
    }

    /**
     * Validate the external consent page URL.
     *
     * @param consentPageUrl Consent page URL.
     */
    private void validateConsentPageUrl(String consentPageUrl) {

        boolean isVaild = true;
        try {
            URL url = new URL(consentPageUrl);
            if (!url.getProtocol().equals("https")) {
                isVaild = false;
            }
        } catch (MalformedURLException e) {
            isVaild = false;
        }
        if (!isVaild) {
            throw buildBadRequestError("Invalid External Consent Page URL is found. Only https urls are " +
                    "allowed.");
        }
    }

    private ExternalizedConsentPageConfig getExternalizedConsentPageConfig (
            LocalAndOutboundAuthenticationConfig config) {

        if (config.getExternalizedConsentPageConfig() == null) {
            config.setExternalizedConsentPageConfig(new ExternalizedConsentPageConfig());
        }
        return config.getExternalizedConsentPageConfig();
    }


    private void handleAdditionalSpProperties(List<AdditionalSpProperty> spAdditionalProperties) {

        // `additionalSpProperties` not yet supported.
        if (!CollectionUtils.isEmpty(spAdditionalProperties)) {
            throw buildBadRequestError(ADDITIONAL_SP_PROP_NOT_SUPPORTED.getCode(),
                    ADDITIONAL_SP_PROP_NOT_SUPPORTED.getDescription());
        }
    }
}
