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
	
	public void desenha(int dimLado, int direcao) {
		this.dimLado = dimLado;
		this.direcao = direcao;
		haTrabalho.release();
		estado = ESCREVER_FORMA;
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
				cliente.Reta(dimLado);
				cliente.CurvarEsquerda(0,90);

				cliente.Reta(dimLado);
				cliente.CurvarEsquerda(0,90);

				cliente.Reta(dimLado);
				cliente.CurvarEsquerda(0,90);

				cliente.Reta(dimLado);
				cliente.CurvarEsquerda(0,90);
			}
			else {
				cliente.Reta(dimLado);
				cliente.CurvarDireita(0,90);

				cliente.Reta(dimLado);
				cliente.CurvarDireita(0,90);

				cliente.Reta(dimLado);
				cliente.CurvarDireita(0,90);

				cliente.Reta(dimLado);
				cliente.CurvarDireita(0,90);
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
