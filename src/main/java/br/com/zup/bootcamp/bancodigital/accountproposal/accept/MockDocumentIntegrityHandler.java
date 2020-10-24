package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@PropertySource (value = {"classpath:MailConfig.properties"}, encoding = "UTF-8")
@Profile ("dev")
public class MockDocumentIntegrityHandler implements DocumentIntegrityHandler {

	private final EntityManager entityManager;
	private final HandleSuccessDocumentIntegrity handleSuccessDocumentIntegrity;

	public MockDocumentIntegrityHandler (EntityManager entityManager,
	                                     HandleSuccessDocumentIntegrity handleSuccessDocumentIntegrity) {
		this.entityManager = entityManager;
		this.handleSuccessDocumentIntegrity = handleSuccessDocumentIntegrity;
	} // 5

	@Override
	@Transactional
	public void handle (DocumentStatus documentStatus, AccountProposal accountProposal) {

		if (documentStatus == DocumentStatus.SUCCESS) {
			this.handleSuccessDocumentIntegrity.execute(accountProposal);
		} else {
			accountProposal.setDocumentStatus(documentStatus);
			this.entityManager.merge(accountProposal);
		}

	}
}
