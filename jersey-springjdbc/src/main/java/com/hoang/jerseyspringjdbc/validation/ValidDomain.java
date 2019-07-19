package com.hoang.jerseyspringjdbc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.MessageFormat;
import java.util.regex.Pattern;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.lang3.StringUtils;

import com.hoang.jerseyspringjdbc.exception.BusinessException;
import com.hoang.jerseyspringjdbc.model.Constants;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER,
    ElementType.TYPE_USE })
@Constraint(validatedBy = ValidDomain.DomainValidator.class)
public @interface ValidDomain {
    Class<?>[] groups () default {};

    String message () default "";

    Class<? extends Payload>[] payload () default {};

    class DomainValidator implements ConstraintValidator<ValidDomain, String> {

        @Override
        public void initialize (ValidDomain validDomainAnnotation) {
        }

        @Override
        public boolean isValid (String value, ConstraintValidatorContext constraintValidatorContext) {
            if ( StringUtils.isNotEmpty(value) ) {
                for (String domain : StringUtils.split(value, ",")) {
                    String errorMessage = MessageFormat.format(BusinessException.DOMAIN_INVALID, value);
                    if ( !Pattern.matches(Constants.DOMAIN_REGEX, domain) ) {
                        constraintValidatorContext.disableDefaultConstraintViolation();
                        constraintValidatorContext.buildConstraintViolationWithTemplate(errorMessage).
                            addConstraintViolation();
                        return false;
                    }
                }
            }
            return true;
        }
    }

}
