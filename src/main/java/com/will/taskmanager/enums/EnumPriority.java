package com.will.taskmanager.enums;

import jakarta.persistence.Converter;

public enum EnumPriority implements IEnum<Integer>{
    VERY_LOW(0, "Very low"),
    LOW(1, "Low"),
    AVERAGE(2, "Average"),
    HIGH(3, "High"),
    VERY_HIGH(4, "Very high")
    ;

    private final Integer key;
    private final String value;
    EnumPriority(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Converter
    public static class EnumConverter extends AbstractEnumConverter<EnumPriority, Integer> {
    }
}
