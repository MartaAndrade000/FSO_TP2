package tp2;

public class CurvarEsquerda extends Mensagem {

	int raio;
	float angulo;
	
	public CurvarEsquerda(int raio, float angulo) {
		super(TIPO_MENSAGEM.CURVA_ESQ, String.format("Curva à Esquerda (raio %d, ângulo %d) \n", raio, (int)angulo));
		this.raio = raio;
		this.angulo = angulo;
	}

	public int getRaio() {
		return raio;
	}

	public float getAngulo() {
		return angulo;
	}
}
