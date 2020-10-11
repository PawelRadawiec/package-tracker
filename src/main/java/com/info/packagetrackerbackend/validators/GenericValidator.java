package com.info.packagetrackerbackend.validators;

import org.springframework.validation.Errors;

public class GenericValidator {

    protected Errors errors;

    public enum ValidationCode {
        REQUIRED("must be set"), UNIQUE("must be unique");

        private String value;

        ValidationCode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    protected void validateIfTrue(Boolean statement, String field, String code, Errors errors) {
        if (statement) {
            errors.rejectValue(field, code, code);
        }
    }

}
