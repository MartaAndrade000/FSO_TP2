public class ServidorRobot extends Thread {

    //ESTADOS
    public enum TIPO_ESTADO {
        LER, TERMINAR
    }
    private TIPO_ESTADO estado;


    GUIServidor gui;

    BufferCircular buffer;
//    RobotLegoEV3 robot;
    GravarFormas gravador;
    RobotDesenhador robot;


    public ServidorRobot(BufferCircular buffer, RobotDesenhador robot, GravarFormas gravador) {
        this.gui = new GUIServidor();
        this.buffer = buffer;
        this.robot = robot;
        this.gravador = gravador;
        estado = TIPO_ESTADO.LER;

    }

    /**
     * Máquina de estados:
     *
     * LER: Lê as mensagens do buffer
     * TERMINAR: Fecha a gui
     */
    public void run() {
        while (true) {
            switch (estado) {
                case LER:
                    stateRead();
                    break;

                case TERMINAR:
                    // Cleanup gui
                    gui.dispose();
                    return;
            }
        }
    }

    /**
     * Lê a mensagem no buffer
     * Manda a mensagem para o robot
     */
    private void stateRead() {

        // Lê a mensagem no buffer
        Mensagem mensagem = buffer.getMensagem();

        if (mensagem == null) return;

        // Grava a mensagem caso o gravador exista
        if (this.gravador != null) this.gravador.recordCommand(mensagem);

        // Consoante o tipo da mensagem
        // manda para o robot o comando correspondente ao tipo
        switch (mensagem.getTipo()) {

            case RETA: {
                MsgReta msg = (MsgReta) mensagem;
                robot.Reta(msg.getDist());
                break;
            }
            case CURVA_ESQ: {
                MsgCurvarEsquerda msg = (MsgCurvarEsquerda) mensagem;
                robot.CurvarEsquerda(msg.getRaio(), msg.getAngulo());
                break;
            }
            case CURVA_DIR: {
                MsgCurvarDireita msg = (MsgCurvarDireita) mensagem;
                robot.CurvarDireita(msg.getRaio(), msg.getAngulo());
                break;
            }
            case PARAR:
                robot.Parar(((MsgParar) mensagem).getAssincrono());
                break;
            default:
                System.out.println("Unknown message type");
                return;
        }
        gui.printCommand(mensagem);
    }

    /**
     * Muda o estado para terminar
     */
    public void terminaServidor() {
        System.out.println("Terminou Servidor");
        estado = TIPO_ESTADO.TERMINAR;
    }
}
