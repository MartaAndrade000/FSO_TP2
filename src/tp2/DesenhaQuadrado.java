package tp2;


import java.util.concurrent.Semaphore;

public class DesenhaQuadrado extends Comportamento {

	private int dimLado;
	private int direcao;

	private Semaphore sStartDrawing;

	public DesenhaQuadrado(BufferCircular buffer, Semaphore sReady, Semaphore sStartDrawing) {
		super(buffer, sReady);
		this.sStartDrawing = sStartDrawing;
	}

	public void desenha() {
		this.dimLado = dimLado;
		this.direcao = direcao;
		haTrabalho.release();
		estado = ESCREVER_FORMA;
	}

	protected void desenharForma() {
		try {
			sStartDrawing.acquire();

			if(direcao == App.DIRECAO_ESQ) {
				for(int i = 0; i<4; i++) {
					cliente.Reta(dimLado);
					cliente.CurvarEsquerda(0, 90);
					cliente.parar(false);
				}
			}
			else {
				for(int i = 0; i<4; i++) {
					cliente.Reta(dimLado);
					cliente.CurvarDireita(0,90);
					cliente.parar(false);
				}
			}
			cliente.parar(false);

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
