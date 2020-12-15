package tp2;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorRobot implements Runnable {
    public static final int CONSTANTE_DO_CARRO_90 = 5; // Time(millis) that takes robot to turn 90 degrees


    GUIServidor gui;

    BufferCircular buffer;
    RobotDesenhador robot;

    AtomicBoolean running = new AtomicBoolean(false);
    Thread worker;

    public ServidorRobot(BufferCircular buffer, RobotDesenhador robot) {
        this.gui = new GUIServidor();
        this.buffer = buffer;
        this.robot = robot;

    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }

    /**
     * Stops ServidorRobot by interrupting any
     * thread currently awaiting lock.
     */
    public void stop() {

        running.set(false);
        worker.interrupt();
    }


    /**
     * TODO gui prints
     * TODO sleep while car is executing task (here?)
     */
    public void run() {

        running.set(true);
        while (running.get()) {
            //State RUNNING
            stateRead();
        }

        // Cleanup gui
        gui.dispose();
        System.out.println("Stopped Robot Server");

    }


    private void stateRead() {
        Mensagem mensagem = buffer.getMensagem();
        int waitTime = 0;

        if (mensagem == null) return;
//            System.out.println("Read message: " + mensagem.getTipo());

        switch (mensagem.getTipo()) {

            case OPEN:
                robot.OpenEV3(((OpenEV3) mensagem).getRobotName());
                break;
            case CLOSE:
                robot.CloseEV3();
                break;
            case RETA: {
                Reta msg = (Reta) mensagem;
                robot.Reta(msg.getDist());
                waitTime = contas(msg.getDist());
                break;
            }
            case CURVA_ESQ: {
                CurvarEsquerda msg = (CurvarEsquerda) mensagem;
                robot.CurvarEsquerda(msg.getRaio(), msg.getAngulo());
                waitTime = contasCurva(msg.getRaio(), msg.getAngulo());
                break;
            }
            case CURVA_DIR: {
                CurvarDireita msg = (CurvarDireita) mensagem;
                robot.CurvarDireita(msg.getRaio(), msg.getAngulo());
                waitTime = contasCurva(msg.getRaio(), msg.getAngulo());
                break;
            }
            case PARAR:
                robot.Parar(((Parar) mensagem).getAssincrono());
                break;
            default:
                System.out.println("Unknown message type");
                return;
        }
        gui.printCommand(mensagem);

        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            System.out.println("Failed to wait for robot: " + e.getMessage());
            if (!running.get()) System.out.println("ServidorRobot is shutting down");
        }
    }


    public static int contasCurva(float raio, float angulo) {
        if (raio == 0) {
            return (int) Math.ceil(angulo * CONSTANTE_DO_CARRO_90);
        }
        float d = (angulo / 360f) * 2 * (float) Math.PI * raio; // math
        return contas(d);
    }

    public static int contas(float dist) {
        return (int) Math.ceil(dist / 30 * 1000); // Wait up to 1 second over the calculated estimate
    }
}
