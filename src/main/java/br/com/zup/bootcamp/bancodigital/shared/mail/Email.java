package br.com.zup.bootcamp.bancodigital.shared.mail;

import javax.validation.constraints.NotBlank;

public class Email {

	private final @NotBlank @javax.validation.constraints.Email String from;
	private	final @NotBlank String nameFrom;
	private	final @NotBlank String subject;
	private final @NotBlank String body;
	private final @NotBlank @javax.validation.constraints.Email String to;

	public Email (@NotBlank String subject,
	              @NotBlank String body,
	              @NotBlank String to) {
		this.from = "no-reply@zup.com.br";
		this.nameFrom = "Zup Banco Digital";
		this.subject = subject;
		this.body = body;
		this.to = to;
	}

	@Override
	public String toString () {
		return "Email{" +
				"from='" + from + '\'' +
				", nameFrom='" + nameFrom + '\'' +
				", subject='" + subject + '\'' +
				", body='" + body + '\'' +
				", to='" + to + '\'' +
				'}';
	}
}
