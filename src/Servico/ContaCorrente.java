package Servico;

public class ContaCorrente {
	
	private String _numero;
	private String _senha;
	private Double _saldo;

	public ContaCorrente(String numeroNovaConta, String senhaNovaConta) {
		_numero = numeroNovaConta;
		_senha = senhaNovaConta;
		_saldo = 0.00;
	}

	public ContaCorrente(String numeroNovaConta, String senhaNovaConta, double saldoNovaConta) {
		_numero = numeroNovaConta;
		_senha = senhaNovaConta;
		_saldo = saldoNovaConta;
	}

	public String getNumero() {
		return _numero;
	}
	
	public String getSenha() {
		return _senha;
	}
	
	public Double getSaldo() {
		return _saldo;
	}

	public void setSaldo(double novoSaldo) {
		_saldo = novoSaldo;
	}
}
