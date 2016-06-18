package br.com.trabalhohash;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import br.com.buscaendereco.Endereco;

public class TesteHashSegundo {
	static public void main(String[] args) throws NumberFormatException, IOException {

		// String path = "/media/aluno/FELIPE_PR/";
		String path = "D:/";
		int cont = 0;
		int contHash = 0;
		Endereco e = new Endereco();
		int hash;
		int auxHash;
		int auxHashPonteiro = 0;
		int primo;		
		
		
		//Arquivo que contera os registro dos ceps para criar o arquivo de hash
		RandomAccessFile f = new RandomAccessFile(path + "cep_colisao.dat", "r");
		//Arquivo que armazenara os registros de hash
		RandomAccessFile fHash = new RandomAccessFile(path + "hash_colisao.dat", "rw");
		// Instaciando classe de um tipo de hash
		HashByte hb = new HashByte();
				

		// numero para o calculo do hash
		primo = Primo.buscaPrimoAnterior((int)f.length()/300);
		
		
		// Setando o arquivo para receber os registros
		for (int i = 0; i < 2*f.length()/300; i++) {
			fHash.seek(i * 24);
			fHash.writeBytes(String.format("%024d", 0));
		}

		System.out.println("O tamanho do arquivo hash zerado ficou com " + fHash.length() + " Bytes");

		
		//Loop para a leitura dos registros e escrita no arquivo hahs
		while (f.getFilePointer() < f.length()) // para Detectar EOF
		{
			//Vizualicao da porcentagem ja executada do algoritmo
			System.out.println("Porcentagem do algoritmo "+((double)f.getFilePointer()/(double)f.length())*100);
			
			
			// Saltos buscando os registros
			f.seek(300 * cont);
			cont++;
			e.leEndereco(f);

			

			// Valor de Hash gerado pelo tamanho do CEP passado para o algoritmo de encotrar um primo proximo 
			hash = Integer.valueOf(e.getCep()) % primo;
			fHash.seek(hash * 24);
			hb.leHashByte(fHash);

			// Caso o valor do espaco seja vazio preencher com o CEP
			// a posicao sera o valor do hash
			if (hb.getCepRegHash().equals("00000000")) {
				auxHash = hash;
				//voltar a posicao pois ele andou com leitura
				fHash.seek(hash * 24);
				fHash.writeBytes(e.getCep());
				fHash.writeBytes(String.format("%09d", cont));
				fHash.writeBytes("0000000");
			}
			// Se ocorreu uma colisao
			else {

			
				// guardar o valor do hash
				auxHash = hash;
				// Enquanto o valor do capo de registro da posicao do cep nao e vazio repita
				while (!hb.getCepRegHash().equals("00000000")) {
					hash++;
					fHash.seek(hash * 24);
					hb.leHashByte(fHash);
					// Caso o valor passe do tamanho possivel reinicie

				} // FIM WHILE

				
				//voltar posicao pois houve uma leitura 
				fHash.seek(auxHash * 24);
				hb.leHashByte(fHash);
				if (!hb.getPonteiroRegHash().equals("0000000")) {

					auxHashPonteiro = Integer.parseInt(hb.getPonteiroRegHash());
				} else {

					auxHashPonteiro = 0;
				}

				fHash.seek(auxHash * 24 + 17);
				fHash.writeBytes(String.format("%07d", hash));

				try {
					fHash.seek(hash * 24);
					fHash.writeBytes(e.getCep());
					fHash.writeBytes(String.format("%09d", cont));
					fHash.writeBytes(String.format("%07d", auxHashPonteiro));

				} catch (Exception ex) {
					System.out.println("ERRO " + hash);
					System.out.println(e.getCep());
				}
			} // FIM ELSE

	
		}

		

		f.close();
		fHash.close();
		System.out.print("terminou");

	}
}