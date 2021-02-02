import java.util.concurrent.Semaphore;

public class DesenharCirculo extends Comportamento {

	int raio;
	private int direcao;

	public DesenharCirculo(BufferCircular buffer, Semaphore sReady, Semaphore sStartDrawing) {
		super(buffer, sReady, sStartDrawing,"Desenha Círculo");
	}

	public void iniciaDesenho() {
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
			Thread.sleep(getCurveSleepTime(raio, 90));
			cliente.Parar(false);
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