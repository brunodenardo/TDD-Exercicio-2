package Servico;

public interface ServicoRemoto {

	public ContaCorrente recuperaConta(String numeroInserido);
	
	public void persistirConta(String numeroConta, double saldoAtualizado);


	
}
