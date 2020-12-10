package tp2;


import java.util.concurrent.Semaphore;

public class DesenhaQuadrado extends Comportamento {

	private int dimLado;
	private int direcao;
	
	public DesenhaQuadrado(BufferCircular buffer, Semaphore sReady) {
		super(buffer, sReady);
	}
	
	public void desenha(int dimLado, int direcao) {
		this.dimLado = dimLado;
		this.direcao = direcao;
		haTrabalho.release();
		estado = ESCREVER_FORMA;
	}

	protected void desenharForma() {
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
	}
}
