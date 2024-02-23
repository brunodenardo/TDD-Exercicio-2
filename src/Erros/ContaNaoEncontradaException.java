package Erros;

public class ContaNaoEncontradaException extends RuntimeException {

	public ContaNaoEncontradaException() {
		super("Número da conta não bate com o de nenhuma conta existente.");
	}
}
