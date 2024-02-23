package Servico;

import Erros.CartaoInvalidoException;
import Erros.EnvelopeInseridoInvalidoException;

public interface Hardware {

	public String pegarNumeroDaContaCartao(int idCartao) throws CartaoInvalidoException;
	
	public void entregarDinheiro(Boolean dinheiroNaoEntregue) throws EnvelopeInseridoInvalidoException;
	
	public void  lerEnvelope(Boolean dinheiroNaoEntregue) throws EnvelopeInseridoInvalidoException;
}
