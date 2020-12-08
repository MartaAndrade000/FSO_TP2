package tp1;

public class Mensagem {

	//Tipos de Mensagem
	public static final int TODAS  = 0;
	public static final int FSO    = 1;
	public static final int ROBOTS = 2;
	public static final int JAVA   = 3;
	
	//Número máximo de chars
	public static final int MAX_LENGTH = 256; 

	//ID da mensagem
	private int id;
	
	//Tipo de Mensagem
	private int tipo;
	
	//Conteúdo da Mensagem
	private String texto;


	/**
	 * Instancia uma mensagem
	 * 
	 * @param ID da mensagem
	 * @param Tipo de Mensagem
	 * @param Conteúdo da Mensagem
	 */
	public Mensagem(int id, int tipo, String texto) {
		this.tipo = tipo;
		this.texto = texto;
		this.id = id;
	}

	
	public String getTexto() {
		return texto;
	}

	public int getId() {
		return id;
	}

	public int getTipoMensagem() {
		return tipo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTipoMensagem(int tipo) {
		this.tipo = tipo;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Override
	public String toString() {
		return String.format("[%d, %d] %s", this.getId(), this.getTipoMensagem(), this.getTexto());
	}
}
