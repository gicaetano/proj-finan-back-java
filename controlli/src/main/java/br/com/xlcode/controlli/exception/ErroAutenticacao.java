package br.com.xlcode.controlli.exception;

public class ErroAutenticacao extends RuntimeException {

	private static final long serialVersionUID = 6387121765621954892L;
	
	public ErroAutenticacao(String mensagem) {
		super(mensagem);
	}

}
