package com.semihbkgr.example.springboot.tale.validate;

import com.semihbkgr.example.springboot.tale.model.User;
import reactor.core.publisher.Mono;

import java.util.Map;

public class UserValidator extends ConstraintValidator<User> {

    public UserValidator(Map<String, Object> constraintMap) {
        super(constraintMap);
    }

    @Override
    public Mono<User> validate(User user) {
        return Mono.defer(() -> {

            var vldExc = new ValidationException();

            if (user.getUsername().length() < minLengthOf("username")) {
                vldExc.addInvalidFiled(new ValidationException.InvalidField("username", user.getUsername(), "min"));
            } else if (user.getUsername().length() > maxLengthOf("username")) {
                vldExc.addInvalidFiled(new ValidationException.InvalidField("username", user.getUsername(), "max"));
            }

            if (user.getEmail().length() < minLengthOf("email")) {
                vldExc.addInvalidFiled(new ValidationException.InvalidField("email", user.getEmail(), "min"));
            } else if (user.getEmail().length() > maxLengthOf("email")) {
                vldExc.addInvalidFiled(new ValidationException.InvalidField("email", user.getEmail(), "max"));
            }

            if (user.getPassword().length() < minLengthOf("password")) {
                vldExc.addInvalidFiled(new ValidationException.InvalidField("password", user.getPassword(), "min"));
            } else if (user.getPassword().length() > maxLengthOf("password")) {
                vldExc.addInvalidFiled(new ValidationException.InvalidField("password", user.getPassword(), "max"));
            }

            if (vldExc.getInvalidFieldMap().isEmpty())
                return Mono.just(user);

            return Mono.error(vldExc);

        });
    }

}
