package tp2;

import java.util.concurrent.Semaphore;

public class DesenhaCirculo extends Comportamento {

	int raio;
	private int direcao;
	private final Semaphore sStartDrawing;

	public DesenhaCirculo(BufferCircular buffer, Semaphore sReady, Semaphore sStartDrawing) {
		super(buffer, sReady, "Desenha Círculo");
		this.sStartDrawing = sStartDrawing;
	}

	public void desenha() {
		haTrabalho.release();
		estado = ESCREVER_FORMA;
	}

	@Override
	protected int getTempoExecucao() {
		return getContasCurva(raio, 360);
	}

	protected void desenharForma() {
		try {
			sStartDrawing.acquire();
			if (direcao == App.DIRECAO_ESQ) {
				cliente.CurvarEsquerda(raio, 360);
				Thread.sleep(getContasCurva(raio, 90));
			} else {
				cliente.CurvarDireita(raio, 360);
				Thread.sleep(getContasCurva(raio, 90));
			}
			cliente.parar(false);
			acabouDesenho = true;
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