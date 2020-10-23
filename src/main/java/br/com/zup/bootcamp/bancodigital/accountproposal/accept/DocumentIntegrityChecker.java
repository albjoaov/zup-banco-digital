package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;

@FunctionalInterface
public interface DocumentIntegrityChecker {

	void check2 (AccountProposal accountProposal);
//	DocumentStatus check2 (AccountProposal accountProposal);
}
