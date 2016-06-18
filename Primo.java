package br.com.trabalhohash;

public class Primo {

	
static public  boolean ePrimo(int numero) {
	    for (int divisor = 2; divisor <= numero / 2; divisor++) {
	        if (numero % divisor == 0) {
	            return false;
	        }
	    }
	    return true;
	}
	
	
	static public int buscaPrimoProximo(int _numero) {
		
		while (!ePrimo(_numero))
		      _numero++;
		return _numero;
	}
	
	static public int buscaPrimoAnterior(int _numero) {
		
		while (!ePrimo(_numero))
		      _numero--;
		return _numero;
	}
	
	
	
}
