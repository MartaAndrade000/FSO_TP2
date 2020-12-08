package tp2;

import java.util.concurrent.Semaphore;

public class DesenhaCirculo extends Thread {

	ClienteRobot cliente;
	private Semaphore sReady;
	private boolean podeDesenhar;
	
	int raio;
	private int direcao;
	
	public DesenhaCirculo(BufferCircular buffer, Semaphore sReady) {
		this.cliente = new ClienteRobot(buffer);
		this.sReady = sReady;
		this.podeDesenhar = false;

	}
	
	public void desenha(int raio, int direcao) {
		this.raio = raio;
		this.direcao = direcao;
		this.podeDesenhar = true;
	}

	public void run() {
		while(true) {
			try {
				sReady.acquire();
				
				if(podeDesenhar) {
					if(direcao == App.DIRECAO_ESQ) {
						cliente.CurvarEsquerda(raio, 360);
					}
					else {
						cliente.CurvarDireita(raio, 360);
					}
					cliente.parar(false);
					podeDesenhar = false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally {
				sReady.release();
	        }
		}
 	}
}
