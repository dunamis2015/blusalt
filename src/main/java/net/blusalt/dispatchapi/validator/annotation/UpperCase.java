package net.blusalt.dispatchapi.validator.annotation;

import net.blusalt.dispatchapi.validator.UpperCaseValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Olusegun Adeoye
 */
@Documented
@Constraint(validatedBy = UpperCaseValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpperCase {
    String message() default "Only upper case letters, underscore, and numbers are allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
