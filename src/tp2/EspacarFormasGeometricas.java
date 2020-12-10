package tp2;

import java.util.concurrent.Semaphore;

public class EspacarFormasGeometricas extends Comportamento {

	// TODO maybe fazer uma forma de alterar isto pela GUIApp?
	private final int distEspacamento = 10; // distância de espaçamento
	private int dist;
	
	public EspacarFormasGeometricas(BufferCircular buffer, Semaphore sReady) {
		super(buffer, sReady);
	}
	
	public void desenha(int dist) {
		this.dist = dist + distEspacamento;
		estado = DESENHAR;
	}

	protected void desenharForma() {
		cliente.Reta(dist);
		cliente.parar(false);
	}
}

