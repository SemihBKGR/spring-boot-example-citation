package com.semihbkgr.example.springboot.citation.validate;

import com.semihbkgr.example.springboot.citation.model.User;
import reactor.core.publisher.Mono;

import java.util.Map;

public class UserConstraintValidator extends ConstraintValidator<User> {

    public UserConstraintValidator(Map<String, Object> constraintMap) {
        super(constraintMap);
    }

    @Override
    public Mono<User> validate(User user, boolean lenient) {
        return Mono.defer(() -> {

            var vldExc = new ConstraintValidationException();

            if (!lenient || user.getUsername().length() > 0) {
                if (user.getUsername().length() < minLengthOf("username") || user.getUsername().length() > maxLengthOf("username"))
                    vldExc.addInvalidFiled(new ValidationException.InvalidField("username", user.getUsername(),
                            "username length is must be between " + minLengthOf("username") + "-" + maxLengthOf("username")));
            }

            if (!lenient || user.getEmail().length() > 0) {
                if (user.getEmail().length() < minLengthOf("email") || user.getEmail().length() > maxLengthOf("email"))
                    vldExc.addInvalidFiled(new ValidationException.InvalidField("email", user.getEmail(),
                            "email length is must be between " + minLengthOf("email") + "-" + maxLengthOf("email")));
            }

            if (!lenient || user.getPassword().length() > 0) {
                if (user.getPassword().length() < minLengthOf("password") || user.getPassword().length() > maxLengthOf("password"))
                    vldExc.addInvalidFiled(new ValidationException.InvalidField("password", user.getPassword(),
                            "password length is must be between " + minLengthOf("password") + "-" + maxLengthOf("password")));

            }

            if (vldExc.getInvalidFieldMap().isEmpty())
                return Mono.just(user);

            return Mono.error(vldExc);

        });
    }

}
