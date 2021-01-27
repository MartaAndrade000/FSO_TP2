import java.util.concurrent.Semaphore;

public class DesenharQuadrado extends Comportamento {

	private App app;
	private int dimLado;
	private int direcao;
	private final Semaphore sStartDrawing;

	public DesenharQuadrado(App app, BufferCircular buffer, Semaphore sReady, Semaphore sStartDrawing) {
		super(buffer, sReady, sStartDrawing, "Desenha Quadrado");
		this.app = app;
		this.sStartDrawing = sStartDrawing;
	}

	public void desenha() {
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
