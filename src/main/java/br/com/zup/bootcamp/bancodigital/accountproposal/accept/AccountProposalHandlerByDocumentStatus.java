package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;
import br.com.zup.bootcamp.bancodigital.shared.mail.Mailer;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface AccountProposalHandlerByDocumentStatus {

	void handle(EntityManager entityManager, AccountProposal accountProposal, Mailer mailer);
}
