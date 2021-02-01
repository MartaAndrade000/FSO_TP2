import java.util.concurrent.Semaphore;

public abstract class Comportamento extends Thread {
    // Time(millis) that takes robot to turn 90 degrees
    public static final int CONSTANTE_DO_CARRO_360 = 2500;

    protected int estado;

    //ESTADOS
    protected static final int ESPERAR = 0;
    protected static final int ESCREVER_FORMA = 1;
    protected static final int TERMINAR = 2;

    protected ClienteRobot cliente;
    protected Semaphore sMutex;
    protected Semaphore sStartDrawing ;
    protected Semaphore haTrabalho;
    protected boolean acabouDesenho = false;


    public Comportamento(BufferCircular buffer, Semaphore sMutex, Semaphore sStartDrawing, String tipoCliente) {
        this.cliente = new ClienteRobot(buffer, tipoCliente);
        this.sMutex = sMutex;
        this.sStartDrawing = sStartDrawing;
        haTrabalho = new Semaphore(0);
        estado = ESPERAR;
    }

    public void run() {
        while(true) {

                switch(estado) {
                    case TERMINAR:
                        cliente.Parar(true);
                        cliente.gui.dispose();
                        return;
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

    protected abstract void desenharForma() throws InterruptedException;

    protected abstract void iniciaDesenho();

    public boolean isAcabouDesenho() {
        return acabouDesenho;
    }

    public void setAcabouDesenho(boolean acabouDesenho) {
        this.acabouDesenho = acabouDesenho;
    }

    public static int getCurveSleepTime(float raio, float angulo) {
        if (raio == 0) {
            return CONSTANTE_DO_CARRO_360 / 4;
        }
        float d = (angulo / 360f) * 2 * (float) Math.PI * raio; // math
        return getSleepTime(d);
    }

    public static int getSleepTime(float dist) {
        return (int) Math.ceil((dist / 30) * 1000); // Wait up to 1 second over the calculated estimate
    }

    public void terminarComportamento() {
        System.out.println("Terminou Comportamento");
        estado = TERMINAR;
        haTrabalho.release();
    }
}


