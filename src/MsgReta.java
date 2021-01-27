public class MsgReta extends Mensagem{
	
	int dist;
	
	public MsgReta(int dist) {
		super(TIPO_MENSAGEM.RETA, String.format("Reta (lado %d) \n", dist));
		this.dist = dist;
	}

	public int getDist() {
		return this.dist;
	}
}
