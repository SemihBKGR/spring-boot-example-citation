package com.semihbkgr.example.springboot.tale.validate;

import lombok.NonNull;

import java.util.Map;

public abstract class ConstraintValidator<E> implements Validator<E> {

    protected static final String SUFFIX_MIN_LENGTH = "-min";
    protected static final String SUFFIX_MAX_LENGTH = "-max";
    protected static final String SUFFIX_REGEX = "-regex";
    protected static final int DEFAULT_MIN_LENGTH = 0;
    protected static final int DEFAULT_MAX_LENGTH = Integer.MAX_VALUE;
    protected static final String DEFAULT_REGEX = "";

    protected final Map<String, Object> constraintMap;

    protected ConstraintValidator(Map<String, Object> constraintMap) {
        this.constraintMap = constraintMap;
    }

    protected final int minLengthOf(@NonNull String filed) {
        return (int) constraintMap.getOrDefault(filed.concat(SUFFIX_MIN_LENGTH), DEFAULT_MIN_LENGTH);
    }

    protected final int maxLengthOf(@NonNull String filed) {
        return (int) constraintMap.getOrDefault(filed.concat(SUFFIX_MAX_LENGTH), DEFAULT_MAX_LENGTH);
    }

    protected final String regex(@NonNull String filed) {
        return (String) constraintMap.getOrDefault(filed.concat(SUFFIX_REGEX), DEFAULT_REGEX);
    }

}
