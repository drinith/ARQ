package br.com.trabalhohash;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import br.com.buscaendereco.Endereco;

public class TesteHash {
	static public void main (String [] args) throws NumberFormatException, IOException{
		
		
		//String path = "/media/aluno/FELIPE_PR/";
				String path = "F:/";
				
				int cont=0;
				int contHash=0;
				Endereco e = new Endereco();
				int hash;
				int auxHash;
				int auxHashPonteiro;
				RandomAccessFile f = new RandomAccessFile(path+"cep.dat", "r");
				RandomAccessFile fHash = new RandomAccessFile(path+"hashReal.dat", "rw");
				HashByte hb= new HashByte();
				
				for (int i = 0; i < 800000; i++) {
					fHash.seek(i*24);
					fHash.writeBytes("000000000000000000000000");
				}
				
				
				
				while( f.getFilePointer() < f.length() ) // para Detectar EOF
				{
					// Saltos buscando os registros
					f.seek(300*cont);
					cont++;
					e.leEndereco(f);

					//Printando a posicao somente para acompanhamento
					

					//Valor de Hash gerado pelo valor do CEP
					hash = Integer.valueOf(e.getCep())%750000 ;
					fHash.seek(hash*24);			
					hb.leHashByte(fHash);
					
					//Caso o valor do espaco seja vazio preencher com o CEP
					//a posicao sera o valor do hash
					if(hb.getCepRegHash().equals("00000000")){
						auxHash=hash;
						fHash.writeBytes(e.getCep());
						fHash.writeBytes(String.format("%09d",cont));
						fHash.writeBytes("0000000");
					}
					//Se ocorreu uma colisao
					else{				
						
						//Printe para acompanhamento das colisoes
						
						//guardar o valor do hash
						auxHash=hash;
						//Preencher o valor do proximo campo vazio
						while(!hb.getCepRegHash().equals("00000000")){
							hash++;
							fHash.seek(hash*24);
							hb.leHashByte(fHash);
							//Caso o valor passe do tamanho possivel reinicie
							
						}//FIM WHILE
						
						
						//*** AQUI VOU TER QUE OLHAR SE O PONTEIRO Tï¿½ PRENCHIDO PARA ENCONTRAR A TRILHA DAS COLISOES
						if (hb.getPonteiroRegHash()!="0000000"){
							
							auxHashPonteiro=Integer.parseInt(hb.getPonteiroRegHash());
						}
						else{
							
							auxHashPonteiro=0;
						}
						
						fHash.seek(auxHash*24+17);
						fHash.writeBytes(String.format("%07d", hash));
						
						
						try{
							fHash.seek(hash*24);
							fHash.writeBytes(e.getCep());
							fHash.writeBytes(String.format("%09d",cont));
							fHash.writeBytes(String.format("%07d",auxHashPonteiro));
							
							
						}
						catch(Exception ex){
							System.out.println("ERRO "+ hash);
							System.out.println(e.getCep());
						}
					}//FIM ELSE



					//fHash.writeBytes(String.format("%06d",new Object [] {f.getFilePointer()}));
					//*** Saida nao esta batendo 

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