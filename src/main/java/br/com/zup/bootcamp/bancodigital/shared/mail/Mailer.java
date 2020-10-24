package br.com.zup.bootcamp.bancodigital.shared.mail;

@FunctionalInterface
public interface Mailer {

	void send(Email email);
}
