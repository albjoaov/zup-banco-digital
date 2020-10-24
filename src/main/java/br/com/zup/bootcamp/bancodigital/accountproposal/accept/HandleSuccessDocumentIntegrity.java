package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import br.com.zup.bootcamp.bancodigital.shared.mail.Email;
import br.com.zup.bootcamp.bancodigital.shared.mail.Mailer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
@PropertySource (value = "classpath:MailConfig.properties", encoding = "UTF-8")
public class HandleSuccessDocumentIntegrity {

	@Value ("${account-accept.mail.subject}")
	private String acceptMailSubject;
	@Value("${account-accept.mail.body}")
	private String acceptMailBody;

	private final EntityManager entityManager;
	private final Mailer mailer;

	public HandleSuccessDocumentIntegrity (EntityManager entityManager, Mailer mailer) {
		this.entityManager = entityManager;
		this.mailer = mailer;
	} // 4

	public void execute (AccountProposal accountProposal) {

		accountProposal.setDocumentStatus(DocumentStatus.SUCCESS);
		this.entityManager.merge(accountProposal);

		Account account = new Account(accountProposal);
		this.entityManager.persist(account);

		Email email = new Email(this.acceptMailSubject, this.acceptMailBody, accountProposal.getEmail());
		this.mailer.send(email);
	}

}
