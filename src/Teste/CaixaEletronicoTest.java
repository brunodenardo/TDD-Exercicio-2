package Teste;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Erros.CartaoInvalidoException;
import Erros.ContaNaoEncontradaException;
import Erros.EnvelopeInseridoInvalidoException;
import Erros.SemCedulasException;
import Mocks.MockHardware;
import Mocks.MockServicoRemoto;
import Servico.CaixaEletronico;
import Servico.ContaCorrente;

class CaixaEletronicoTest {
	
	private CaixaEletronico caixa;
	private ContaCorrente conta1;
	private ContaCorrente conta2;
	private MockServicoRemoto mockServico;
	private MockHardware mockHardware;
	
	@BeforeEach
	public void setUp() {
		caixa = new CaixaEletronico();
		conta1 = new ContaCorrente("1111", "senha1", 123.90);
		conta2 = new ContaCorrente("2222", "senha2", 123.91);
		mockServico = new MockServicoRemoto();
		mockHardware = new MockHardware();
		mockServico.adicionarConta(conta1);
		mockHardware.adicionarNumeroConta(1, conta1.getNumero());
		mockServico.adicionarConta(conta2);
		mockHardware.adicionarNumeroConta(2, conta2.getNumero());
	}
	
	// ------------------ LOGIN -----------------

	@Test
	public void testeLoginCorreto() {
		String numeroConta = mockHardware.pegarNumeroDaContaCartao(1);
		String resposta = caixa.login(numeroConta, "senha1", mockServico);
		assertEquals(resposta, "Usuário Autenticado");
	}
	
	@Test
	public void testeLoginNumeroIncorreto() {
		try {
			String numeroConta = mockHardware.pegarNumeroDaContaCartao(0);
			fail("Hardware permitindo cartão inválido.");
		} catch (CartaoInvalidoException e) {}
	}
	
	@Test
	public void testeLoginSenhaIncorreta() {
		String numeroConta = mockHardware.pegarNumeroDaContaCartao(1);
		String resposta = caixa.login(numeroConta, "senha2", mockServico);
		assertEquals(resposta, "Não foi possível autenticar o usuário");
	}
	
	// ------------------ SALDO -----------------
	
	@Test
	public void conferindoSaldoComZeroNoFinalDosCentavos() {
		String resposta = caixa.saldo("1111", mockServico);
		assertEquals("O saldo é R$123,90", resposta);
	}
	
	@Test
	public void conferindoSaldoSemZeroNoFinalDosCentavos() {
		String resposta = caixa.saldo("2222", mockServico);
		assertEquals("O saldo é R$123,91", resposta);
	}
	
	@Test
	public void conferindoSaldoComNumeroContaErrado() {
		try {
			String resposta = caixa.saldo("1122", mockServico);
			fail("permite consulta em contas não existentes");
		} catch (ContaNaoEncontradaException e) {}
	}

	// ------------------ DEPÓSITO ------------------
	
	@Test
	void depositandoDinheiroCorreto() {
		String resposta = caixa.depositar("1111", 104.03, mockServico, mockHardware, false);
		Double saldoPosMudanca = mockServico.recuperaConta("1111").getSaldo();
		assertEquals("Depósito recebido com sucesso", resposta);
		assertEquals(227.93, saldoPosMudanca);
	}
	
	
	@Test
	void depositandoDinheiroSemInserirInvelope() {
		try {
			String resposta = caixa.depositar("1111", 104.03, mockServico, mockHardware, false);
		} catch (EnvelopeInseridoInvalidoException e) {}
	}
	
	// ------------------ SAQUE -----------------
	
	@Test
	void saqueRespeitandoLimiteSaldo() {
		String resultado = caixa.sacar("1111", 100.90, mockServico, mockHardware, true);
		assertEquals("Retire seu dinheiro", resultado);
		assertEquals(23.00, conta1.getSaldo());
	}
	
	@Test
	void saqueNaoRespeitandoLimiteSaldo() {
		String resultado = caixa.sacar("1111", 150.90, mockServico, mockHardware, true);
		assertEquals("Saldo insuficiente", resultado);
		assertEquals(123.90, conta1.getSaldo());
	}
	
	@Test
	void saqueSemCedula() {
		try {
			caixa.sacar("1111", 100.90, mockServico, mockHardware, false);
		} catch (SemCedulasException e) {
			assertEquals(123.90, conta1.getSaldo());
		}
	}
}
