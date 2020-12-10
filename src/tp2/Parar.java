package tp2;

public class Parar extends Mensagem {

	boolean assincrono;
	public Parar(boolean assincrono) {
		super(TIPO_MENSAGEM.PARAR, String.format("Parar (%s) \n \n", assincrono));
		this.assincrono = assincrono;
	}

	public boolean getAssincrono() {
		return this.assincrono;
	}

}
