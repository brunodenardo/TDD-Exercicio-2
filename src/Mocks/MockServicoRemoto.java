package Mocks;

import java.util.HashMap;

import Servico.ContaCorrente;
import Servico.ServicoRemoto;

public class MockServicoRemoto implements  ServicoRemoto{

	private HashMap<String, ContaCorrente> contasSistema = new HashMap<String, ContaCorrente>();
	
	@Override
	public ContaCorrente recuperaConta(String numeroInserido) {
		if(contasSistema.containsKey(numeroInserido))
			return contasSistema.get(numeroInserido);
		return null;
	}

	public void adicionarConta(ContaCorrente conta) {
		contasSistema.put(conta.getNumero(), conta);
		
	}


	@Override
	public void persistirConta(String numeroConta, double saldoAtualizado) {
		ContaCorrente conta = contasSistema.get(numeroConta);
		conta.setSaldo(saldoAtualizado);
	}



}
