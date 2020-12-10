package tp2;

public class Reta extends Mensagem{
	
	int dist;
	
	public Reta(int dist) {
		super(TIPO_MENSAGEM.RETA, String.format("Reta (lado %d) \n", dist));
		this.dist = dist;
	}

	public int getDist() {
		return this.dist;
	}
}
