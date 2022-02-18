package com.semihbkgr.example.springboot.citation.validate;

import java.util.Collections;
import java.util.Set;

public abstract class BlacklistValidator<E> implements Validator<E> {

    private final Set<String> blacklistSet;

    protected BlacklistValidator(Set<String> blacklistSet) {
        this.blacklistSet = blacklistSet;
    }

    protected boolean contains(String value) {
        return blacklistSet.contains(value);
    }

    public Set<String> getBlacklistSet() {
        return Collections.unmodifiableSet(blacklistSet);
    }

}
