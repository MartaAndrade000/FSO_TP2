public class MsgCurvarEsquerda extends Mensagem {

	int raio;
	int angulo;
	
	public MsgCurvarEsquerda(int raio, int angulo) {
		super(TIPO_MENSAGEM.CURVA_ESQ, String.format("Curva à Esquerda (raio %d, ângulo %d) \n", raio, (int)angulo));
		this.raio = raio;
		this.angulo = angulo;
	}

	public int getRaio() {
		return raio;
	}

	public int getAngulo() {
		return angulo;
	}
}
