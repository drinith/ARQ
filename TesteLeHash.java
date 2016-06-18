package br.com.trabalhohash;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Scanner;

public class TesteLeHash {

	static public void main(String[] args) throws IOException {

		String path = "/media/aluno/FELIPE_PR/";
		String buscaCEP;
		int valorHash;
		String cepRegHash;
		String posicaoRegHash;
		String ponteiroRegHash;
		Scanner scan = new Scanner(System.in);
		byte [] cepHash = new byte[8];
		byte [] posicaoHash = new byte[9];
		byte [] ponteiroHash = new byte[7];
		
		
		RandomAccessFile hash = new RandomAccessFile(path+"hash.dat", "r");
		RandomAccessFile registro = new RandomAccessFile(path+"cep.dat", "r");
		Endereco e = new Endereco();
		
		System.out.println("Digite o CEP");
		buscaCEP = scan.next();
		valorHash = Integer.valueOf(buscaCEP)% 750000;

		
		hash.seek(valorHash*24);
		hash.readFully(cepHash);
		hash.readFully(posicaoHash);
		hash.readFully(ponteiroHash);
		
		Charset enc = Charset.forName("ISO-8859-1");
		
		cepRegHash = new String(cepHash,enc);
		posicaoRegHash = new String(posicaoHash,enc);
		ponteiroRegHash = new String(ponteiroHash,enc);
		
		while (!ponteiroRegHash.equals("000000000") && !cepRegHash.equals(buscaCEP)){
		
			hash.seek(Integer.valueOf(ponteiroRegHash)*24);
			hash.readFully(cepHash);
			hash.readFully(posicaoHash);
			hash.readFully(ponteiroHash);
			
			
			
			cepRegHash = new String(cepHash,enc);
			posicaoRegHash = new String(posicaoHash,enc);
			ponteiroRegHash = new String(ponteiroHash,enc);
			
			
		}
		
		int valor = Integer.valueOf(posicaoRegHash); 
		registro.seek(300*Integer.valueOf(valor));
		
		e.leEndereco(registro);
		
		System.out.println(e.getBairro());
		
		
		
		hash.close();
		
		
		
	}

}
