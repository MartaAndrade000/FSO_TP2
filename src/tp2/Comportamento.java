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
    protected static final int ESCREVER_FORMA = 1;
    
    protected ClienteRobot cliente;
    protected Semaphore sMutex;
    protected Semaphore haTrabalho;
//  protected boolean podeDesenhar;

    public Comportamento(BufferCircular buffer, Semaphore sMutex, String tipoCliente) {
        this.cliente = new ClienteRobot(buffer, tipoCliente);
        this.sMutex = sMutex;
        haTrabalho = new Semaphore(0);
//        this.podeDesenhar = false;
        estado = ESPERAR;
    }

    public void run() {
        while(true) {

                switch(estado) {
                    case ESPERAR:
                        try {
                            haTrabalho.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;

                    case ESCREVER_FORMA:
                        try {
                            sMutex.acquire();
                            desenharForma();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finally {
                            sMutex.release();
                        }

                        if(estado == ESCREVER_FORMA) {
                            estado = ESPERAR;
                            break;
                        }
                }
        }
    }

    protected abstract void desenharForma();

    protected abstract void desenha();
}


