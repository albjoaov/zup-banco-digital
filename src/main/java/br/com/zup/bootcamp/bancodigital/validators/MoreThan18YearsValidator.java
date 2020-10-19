package br.com.zup.bootcamp.bancodigital.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class MoreThan18YearsValidator implements ConstraintValidator<MoreThan18Years, LocalDate> {

	@Override
	public boolean isValid (LocalDate value, ConstraintValidatorContext context) {
		final var now = LocalDate.now();
		final int age = Period.between(value, now).getYears();
		return age >= 18;
	}
}
