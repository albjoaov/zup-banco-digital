package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import br.com.zup.bootcamp.bancodigital.shared.mail.Email;
import br.com.zup.bootcamp.bancodigital.shared.mail.Mailer;

import javax.persistence.EntityManager;

public enum DocumentStatus implements AccountProposalHandlerByDocumentStatus {
// 1

	// 2
	SUCCESS {
		@Override
		//3 e 4
		public void handle (EntityManager entityManager, AccountProposal accountProposal, Mailer mailer) {
			accountProposal.setDocumentStatus(this);
			entityManager.merge(accountProposal);

			// 5
			Account account = new Account(accountProposal);
			entityManager.persist(account);

			// 6
			Email email = new Email("","", accountProposal.getEmail());
			mailer.send(email);
		}
	},


	// 7
	ERROR {
		@Override
		public void handle (EntityManager entityManager, AccountProposal accountProposal, Mailer mailer) {
			accountProposal.setDocumentStatus(this);
			entityManager.merge(accountProposal);
		}
	};
}
