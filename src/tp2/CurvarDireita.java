package tp2;

public class CurvarDireita extends Mensagem {

	int raio;
	float angulo;
	
	public CurvarDireita(int raio, float angulo) {
		super(TIPO_MENSAGEM.CURVA_DIR, String.format("Curva à Direita (raio %d, ângulo %d) \n", raio, (int)angulo));
		this.raio = raio;
		this.angulo = angulo;
	}

	public int getRaio() {
		return this.raio;
	}

	public float getAngulo() {
		return this.angulo;
	}
}
