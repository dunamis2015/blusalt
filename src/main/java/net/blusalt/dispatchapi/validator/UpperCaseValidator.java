package net.blusalt.dispatchapi.validator;

import net.blusalt.dispatchapi.validator.annotation.UpperCase;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Olusegun Adeoye
 */
public class UpperCaseValidator implements ConstraintValidator<UpperCase, String> {

    @Override
    public void initialize(UpperCase constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        // Check if the string contains only upper case letters, underscore, and numbers
        return value.matches("[A-Z0-9_]+");
    }
}

