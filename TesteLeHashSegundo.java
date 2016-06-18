package br.com.trabalhohash;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.Scanner;

public class TesteLeHashSegundo {

	public static void main(String[] args) throws IOException {

		String path="D:/";
		String buscaCEP;
		int valorHash;
		int colisaoSoma = 0;
		int primo;
		String cepRegHash;
		String posicaoRegHash;
		String ponteiroRegHash;
		Scanner scan = new Scanner(System.in);
		byte[] cepHash = new byte[8];
		byte[] posicaoHash = new byte[9];
		byte[] ponteiroHash = new byte[7];

		RandomAccessFile hash = new RandomAccessFile(path+"hash_colisao.dat", "r");
		RandomAccessFile registro = new RandomAccessFile(path+"cep_colisao.dat", "r");
		Endereco e = new Endereco();

		System.out.println("Digite o CEP");
		buscaCEP = scan.next();
		
		// numero para o calculo do hash
		primo = Primo.buscaPrimoAnterior((int)registro.length()/300);
		valorHash = Integer.valueOf(buscaCEP) % primo;

		//Preenchimento dos vetores com os registros
		hash.seek(valorHash * 24);
		hash.readFully(cepHash);
		hash.readFully(posicaoHash);
		hash.readFully(ponteiroHash);

		Charset enc = Charset.forName("ISO-8859-1");

		cepRegHash = new String(cepHash, enc);
		posicaoRegHash = new String(posicaoHash, enc);
		ponteiroRegHash = new String(ponteiroHash, enc);

		while (!ponteiroRegHash.equals("000000000") && !cepRegHash.equals(buscaCEP)) {

			hash.seek(Integer.valueOf(ponteiroRegHash) * 24);
			hash.readFully(cepHash);
			hash.readFully(posicaoHash);
			hash.readFully(ponteiroHash);

			colisaoSoma++;

			cepRegHash = new String(cepHash, enc);
			posicaoRegHash = new String(posicaoHash, enc);
			ponteiroRegHash = new String(ponteiroHash, enc);

		}

		int valor = Integer.valueOf(posicaoRegHash);
		registro.seek(300 * Integer.valueOf(valor));

		e.leEndereco(registro);

		System.out.println("Bairro: " + e.getBairro());
		System.out.println("Rua: " + e.getLogradouro());
		System.out.println("Numero Colisao " + colisaoSoma);

		hash.close();

	}
}
