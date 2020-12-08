package TP2;

public class CurvarEsquerda extends Mensagem {

	int raio;
	int angulo;
	
	public CurvarEsquerda(int raio, int angulo) {
		super(Mensagem.CURVA_ESQ, String.format("Curva à Esquerda (raio %d, ângulo %d) \n", raio, angulo));
		this.raio = raio;
		this.angulo = angulo;
	}
}
