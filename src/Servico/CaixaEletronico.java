package Servico;

import Erros.ContaNaoEncontradaException;
import Mocks.MockHardware;
import Mocks.MockServicoRemoto;

public class CaixaEletronico {

	public String login(String numeroInserido, String senhaInserida, ServicoRemoto servicoRemoto) {
		ContaCorrente conta = servicoRemoto.recuperaConta(numeroInserido);
		if(conta == null)
			return "Não foi possível autenticar o usuário";
		if(senhaInserida.equals(conta.getSenha()))
			return "Usuário Autenticado";
		return "Não foi possível autenticar o usuário";
	}


	public String saldo(String numeroInserido, ServicoRemoto servicoRemoto) {
		ContaCorrente conta = servicoRemoto.recuperaConta(numeroInserido);
		if(conta == null)
			throw new ContaNaoEncontradaException();
		String saldo = conta.getSaldo().toString();
		saldo = saldo.replace(".", ",");
		saldo = this.adicionaZeroNoSaldo(saldo);
		String resposta = "O saldo é R$" + saldo;
		return resposta;
	}
	
	
	private String adicionaZeroNoSaldo(String saldoSemTratamento) {
		String[] centavosSaldo = saldoSemTratamento.split(",");
		if(centavosSaldo[1].length() == 2)
			return saldoSemTratamento;
		return saldoSemTratamento + "0";
	}


	public String depositar(String numeroConta, double valorInserido,
			ServicoRemoto servico, Hardware hardware, Boolean envelopeMalEntregue) {
		hardware.lerEnvelope(envelopeMalEntregue);
		ContaCorrente conta = servico.recuperaConta(numeroConta);
		Double saldoAtualizado = Double.sum(valorInserido, conta.getSaldo());
		servico.persistirConta(numeroConta, saldoAtualizado);
		return "Depósito recebido com sucesso";
	}


	public String sacar(String numeroConta, double valorSacado,
			ServicoRemoto servico, Hardware hardware, Boolean temCedolas) {
		ContaCorrente conta = servico.recuperaConta(numeroConta);
		Double saldoAtual = Double.sum(-valorSacado, conta.getSaldo());
		hardware.entregarDinheiro(temCedolas);
		if(saldoAtual >= 0.00) {
			servico.persistirConta(numeroConta, saldoAtual);
			return "Retire seu dinheiro";
		}
		return "Saldo insuficiente";
	}

	

}
