package tp2;

import java.util.concurrent.Semaphore;

public abstract class Comportamento extends Thread {
    // TODO fazer super behaviour
    // TODO Fazer sleep entre cada comando
    // TODO eu falei com o prof sobre a questão do tempo de uma rotação de raio 0
    // e é o mesmo tempo que percorrer 5.5cm

    protected int estado;

    //ESTADOS
    protected static final int ESPERAR = 0;
    protected static final int DESENHAR  = 1;
    
    protected ClienteRobot cliente;
    protected Semaphore sReady;
//    protected boolean podeDesenhar;

    public Comportamento(BufferCircular buffer, Semaphore sReady) {
        this.cliente = new ClienteRobot(buffer);
        this.sReady = sReady;
//        this.podeDesenhar = false;
        estado = ESPERAR;
    }

    public void run() {
        while(true) {

            try {
                sReady.acquire();

                switch(estado) {
                    case ESPERAR:
                        break;

                    case DESENHAR:
                        desenharForma();
                        estado = ESPERAR;
                        break;

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                sReady.release();
            }
        }
    }

    protected abstract void desenharForma();
}


