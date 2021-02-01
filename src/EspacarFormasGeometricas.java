import java.util.concurrent.Semaphore;

public class EspacarFormasGeometricas extends Comportamento {

	private int dist;
	private Comportamento nextShape;
	
	public EspacarFormasGeometricas(BufferCircular buffer, Semaphore sMutex, Semaphore sStartDrawing) {
		super(buffer, sMutex, sStartDrawing, "Espaçar Formas Geométricas");
		this.sStartDrawing = sStartDrawing;
	}
	
	public void desenha(int lastDim, int nextDim, Comportamento nextShape) {
		this.nextShape = nextShape;
		if(lastDim != 0) {
			// distância de espaçamento
			int distEspacamento = 10;
			dist = distEspacamento + lastDim;
			if(nextShape instanceof DesenharCirculo)
				dist += nextDim;
			haTrabalho.release();
			estado = ESCREVER_FORMA;
		}
		// Se não houver nenhuma distância anterior, passa logo para o desenho da forma
		else {
			sStartDrawing.release();
			nextShape.iniciaDesenho();
		}
	}

	protected void desenharForma() throws InterruptedException {
		cliente.Reta(dist);
		Thread.sleep(getSleepTime(dist));
		cliente.Parar(false);
		sStartDrawing.release();
		nextShape.iniciaDesenho();
	}

	@Override
	protected void iniciaDesenho() {
	}
}

