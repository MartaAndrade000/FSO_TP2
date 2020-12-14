package tp2;

import java.util.concurrent.Semaphore;

public class EspacarFormasGeometricas extends Comportamento {

	private final int distEspacamento = 10; // distância de espaçamento
	private int dist;
	private Semaphore sStartDrawing;
	private Comportamento nextShape;
	
	public EspacarFormasGeometricas(BufferCircular buffer, Semaphore sMutex, Semaphore sStartDrawing) {
		super(buffer, sMutex);
		this.sStartDrawing = sStartDrawing;
	}
	
	public void desenha(int lastDim, int nextDim, Comportamento nextShape) {
		this.nextShape = nextShape;
		if(lastDim != 0) {
			dist = distEspacamento + lastDim;
			if(nextShape instanceof DesenhaCirculo)
				dist += nextDim;
			haTrabalho.release();
			estado = ESCREVER_FORMA;
		}
		// Se não houver nenhuma distância anterior, passa logo para o desenho da forma
		else {
//			haTrabalho.release();
			sStartDrawing.release();
			nextShape.desenha();
		}
	}

	protected void desenharForma() {
		cliente.Reta(dist);
		cliente.parar(false);
		sStartDrawing.release();
		nextShape.desenha();
	}

	protected void desenha() { }
}

