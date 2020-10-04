package com.info.packagetrackerbackend.validators;

import com.info.packagetrackerbackend.model.SystemUser;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator extends GenericValidator implements Validator {

    private SystemUserRepository repository;

    public RegistrationValidator(SystemUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SystemUser.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SystemUser user = (SystemUser) o;
        validateRequired(user, errors);
    }

    private void validateRequired(SystemUser user, Errors errors) {
        validateIfTrue(StringUtils.isEmpty(user.getUsername()), "username", ValidationCode.REQUIRED.getValue(), errors);
        validateIfTrue(StringUtils.isEmpty(user.getEmail()), "email", ValidationCode.REQUIRED.getValue(), errors);
        validateIfTrue(StringUtils.isEmpty(user.getPassword()), "password", ValidationCode.REQUIRED.getValue(), errors);
    }

    private void validateUniqueness(SystemUser user, Errors errors) {

    }

}
