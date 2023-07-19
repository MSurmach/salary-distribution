package com.intexsoft.salary_distribution.validation.annotation;

import com.intexsoft.salary_distribution.validation.PeriodInputValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
@Constraint(validatedBy = PeriodInputValidator.class)
public @interface ValidInputPeriod {
    String message() default "Constraint violation. Period must be specified in yyyy-MM format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
