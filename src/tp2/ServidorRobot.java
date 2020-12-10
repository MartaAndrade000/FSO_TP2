package tp2;

public class ServidorRobot extends Thread {

    GUIServidor gui;

    BufferCircular buffer;
    RobotDesenhador robot;

    public ServidorRobot(BufferCircular buffer, RobotDesenhador robot) {
        this.gui = new GUIServidor();
        this.buffer = buffer;
        this.robot = robot;
    }

    /**
     *
     */
    public void run() {

		while (true) {

            Mensagem mensagem = buffer.getMensagem();

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
                    robot.CurvarEsquerda(((CurvarEsquerda) mensagem).getRaio(), ((CurvarEsquerda) mensagem).getAngulo());
                    break;
                case CURVA_DIR:
                    robot.CurvarDireita(((CurvarDireita) mensagem).getRaio(), ((CurvarDireita) mensagem).getAngulo());
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
}
