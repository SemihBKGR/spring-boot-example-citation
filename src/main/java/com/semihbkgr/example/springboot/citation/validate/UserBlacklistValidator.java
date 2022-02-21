package com.semihbkgr.example.springboot.citation.validate;

import com.semihbkgr.example.springboot.citation.model.User;
import reactor.core.publisher.Mono;

import java.util.Set;

public class UserBlacklistValidator extends BlacklistValidator<User> {

    public UserBlacklistValidator(Set<String> blacklistSet) {
        super(blacklistSet);
    }

    @Override
    public Mono<User> validate(User user, boolean lenient) {
        return Mono.defer(() -> {

            var vldExc = new BlacklistValidationException();

            if (contains(user.getUsername()))
                vldExc.addInvalidFiled(new ValidationException.InvalidField("username", user.getUsername(),
                        "'" + user.getUsername() + "' is reserved name"));

            if (vldExc.getInvalidFieldMap().isEmpty())
                return Mono.just(user);

            return Mono.error(vldExc);

        });
    }

}
