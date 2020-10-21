package br.com.zup.bootcamp.bancodigital.validators;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidationUtils {

	public static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

}
