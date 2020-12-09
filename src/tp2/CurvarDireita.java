package tp2;

public class CurvarDireita extends Mensagem {

	int raio;
	int angulo;
	
	public CurvarDireita(int raio, int angulo) {
		super(TIPO_MENSAGEM.CURVA_DIR, String.format("Curva à Direita (raio %d, ângulo %d) \n", raio, angulo));
		this.raio = raio;
		this.angulo = angulo;
	}

	public int getRaio() {
		return this.raio;
	}
	public int getAngulo() {
		return this.angulo;
	}
}
