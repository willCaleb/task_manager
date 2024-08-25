package com.will.taskmanager.enums;

import jakarta.persistence.Converter;

public enum EnumTaskStatus implements IEnum<String>{

    CREATED("CREATED", "Created"),
    IN_EXECUTION("IN_EXECUTION", "In execution"),
    FINISHED("FINISHED", "Finished");

    private final String key;
    private final String value;

    EnumTaskStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Converter
    public static class EnumConverter extends AbstractEnumConverter<EnumTaskStatus, String> {
    }

}
