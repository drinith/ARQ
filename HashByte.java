package br.com.trabalhohash;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class HashByte {
	
	
	private String cepRegHash;
	private String posicaoRegHash;
	private String ponteiroRegHash;
	
	private byte [] cepHash = new byte[8];
	private byte [] posicaoHash = new byte[9];
	private byte [] ponteiroHash = new byte[7];
	
	public String getCepRegHash() {
		return cepRegHash;
	}
	public void setCepRegHash(String cepRegHash) {
		this.cepRegHash = cepRegHash;
	}
	public String getPosicaoRegHash() {
		return posicaoRegHash;
	}
	public void setPosicaoRegHash(String posicaoRegHash) {
		this.posicaoRegHash = posicaoRegHash;
	}
	public String getPonteiroRegHash() {
		return ponteiroRegHash;
	}
	public void setPonteiroRegHash(String ponteiroRegHash) {
		this.ponteiroRegHash = ponteiroRegHash;
	}
	public void leHashByte(RandomAccessFile fHash) throws IOException {
		
		fHash.readFully(cepHash);
		fHash.readFully(posicaoHash);
		fHash.readFully(ponteiroHash);
		
		
		Charset enc = Charset.forName("ISO-8859-1");
		
		cepRegHash = new String(cepHash,enc);
		posicaoRegHash = new String(posicaoHash,enc);
		ponteiroRegHash = new String(ponteiroHash,enc);
		
	}
	

	
	
}
