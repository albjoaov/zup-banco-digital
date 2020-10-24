package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@PropertySource (value = {"classpath:ValidationMessages.properties",
		"classpath:MailConfig.properties",
		"classpath:RetryConfig.properties"},
		encoding = "UTF-8")
@Profile ("dev")
public class MockDocumentIntegrityChecker implements DocumentIntegrityChecker {

	private final RestTemplate httpClient;

	private static final String SUCCESS_URI_FAKE = "https://api.mocki.io/v1/b254cfd0";
	private static final String ERROR_URI_FAKE = "https://api.mocki.io/v1/c74ecc16 ";

	public MockDocumentIntegrityChecker (RestTemplate httpClient) { // 4
		this.httpClient = httpClient;
	}

	@Override
	@Transactional
	@Retryable (value = RestClientException.class,
			maxAttemptsExpression = "${retry.max-attempts}",
			backoff = @Backoff (delayExpression = "${retry.max-delay}"))
	public DocumentStatus check (AccountProposal accountProposal) {

		var documentIntegrityCheckerRequest = new DocumentIntegrityCheckerRequest(accountProposal);
		var documentStatusResponse = httpClient.postForObject(SUCCESS_URI_FAKE,
				documentIntegrityCheckerRequest,
				DocumentIntegrityCheckerResponse.class);

		return documentStatusResponse.getDocumentStatus();
	}

}
