package tp2;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorRobot implements Runnable {

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

    public void interrupt() {
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

            Mensagem mensagem = buffer.getMensagem();

            if (mensagem == null) continue;
//            System.out.println("Read message: " + mensagem.getTipo());

            switch (mensagem.getTipo()) {

                case OPEN:
                    robot.openEV3(((OpenEV3) mensagem).getRobotName());
                    break;
                case CLOSE:
                    robot.closeEV3();
                    break;
                case RETA:
                    robot.reta(((Reta) mensagem).getDist());
                    break;
                case CURVA_ESQ:
                    robot.curvarEsq(((CurvarEsquerda) mensagem).getRaio(), ((CurvarEsquerda) mensagem).getAngulo());
                    break;
                case CURVA_DIR:
                    robot.curvarDir(((CurvarDireita) mensagem).getRaio(), ((CurvarDireita) mensagem).getAngulo());
                    break;
                case PARAR:
                    robot.parar(((Parar) mensagem).getAssincrono());
                    break;
                default:
                    System.out.println("Unknown message type");
            }
            gui.mostraComando(mensagem);
        }

        System.out.println("Stopped Robot Server");

    }
}
