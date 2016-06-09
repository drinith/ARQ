package br.com.trabalhohash;

public class Hash {

	
	//posicao na lista de CEP
	private String cep;
	//posicao para o outro arquivo colisao
	private int ponteiro;
	private int endereco;
	
		
	public Hash(String _cep, int _endereco, int _ponteiro) {
		cep = _cep;
		ponteiro = _ponteiro;
		setEndereco(_endereco) ;
	
	}
			
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	
	
	
	public int getPonteiro() {
		return ponteiro;
	}
	public void setPonteiro(int ponteiro) {
		this.ponteiro = ponteiro;
	}

	public int getEndereco() {
		return endereco;
	}

	public void setEndereco(int endereco) {
		this.endereco = endereco;
	}
	
	
	
	
	
	
}
