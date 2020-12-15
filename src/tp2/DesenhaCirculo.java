package tp2;

import java.util.concurrent.Semaphore;

public class DesenhaCirculo extends Comportamento {

	int raio;
	private int direcao;
	private Semaphore sStartDrawing;

	public DesenhaCirculo(BufferCircular buffer, Semaphore sReady, Semaphore sStartDrawing) {
		super(buffer, sReady, "Desenha Círculo");
		this.sStartDrawing = sStartDrawing;
	}

	public void desenha() {
		this.raio = raio;
		this.direcao = direcao;
		haTrabalho.release();
		estado = ESCREVER_FORMA;
	}
	protected void desenharForma() {
		try {
			sStartDrawing.acquire();
			if (direcao == App.DIRECAO_ESQ) {
				cliente.CurvarEsquerda(raio, 360);
			} else {
				cliente.CurvarDireita(raio, 360);
			}
			cliente.parar(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}

	public void setDirecao(int direcao) {
		this.direcao = direcao;
	}
}