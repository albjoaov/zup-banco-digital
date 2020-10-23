package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import br.com.zup.bootcamp.bancodigital.shared.mail.Email;
import br.com.zup.bootcamp.bancodigital.shared.mail.Mailer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@PropertySource (value = "classpath:RetryConfig.properties", encoding = "UTF-8")
public class MockDocumentIntegrityChecker implements DocumentIntegrityChecker {

	private final EntityManager entityManager;
	private final Mailer mailer; //1
	private final RestTemplate httpClient;

	private static final String FAKE_SUCCESS_URI = "https://api.mocki.io/v1/b254cfd0";
	private static final String FAKE_ERROR_URI = "https://api.mocki.io/v1/c74ecc16 ";

	public MockDocumentIntegrityChecker (EntityManager entityManager,
	                                     Mailer mailer,
	                                     RestTemplate httpClient) {
		this.entityManager = entityManager;
		this.mailer = mailer;
		this.httpClient = httpClient;
	}

	/**
	 * This method implements retryable method
	 * @param accountProposal Account Proposal that has client information
	 * @return
	 */
//	@Override
	//2
	@Retryable (value = RestClientException.class,
			maxAttemptsExpression = "${retry.max-attempts}",
			backoff = @Backoff (delayExpression = "${retry.max-delay}"))
	public DocumentStatus check (AccountProposal accountProposal) {
		//3
		var documentIntegrityCheckerRequest = new DocumentIntegrityCheckerRequest(accountProposal);
		var documentIntegrityCheckerResponse = httpClient.postForObject(FAKE_SUCCESS_URI,
				documentIntegrityCheckerRequest,
				DocumentIntegrityCheckerResponse.class);
		return documentIntegrityCheckerResponse.getDocumentStatus();
	}

	// 2
	@Transactional
	public void check2 (AccountProposal accountProposal) {
		//3
		var documentIntegrityCheckerRequest = new DocumentIntegrityCheckerRequest(accountProposal);
		var documentStatusResponse = httpClient.postForObject(FAKE_SUCCESS_URI, documentIntegrityCheckerRequest, DocumentIntegrityCheckerResponse.class);
		DocumentStatus documentStatus = documentStatusResponse.getDocumentStatus(); //3
		//4

		//5
		if (documentStatus == DocumentStatus.SUCCESS) {
			accountProposal.setDocumentStatus(documentStatus);
			this.entityManager.merge(accountProposal);

			//6
			Account account = new Account(accountProposal);
			this.entityManager.persist(account);

			//7
			Email email = new Email("","", accountProposal.getEmail());
			this.mailer.send(email);

			//8
		} else {
			accountProposal.setDocumentStatus(documentStatus);
			this.entityManager.merge(accountProposal);
		}
	}

}
