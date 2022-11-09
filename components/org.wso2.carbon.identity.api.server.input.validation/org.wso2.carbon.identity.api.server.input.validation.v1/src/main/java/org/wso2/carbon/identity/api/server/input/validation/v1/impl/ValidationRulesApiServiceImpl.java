/*
 * Copyright (c) 2022, WSO2 LLC. (http://www.wso2.org).
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

package org.wso2.carbon.identity.api.server.input.validation.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.carbon.identity.api.server.input.validation.v1.ValidationRulesApiService;
import org.wso2.carbon.identity.api.server.input.validation.v1.core.ValidationRulesManagementApiService;
import org.wso2.carbon.identity.api.server.input.validation.v1.models.ValidationConfigModel;

import static org.wso2.carbon.identity.api.server.common.ContextLoader.getTenantDomainFromContext;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Implementation of ValidationRulesApiService.
 */
public class ValidationRulesApiServiceImpl implements ValidationRulesApiService {

    @Autowired
    private ValidationRulesManagementApiService validationRulesManagementApiService;

    @Override
    public Response getValidationRules() {

        String tenantDomain = getTenantDomainFromContext();
        return Response.ok().entity(validationRulesManagementApiService
                .getValidationConfiguration(tenantDomain)).build();
    }

    @Override
    public Response getValidators() {

        String tenantDomain = getTenantDomainFromContext();
        return Response.ok().entity(validationRulesManagementApiService
                .getValidators(tenantDomain)).build();
    }

    @Override
    public Response updateValidationRules(List<ValidationConfigModel> validationConfigModels) {

        String tenantDomain = getTenantDomainFromContext();
        return Response.ok().entity(validationRulesManagementApiService
                .updateInputValidationConfiguration(validationConfigModels, tenantDomain)).build();
    }
}
