package TP2;

import java.util.concurrent.Semaphore;

public class DesenhaQuadrado extends Thread {
	
	// TODO Fazer sleep entre cada comando
	// TODO eu falei com o prof sobre a questão do tempo de uma rotação de rotação de raio 0 
	// e é o mesmo tempo que percorrer 5.5cm
	
	private ClienteRobot cliente;
	private Semaphore sReady;
	private boolean podeDesenhar;
	
	private int dimLado;
	private int direcao;
	
	public DesenhaQuadrado(BufferCircular buffer, Semaphore sReady) {
		this.cliente = new ClienteRobot(buffer);
		this.sReady = sReady;
		this.podeDesenhar = false;
	}
	
	public void desenha(int dimLado, int direcao) {
		this.dimLado = dimLado;
		this.direcao = direcao;
		this.podeDesenhar = true;
	}

	public void run() {
		while(true) {
			try {
				sReady.acquire();
				
				if(podeDesenhar) {
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
