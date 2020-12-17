package tp2;


import java.util.concurrent.Semaphore;

public class DesenhaQuadrado extends Comportamento {

	private App app;
	private int dimLado;
	private int direcao;
	private final Semaphore sStartDrawing;

	public DesenhaQuadrado(App app, BufferCircular buffer, Semaphore sReady, Semaphore sStartDrawing) {
		super(buffer, sReady, "Desenha Quadrado");
		this.app = app;
		this.sStartDrawing = sStartDrawing;
	}

	public void desenha() {
		haTrabalho.release();
		estado = ESCREVER_FORMA;
	}

	@Override
	protected int getTempoExecucao() {
		return (4 * contas(dimLado)) + (4 * getContasCurva(0, 90));
	}

	protected void desenharForma() {
		try {
			sStartDrawing.acquire();
			if(direcao == App.DIRECAO_ESQ) {
				for(int i = 0; i<4; i++) {
					cliente.Reta(dimLado);
					Thread.sleep(contas(dimLado));

					cliente.CurvarEsquerda(0, 90);
					Thread.sleep(getContasCurva(0, 90));

					cliente.parar(false);
				}
			}
			else {
				for(int i = 0; i<4; i++) {
					cliente.Reta(dimLado);
					Thread.sleep(contas(dimLado));
					cliente.CurvarDireita(0,90);
					Thread.sleep(getContasCurva(0, 90));
					cliente.parar(false);
				}
			}
			cliente.parar(false);
			acabouDesenho = true;
//			app.gui.setEstadoBtnFormas(true);

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
