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
		RandomAccessFile f = new RandomAccessFile(path + "cep_colisao.dat", "r");
		RandomAccessFile fHash = new RandomAccessFile(path + "hashcolisao.dat", "rw");
		HashByte hb = new HashByte();

		for (int i = 0; i < 2 * f.length() / 24; i++) {
			fHash.seek(i * 24);
			fHash.writeBytes(String.format("%024d", 0));
		}

		System.out.println("O tamanho do arquivo hash zerado ficou com " + fHash.length() + " Bytes");

		while (f.getFilePointer() < f.length()) // para Detectar EOF
		{
			// Saltos buscando os registros
			f.seek(300 * cont);
			cont++;
			e.leEndereco(f);

			// Printando a posicao somente para acompanhamento

			// Valor de Hash gerado pelo valor do CEP
			hash = Integer.valueOf(e.getCep()) % 13;
			fHash.seek(hash * 24);
			System.out.println("Posicao do arquivo Hash " + fHash.getFilePointer());
			hb.leHashByte(fHash);

			// Caso o valor do espaco seja vazio preencher com o CEP

			if (hb.getCepRegHash().equals("00000000")) {

				fHash.seek(hash * 24);
				System.out.println("Posicao do arquivo Hash " + fHash.getFilePointer());
				fHash.writeBytes(e.getCep() + String.format("%09d", cont) + "0000000");

			}
			// Se ocorreu uma colisao
			else {

				// Printe para acompanhamento das colisoes

				// guardar o valor do hash
				auxHash = hash;

				// Caso ponteiro seja vazio vai procurar um proximo espaco para
				// escrever a linha
				if (hb.getPonteiroRegHash().equals("0000000")) {
					hash++;
					fHash.seek(hash * 24);
					hb.leHashByte(fHash);
					// ande enquanto CEP nao vazio
					while (!hb.getCepRegHash().equals("00000000")) {
						hash++;
						fHash.seek(hash * 24);
						hb.leHashByte(fHash);
						fHash.seek(hash * 24);
						System.out.println("Posicao do arquivo Hash " + fHash.getFilePointer());

					}

					// campo novo encontrado sendo preenchido
					fHash.seek(hash * 24);
					fHash.writeBytes(e.getCep());
					fHash.writeBytes(String.format("%09d", cont));
					fHash.writeBytes(String.format("%07d", 0));
					// escrevendo o ponteiro do antigo para o novo
					fHash.seek(auxHash * 24 + 17);
					fHash.writeBytes(String.format("%07d", hash));

				} // FIM caso o ponteiro nao seja vazio
				else {
					fHash.seek(24 * Integer.valueOf(hb.getPonteiroRegHash()));
					hb.leHashByte(fHash);
					if (hb.getPonteiroRegHash().equals("0000000")) {
						hash++;
						fHash.seek(hash * 24);
						hb.leHashByte(fHash);
						// ande enquanto CEP nao vazio
						while (!hb.getCepRegHash().equals("00000000")) {
							hash++;
							fHash.seek(hash * 24);
							hb.leHashByte(fHash);
							fHash.seek(hash * 24);
							System.out.println("Posicao do arquivo Hash " + fHash.getFilePointer());

						}

						// campo novo encontrado sendo preenchido
						fHash.seek(hash * 24);
						fHash.writeBytes(e.getCep());
						fHash.writeBytes(String.format("%09d", cont));
						fHash.writeBytes(String.format("%07d", 0));
						// escrevendo o ponteiro do antigo para o novo
						fHash.seek(auxHash * 24 + 17);
						fHash.writeBytes(String.format("%07d", hash));

					}

					// FIM WHILE

					// *** AQUI VOU TER QUE OLHAR SE O PONTEIRO Tï¿½ PRENCHIDO
					// PARA
					// ENCONTRAR A TRILHA DAS COLISOES

					// FIM ELSE

					// fHash.writeBytes(String.format("%06d",new Object []
					// {f.getFilePointer()}));
					// *** Saida nao esta batendo
					System.out.println("Posicao do arquivo original cep " + f.getFilePointer());
				}

				/*
				 * Testar escrevendo com o campo CEP,ENDERECO ARQ CEP,PONTEIRO
				 * escrever a colisao reapontando para o lugar certo Implementar
				 * a leitura do arquivo pensar na questão relacionada de sempre
				 * escrever no fim
				 */

				f.close();
				fHash.close();
				System.out.print("terminou");

			}
		}
	}
}