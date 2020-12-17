<<<<<<< Updated upstream:src/tp2/ServidorRobot.java
package tp2;
=======
import robot.RobotEV3;
>>>>>>> Stashed changes:src/ServidorRobot.java

public class ServidorRobot extends Thread {

    GUIServidor gui;

    BufferCircular buffer;
    RobotLegoEV3 robot;
//    RobotDesenhador robot;


<<<<<<< Updated upstream:src/tp2/ServidorRobot.java
    public ServidorRobot(BufferCircular buffer, RobotDesenhador robot) {
        this.gui = new GUIServidor();
        this.buffer = buffer;
        this.robot = robot;
=======
//    public ServidorRobot(BufferCircular buffer, RobotDesenhador robot) {
//        this.gui = new GUIServidor();
//        this.buffer = buffer;
//        this.robot = robot;
//        estado = TIPO_ESTADO.LER;
//    }

    public ServidorRobot(BufferCircular buffer, RobotLegoEV3 robot) {
        this.gui = new GUIServidor();
        this.buffer = buffer;
        this.robot = robot;
        estado = TIPO_ESTADO.LER;
>>>>>>> Stashed changes:src/ServidorRobot.java
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
