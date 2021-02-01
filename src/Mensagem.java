public abstract class Mensagem {
	
	protected TIPO_MENSAGEM tipo;
	protected String texto;
	
	//Tipos de Mensagem
	enum TIPO_MENSAGEM {
		RETA,
		CURVA_ESQ,
		CURVA_DIR,
		PARAR
		}

	public Mensagem(TIPO_MENSAGEM tipo, String texto) {
		this.tipo  = tipo;
		this.texto = texto;
	}

	public TIPO_MENSAGEM getTipo() {
		return tipo;
	}

	@Override
	public String toString() {
		return "Msg: " + texto;
	}
}
