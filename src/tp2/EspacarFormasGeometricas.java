package tp2;

import java.util.concurrent.Semaphore;

public class EspacarFormasGeometricas extends Thread {
	//TODO Fazer sleep entre cada comando
	
	ClienteRobot cliente;
	private Semaphore sReady;
	private boolean podeDesenhar;
	
	// TODO maybe fazer uma forma de alterar isto pela GUIApp?
	private final int distEspacamento = 10; // distância de espaçamento
	private int dist;
	
	public EspacarFormasGeometricas(BufferCircular buffer, Semaphore sReady) {
		this.cliente = new ClienteRobot(buffer);
		this.sReady = sReady;
		this.podeDesenhar = false;
	}
	
	public void desenha(int dist) {
		this.dist = dist + distEspacamento;
		this.podeDesenhar = true;
	}
	
	public void run() {
		while(true) {
			try {
				sReady.acquire();
				
				if(podeDesenhar) {
					cliente.Reta(dist);
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

