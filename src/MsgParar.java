public class MsgParar extends Mensagem {

	boolean assincrono;
	public MsgParar(boolean assincrono) {
		super(TIPO_MENSAGEM.PARAR, String.format("Parar (%s) \n \n", assincrono));
		this.assincrono = assincrono;
	}

	public boolean getAssincrono() {
		return this.assincrono;
	}

}
