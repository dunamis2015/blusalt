package net.blusalt.dispatchapi.validator;

import net.blusalt.dispatchapi.validator.annotation.ValidValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Olusegun Adeoye
 */
public class ValueValidator implements ConstraintValidator<ValidValue, String> {

  private static final String UNWANTED_CHARACTER = "[^,;:*\\[\\]|()\'\"]+";

  @Override
  public void initialize(ValidValue constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return (validateValue(value));
  }

  public boolean validateValue(String value) {
    Pattern pattern = Pattern.compile(UNWANTED_CHARACTER);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}