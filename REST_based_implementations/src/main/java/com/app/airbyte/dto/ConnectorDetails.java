package com.app.airbyte.dto;

public class ConnectorDetails {

    public String definitionId;
    public String name;
    public Boolean isEnterprise;
    public Boolean isMarketplace;

    public ConnectorDetails(String definitionId, String name, Boolean isEnterprise, Boolean isMarketplace) {
        this.definitionId = definitionId;
        this.name = name;
        this.isEnterprise = isEnterprise;
        this.isMarketplace = isMarketplace;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnterprise() {
        return isEnterprise;
    }

    public void setEnterprise(Boolean enterprise) {
        isEnterprise = enterprise;
    }

    public Boolean getMarketplace() {
        return isMarketplace;
    }

    public void setMarketplace(Boolean marketplace) {
        isMarketplace = marketplace;
    }
}
