public class ServidorRobot extends Thread {

    //ESTADOS
    public enum TIPO_ESTADO {
        LER, TERMINAR, REPLAY
    }
    private TIPO_ESTADO estado;


    GUIServidor gui;

    BufferCircular buffer;
    RobotLegoEV3 robot;
    GravarFormas gravador;
//    RobotDesenhador robot;


    public ServidorRobot(BufferCircular buffer, RobotLegoEV3 robot, GravarFormas gravador) {
        this.gui = new GUIServidor();
        this.buffer = buffer;
        this.robot = robot;
        this.gravador = gravador;
        estado = TIPO_ESTADO.LER;

    }
/*    public ServidorRobot(BufferCircular buffer, RobotDesenhador robot) {
        this.gui = new GUIServidor();
        this.buffer = buffer;
        this.robot = robot;
        estado = TIPO_ESTADO.LER;

    }*/

    public void run() {

        while (true) {
            switch (estado) {
                case LER:
                    stateRead();
                    break;

                case REPLAY:
                    break;

                case TERMINAR:
                    // Cleanup gui
                    gui.dispose();
                    return;
            }
        }
    }

    private void stateRead() {
        Mensagem mensagem = buffer.getMensagem();
        if (mensagem == null) return;

        if (this.gravador != null) this.gravador.recordCommand(mensagem);

        switch (mensagem.getTipo()) {

            case CLOSE:
                robot.CloseEV3();
                break;
            case RETA: {
                Reta msg = (Reta) mensagem;
                robot.Reta(msg.getDist());
                break;
            }
            case CURVA_ESQ: {
                CurvarEsquerda msg = (CurvarEsquerda) mensagem;
                robot.CurvarEsquerda(msg.getRaio(), msg.getAngulo());
                break;
            }
            case CURVA_DIR: {
                CurvarDireita msg = (CurvarDireita) mensagem;
                robot.CurvarDireita(msg.getRaio(), msg.getAngulo());
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
    }


    public void terminaServidor() {
        System.out.println("Terminou Servidor");
        estado = TIPO_ESTADO.TERMINAR;
    }

    public void setEstado(TIPO_ESTADO state) {
        this.estado = state;
    }
}
