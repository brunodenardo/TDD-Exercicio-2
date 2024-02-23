package Mocks;

import java.util.HashMap;

import Erros.CartaoInvalidoException;
import Erros.EnvelopeInseridoInvalidoException;
import Servico.Hardware;

public class MockHardware implements Hardware{
	
	private HashMap<Integer, String> dicionarioCartões = new HashMap<Integer, String>();

	@Override
	public String pegarNumeroDaContaCartao(int cartaoInserido) throws CartaoInvalidoException{
		String numeroConta = dicionarioCartões.get(cartaoInserido);
		if(numeroConta == null)
			throw new CartaoInvalidoException();
		return numeroConta;
	}

	public void adicionarNumeroConta(int idCartao, String numeroConta) {
		dicionarioCartões.put(idCartao, numeroConta);
	}

	@Override
	public void entregarDinheiro(Boolean envelopeNaoInserido) {
	
	}

	@Override
	public void lerEnvelope(Boolean envelopeMalInserido) throws EnvelopeInseridoInvalidoException {
		if(envelopeMalInserido)
			throw new EnvelopeInseridoInvalidoException();
	}



}
