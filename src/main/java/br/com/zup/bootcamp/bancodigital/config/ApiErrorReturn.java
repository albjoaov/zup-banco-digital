package br.com.zup.bootcamp.bancodigital.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude (NON_NULL)
@JsonAutoDetect (fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ApiErrorReturn {

	private final String field;
	private final String errorMessage;

	public ApiErrorReturn (String field, String errorMessage) {
		this.field = field;
		this.errorMessage = errorMessage;
	}
}