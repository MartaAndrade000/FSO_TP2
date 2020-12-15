package tp2;


import java.util.concurrent.Semaphore;

import static tp2.ServidorRobot.contas;
import static tp2.ServidorRobot.contasCurva;

public class DesenhaQuadrado extends Comportamento {

	private int dimLado;
	private int direcao;

	private Semaphore sStartDrawing;

	public DesenhaQuadrado(BufferCircular buffer, Semaphore sReady, Semaphore sStartDrawing) {
		super(buffer, sReady, "Desenha Quadrado");
		this.sStartDrawing = sStartDrawing;
	}

	public void desenha() {
		this.dimLado = dimLado;
		this.direcao = direcao;
		haTrabalho.release();
		estado = ESCREVER_FORMA;
	}

	@Override
	protected int getTempoExecucao() {
		return (4 * contas(dimLado)) + (4 * contasCurva(0, 90));
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
