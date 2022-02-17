package com.semihbkgr.example.springboot.tale.validate;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.*;

public class ValidationException extends RuntimeException {

    private final transient Map<String, InvalidField> invalidFieldMap;

    public ValidationException(Map<String, InvalidField> invalidFieldMap) {
        this.invalidFieldMap = new HashMap<>(invalidFieldMap);
    }

    public ValidationException() {
        this(new HashMap<>());
    }

    public void addInvalidFiled(@NonNull ValidationException.InvalidField invalidField) {
        if (invalidFieldMap.containsKey(invalidField.name))
            invalidFieldMap.get(invalidField.name).messages.addAll(invalidField.messages);
        else
            invalidFieldMap.put(invalidField.name, invalidField);
    }

    public Map<String, InvalidField> getInvalidFieldMap() {
        return Collections.unmodifiableMap(invalidFieldMap);
    }

    @EqualsAndHashCode(exclude = "messages")
    public static class InvalidField {

        public final String name;
        public final String value;
        public final List<String> messages;

        public InvalidField(@NonNull String name, @NonNull String value, @NonNull List<String> messages) {
            this.name = name;
            this.value = value;
            this.messages = messages;
        }

        public InvalidField(@NonNull String name, @NonNull String value, String... messages) {
            this(name, value, new ArrayList<>(Arrays.asList(messages)));
        }

        public InvalidField(@NonNull String name, @NonNull String value, String message) {
            this(name, value, new ArrayList<>());
            messages.add(message);
        }

        public InvalidField(@NonNull String name, @NonNull String value) {
            this(name, value, new ArrayList<>());
        }

        public InvalidField(@NonNull String name, @NonNull Object value) {
            this(name, value.toString());
        }

    }

}
