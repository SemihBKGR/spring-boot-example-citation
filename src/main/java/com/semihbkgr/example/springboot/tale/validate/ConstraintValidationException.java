package com.semihbkgr.example.springboot.tale.validate;

import java.util.Map;

public class ConstraintValidationException extends ValidationException {

    public ConstraintValidationException() {
    }

    public ConstraintValidationException(Map<String, InvalidField> invalidFieldMap) {
        super(invalidFieldMap);
    }

}
