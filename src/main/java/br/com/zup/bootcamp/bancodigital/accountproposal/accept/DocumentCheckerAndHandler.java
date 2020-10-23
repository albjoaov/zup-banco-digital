package br.com.zup.bootcamp.bancodigital.accountproposal.accept;

import br.com.zup.bootcamp.bancodigital.accountproposal.AccountProposal;

@FunctionalInterface
public interface DocumentCheckerAndHandler {

	void checkAndHandle (AccountProposal accountProposal);
//	DocumentStatus heck2 (AccountProposal accountProposal);
}
