package TP2;

public abstract class Mensagem {
	
	protected int tipo;
	protected String texto;
	
	//Tipos de Mensagem
	public static final int OPEN 	  = 0;
	public static final int CLOSE     = 1;
	public static final int RETA 	  = 2;
	public static final int CURVA_ESQ = 3;
	public static final int CURVA_DIR = 4;
	public static final int PARAR  	  = 5;

	public Mensagem(int tipo, String texto) {
		this.tipo  = tipo;
		this.texto = texto;
	}

	public String getTexto() {
		return texto;
	}

	public int getTipo() {
		return tipo;
	}
	
}
