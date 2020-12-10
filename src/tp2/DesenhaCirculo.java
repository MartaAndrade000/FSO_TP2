package tp2;

import java.util.concurrent.Semaphore;

public class DesenhaCirculo extends Comportamento {

	int raio;
	private int direcao;

	public DesenhaCirculo(BufferCircular buffer, Semaphore sReady) {
		super(buffer, sReady);
	}

	public void desenha(int raio, int direcao) {
		this.raio = raio;
		this.direcao = direcao;
		haTrabalho.release();
		estado = ESCREVER_FORMA;
	}

	protected void desenharForma() {
		if (direcao == App.DIRECAO_ESQ) {
			cliente.CurvarEsquerda(raio, 360);
		} else {
			cliente.CurvarDireita(raio, 360);
		}
		cliente.parar(false);
	}
}