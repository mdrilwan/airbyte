package com.app.airbyte.config;

public class Constants {
    public static final String AIRBYTE_HOST = System.getenv("AIRBYTE_HOST");
    public static final String AIRBYTE_USER_EMAIL = System.getenv("AIRBYTE_USER_EMAIL");
    public static final String AIRBYTE_PASSWORD = System.getenv("AIRBYTE_PASSWORD");
    public static final String AIRBYTE_SOURCE_LIST_API_URL = "/api/v1/source_definitions/list";
    public static final String AIRBYTE_DEST_LIST_API_URL = "/api/v1/destination_definitions/list";
    public static final String AIRBYTE_CREATE_SOURCE_API_URL = "/api/v1/sources/create";
    public static final String AIRBYTE_CREATE_DEST_API_URL = "/api/v1/destinations/create";
}
