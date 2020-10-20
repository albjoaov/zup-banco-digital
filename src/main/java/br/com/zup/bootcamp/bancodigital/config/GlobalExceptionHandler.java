package br.com.zup.bootcamp.bancodigital.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final MessageSource messageSource;

	public GlobalExceptionHandler (MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseStatus (code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler (MethodArgumentNotValidException.class)
	public List<ApiErrorReturn> handleControllerValidation (MethodArgumentNotValidException exception) {

		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		return fieldErrors.stream().map(fieldError -> {
			String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			return new ApiErrorReturn(fieldError.getField(), message);
		}).collect(Collectors.toList());
	}

	@ResponseStatus (code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler (MethodArgumentNotValidException.class)
	public List<ApiErrorReturn> handleControllerValidationWithMultipartFormData (BindException exception) {

		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

		return fieldErrors.stream().map(fieldError -> {
			String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			return new ApiErrorReturn(fieldError.getField(), message);
		}).collect(Collectors.toList());
	}

	@ResponseStatus (code = HttpStatus.NOT_FOUND)
	@ExceptionHandler (javax.persistence.EntityNotFoundException.class)
	public ApiErrorReturn handleInvalidId (javax.persistence.EntityNotFoundException exception) {
		return new ApiErrorReturn("id", exception.getMessage());
	}

}
