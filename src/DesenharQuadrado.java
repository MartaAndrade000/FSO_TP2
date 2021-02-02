import java.util.concurrent.Semaphore;

public class DesenharQuadrado extends Comportamento {

	private int dimLado;
	private int direcao;

	public DesenharQuadrado(BufferCircular buffer, Semaphore sReady, Semaphore sStartDrawing) {
		super(buffer, sReady, sStartDrawing, "Desenha Quadrado");
	}

	public void iniciaDesenho() {
		haTrabalho.release();
		estado = ESCREVER_FORMA;
	}

	protected void desenharForma() {
		try {
			sStartDrawing.acquire();
			if(direcao == App.DIRECAO_ESQ) {
				for(int i = 0; i<4; i++) {
					cliente.Reta(dimLado);
					cliente.Parar(false);
					Thread.sleep(getSleepTime(dimLado));

					cliente.CurvarEsquerda(0, 90);
					cliente.Parar(false);
					Thread.sleep(getCurveSleepTime(0, 90));
				}
			}
			else {
				for(int i = 0; i<4; i++) {
					cliente.Reta(dimLado);
					cliente.Parar(false);
					Thread.sleep(getSleepTime(dimLado));

					cliente.CurvarDireita(0,90);
					cliente.Parar(false);
					Thread.sleep(getCurveSleepTime(0, 90));
				}
			}
			cliente.Parar(false);
			acabouDesenho = true;

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setDimLado(int dimLado) {
		this.dimLado = dimLado;
	}

	public void setDirecao(int direcao) {
		this.direcao = direcao;
	}
}
