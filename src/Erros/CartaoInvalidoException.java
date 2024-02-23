package Erros;

public class CartaoInvalidoException extends RuntimeException {

	public CartaoInvalidoException() {
		super("Cartão inválido.");
	}
}
