package tp2;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorRobot implements Runnable {


    enum State {
        READ,
        STOP
    }

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

        // State STOPPED

        // Cleanup gui
        gui.dispose();
        System.out.println("Stopped Robot Server");

    }

    private void stateTest() {

    }


    private void stateRead() {
        Mensagem mensagem = buffer.getMensagem();

        if (mensagem == null) return;
//            System.out.println("Read message: " + mensagem.getTipo());

        switch (mensagem.getTipo()) {

            case OPEN:
                robot.OpenEV3(((OpenEV3) mensagem).getRobotName());
                break;
            case CLOSE:
                robot.CloseEV3();
                break;
            case RETA:
                robot.Reta(((Reta) mensagem).getDist());
                break;
            case CURVA_ESQ:
                robot.CurvarEsq(((CurvarEsquerda) mensagem).getRaio(), ((CurvarEsquerda) mensagem).getAngulo());
                break;
            case CURVA_DIR:
                robot.CurvarDir(((CurvarDireita) mensagem).getRaio(), ((CurvarDireita) mensagem).getAngulo());
                break;
            case PARAR:
                robot.Parar(((Parar) mensagem).getAssincrono());
                break;
            default:
                System.out.println("Unknown message type");
        }
        gui.printCommand(mensagem);
    }
}
