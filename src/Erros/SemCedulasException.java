package Erros;

public class SemCedulasException extends RuntimeException {
	
	public SemCedulasException() {
		super("Caixa sem cédulas");
	}
}
