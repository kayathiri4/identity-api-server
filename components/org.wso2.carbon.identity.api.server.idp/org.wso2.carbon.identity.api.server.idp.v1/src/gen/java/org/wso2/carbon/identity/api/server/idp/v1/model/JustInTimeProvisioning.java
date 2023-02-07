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

package org.wso2.carbon.identity.api.server.idp.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;


import io.swagger.annotations.*;
import java.util.Objects;
import javax.validation.Valid;
import javax.xml.bind.annotation.*;

public class JustInTimeProvisioning  {
  
    private Boolean isEnabled = false;

@XmlType(name="SchemeEnum")
@XmlEnum(String.class)
public enum SchemeEnum {

    @XmlEnumValue("PROMPT_USERNAME_PASSWORD_CONSENT") PROMPT_USERNAME_PASSWORD_CONSENT(String.valueOf("PROMPT_USERNAME_PASSWORD_CONSENT")), @XmlEnumValue("PROMPT_PASSWORD_CONSENT") PROMPT_PASSWORD_CONSENT(String.valueOf("PROMPT_PASSWORD_CONSENT")), @XmlEnumValue("PROMPT_CONSENT") PROMPT_CONSENT(String.valueOf("PROMPT_CONSENT")), @XmlEnumValue("PROVISION_SILENTLY") PROVISION_SILENTLY(String.valueOf("PROVISION_SILENTLY"));


    private String value;

    SchemeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static SchemeEnum fromValue(String value) {
        for (SchemeEnum b : SchemeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

    private SchemeEnum scheme = SchemeEnum.PROVISION_SILENTLY;
    private String userstore = "PRIMARY";
    private Boolean associateLocalUser = false;

@XmlType(name="SyncAttributeEnum")
@XmlEnum(String.class)
public enum SyncAttributeEnum {

    @XmlEnumValue("ALL") ALL(String.valueOf("ALL")), @XmlEnumValue("NONE") NONE(String.valueOf("NONE")), @XmlEnumValue("PRESERVE_LOCAL") PRESERVE_LOCAL(String.valueOf("PRESERVE_LOCAL"));


    private String value;

    SyncAttributeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static SyncAttributeEnum fromValue(String value) {
        for (SyncAttributeEnum b : SyncAttributeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}

    private SyncAttributeEnum syncAttribute = SyncAttributeEnum.ALL;

    /**
    **/
    public JustInTimeProvisioning isEnabled(Boolean isEnabled) {

        this.isEnabled = isEnabled;
        return this;
    }
    
    @ApiModelProperty(example = "true", required = true, value = "")
    @JsonProperty("isEnabled")
    @Valid
    @NotNull(message = "Property isEnabled cannot be null.")

    public Boolean getIsEnabled() {
        return isEnabled;
    }
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
    **/
    public JustInTimeProvisioning scheme(SchemeEnum scheme) {

        this.scheme = scheme;
        return this;
    }
    
    @ApiModelProperty(value = "")
    @JsonProperty("scheme")
    @Valid
    public SchemeEnum getScheme() {
        return scheme;
    }
    public void setScheme(SchemeEnum scheme) {
        this.scheme = scheme;
    }

    /**
    **/
    public JustInTimeProvisioning userstore(String userstore) {

        this.userstore = userstore;
        return this;
    }
    
    @ApiModelProperty(example = "PRIMARY", value = "")
    @JsonProperty("userstore")
    @Valid
    public String getUserstore() {
        return userstore;
    }
    public void setUserstore(String userstore) {
        this.userstore = userstore;
    }

    /**
    **/
    public JustInTimeProvisioning associateLocalUser(Boolean associateLocalUser) {

        this.associateLocalUser = associateLocalUser;
        return this;
    }
    
    @ApiModelProperty(example = "true", value = "")
    @JsonProperty("associateLocalUser")
    @Valid
    public Boolean getAssociateLocalUser() {
        return associateLocalUser;
    }
    public void setAssociateLocalUser(Boolean associateLocalUser) {
        this.associateLocalUser = associateLocalUser;
    }

    /**
    **/
    public JustInTimeProvisioning syncAttribute(SyncAttributeEnum syncAttribute) {

        this.syncAttribute = syncAttribute;
        return this;
    }
    
    @ApiModelProperty(value = "")
    @JsonProperty("syncAttribute")
    @Valid
    public SyncAttributeEnum getSyncAttribute() {
        return syncAttribute;
    }
    public void setSyncAttribute(SyncAttributeEnum syncAttribute) {
        this.syncAttribute = syncAttribute;
    }



    @Override
    public boolean equals(java.lang.Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JustInTimeProvisioning justInTimeProvisioning = (JustInTimeProvisioning) o;
        return Objects.equals(this.isEnabled, justInTimeProvisioning.isEnabled) &&
            Objects.equals(this.scheme, justInTimeProvisioning.scheme) &&
            Objects.equals(this.userstore, justInTimeProvisioning.userstore) &&
            Objects.equals(this.associateLocalUser, justInTimeProvisioning.associateLocalUser) &&
            Objects.equals(this.syncAttribute, justInTimeProvisioning.syncAttribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isEnabled, scheme, userstore, associateLocalUser, syncAttribute);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("class JustInTimeProvisioning {\n");
        
        sb.append("    isEnabled: ").append(toIndentedString(isEnabled)).append("\n");
        sb.append("    scheme: ").append(toIndentedString(scheme)).append("\n");
        sb.append("    userstore: ").append(toIndentedString(userstore)).append("\n");
        sb.append("    associateLocalUser: ").append(toIndentedString(associateLocalUser)).append("\n");
        sb.append("    syncAttribute: ").append(toIndentedString(syncAttribute)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
    * Convert the given object to string with each line indented by 4 spaces
    * (except the first line).
    */
    private String toIndentedString(java.lang.Object o) {

        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n");
    }
}

