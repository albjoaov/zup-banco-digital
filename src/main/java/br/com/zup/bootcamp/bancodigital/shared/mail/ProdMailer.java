package br.com.zup.bootcamp.bancodigital.shared.mail;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile ("prod")
public class ProdMailer implements Mailer {

	@Override
	public void send (Email email) {
		// Implementação real aqui com Spring Mail, por exemplo
	}
}
