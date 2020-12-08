package tp2;

public class Parar extends Mensagem {

	boolean assincrono;
	public Parar(boolean assincrono) {
		super(Mensagem.PARAR, String.format("Parar (%s) \n \n", assincrono));
		this.assincrono = assincrono;
	}

}
