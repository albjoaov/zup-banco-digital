package br.com.zup.bootcamp.bancodigital.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target ({ FIELD })
@Retention (RUNTIME)
@Constraint (validatedBy = MoreThan18YearsValidator.class)
public @interface MoreThan18Years {

	String message() default "{account-proposal.birthDate.invalid}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
