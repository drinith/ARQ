package br.com.trabalhohash;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class TesteHashList {
	public static void main(String [] args) throws NumberFormatException, IOException{

		
		//String path = "/media/aluno/FELIPE_PR/";
		String path = "F:/";
		
		int cont=0;
		int contHash=0;
		Hash[] tabela = new Hash[750000];
		Endereco e = new Endereco();
		int hash;
		int auxHash;
		int auxHashPonteiro;
		RandomAccessFile f = new RandomAccessFile(path+"cep.dat", "r");
		RandomAccessFile fHash = new RandomAccessFile(path+"hash.dat", "rw");
		
		
		while( f.getFilePointer() < f.length() ) // para Detectar EOF
		{
			// Saltos buscando os registros
			f.seek(300*cont);
			cont++;
			e.leEndereco(f);

			//Printando a posicao somente para acompanhamento
			System.out.println(f.getFilePointer());

			//Valor de Hash gerado pelo valor do CEP
			hash = Integer.valueOf(e.getCep())% tabela.length;
			
			
			
			//Caso o valor do espaco seja vazio preencher com o CEP
			//a posicao sera o valor do hash
			if(tabela[hash]==null){
				auxHash=hash;
				tabela[hash]= new Hash(e.getCep(),cont,0);
			}
			//Se ocorreu uma colisao
			else{				
				
				//Printe para acompanhamento das colisoes
				System.out.println("colisao");
				//guardar o valor do hash
				auxHash=hash;
				//Preencher o valor do proximo campo vazio
				while(tabela[hash]!=null){
					hash++;
					//Caso o valor passe do tamanho possivel reinicie
					if(hash>tabela.length){
						hash=0;
					}
				}//FIM WHILE
				
				
				//*** AQUI VOU TER QUE OLHAR SE O PONTEIRO Tï¿½ PRENCHIDO PARA ENCONTRAR A TRILHA DAS COLISOES
				if (tabela[auxHash].getPonteiro()!=0){
					
					auxHashPonteiro=tabela[auxHash].getPonteiro();
				}
				else{
					
					auxHashPonteiro=0;
				}
				
				
				tabela[auxHash].setPonteiro(hash);
				
				try{
					tabela[hash]= new Hash(e.getCep(),cont,auxHashPonteiro);
				}
				catch(Exception ex){
					System.out.println("ERRO "+ hash);
					System.out.println(e.getCep());
				}
			}//FIM ELSE



			//fHash.writeBytes(String.format("%06d",new Object [] {f.getFilePointer()}));
			//*** Saida nao esta batendo 

		}

		for (int i=0; i< tabela.length;i++) {
			fHash.seek(24*contHash);
			contHash++;
			try{
				fHash.writeBytes(tabela[i].getCep());
				fHash.writeBytes(String.format("%09d",tabela[i].getEndereco()));
				fHash.writeBytes(String.format("%07d", tabela[i].getPonteiro()));
				
					
			}catch(Exception ex){
				
				fHash.writeBytes("000000000000000000000000");
			}
		}

		/* Testar escrevendo com o campo CEP,ENDERECO ARQ CEP,PONTEIRO
		 * escrever a colisao reapontando para o lugar certo
		 * Implementar a leitura do arquivo   
		 * pensar na questão relacionada de sempre escrever no fim
		 */
		
		

		f.close();
		fHash.close();
		System.out.print("terminou");
	}
}