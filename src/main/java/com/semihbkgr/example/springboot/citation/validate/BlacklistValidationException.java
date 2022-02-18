package com.semihbkgr.example.springboot.citation.validate;

import java.util.Map;

public class BlacklistValidationException extends ValidationException {

    public BlacklistValidationException() {
    }

    public BlacklistValidationException(Map<String, InvalidField> invalidFieldMap) {
        super(invalidFieldMap);
    }

}
