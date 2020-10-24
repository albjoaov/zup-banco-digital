package br.com.zup.bootcamp.bancodigital.shared.mail;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile ("dev")
public class MockMailer implements Mailer {

	@Override
	public void send (Email email) {
		System.out.println(email.toString());
	}
}
