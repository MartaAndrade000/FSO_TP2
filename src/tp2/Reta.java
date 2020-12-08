package TP2;

public class Reta extends Mensagem{
	
	int dist;
	
	public Reta(int dist) {
		super(Mensagem.RETA, String.format("Reta (lado %d) \n", dist));
		this.dist = dist;
	}
}
