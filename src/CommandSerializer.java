public class CommandSerializer {
    /**
     * Encripta uma mensagem
     *
     * Mensagens:
     *
     * Reta           : 0    dist               tempo
     * Curva Esquerda : 1    raio    angulo     tempo
     * Curva Direita  : 2    raio    angulo     tempo
     * Parar          : 3    0/1                tempo
     */
    public static String serialize(Mensagem m, long elapsed) {
        String msg = "";

        msg += m.getTipo().ordinal();

        switch (m.getTipo()) {

            case RETA:
                msg += "," + ((MsgReta) m).getDist();
                break;
            case CURVA_ESQ:
                msg += "," + ((MsgCurvarEsquerda) m).raio + "," + ((MsgCurvarEsquerda) m).angulo;
                break;
            case CURVA_DIR:
                msg += "," + ((MsgCurvarDireita) m).raio + "," + ((MsgCurvarDireita) m).angulo;
                break;
            case PARAR:
                msg += "," + ((MsgParar) m).getAssincrono();
            default:
                break;
        }

        msg += "," + elapsed;
        return msg;
    }

    /**
     * Desencripta uma mensagem
     */
    public static Mensagem deserialize(String in) {
        String[] splitMessage = in.split(",");

        Mensagem.TIPO_MENSAGEM type = Mensagem.TIPO_MENSAGEM.values()[Integer.parseInt(splitMessage[0])];

        Mensagem m = null;

        switch (type) {

            case RETA:
                m = new MsgReta(Integer.parseInt(splitMessage[1]));
                break;
            case CURVA_ESQ:
                m = new MsgCurvarEsquerda(Integer.parseInt(splitMessage[1]),Integer.parseInt(splitMessage[2]));
                break;
            case CURVA_DIR:
                m = new MsgCurvarDireita(Integer.parseInt(splitMessage[1]),Integer.parseInt(splitMessage[2]));
                break;
            case PARAR:
                m = new MsgParar(Boolean.parseBoolean(splitMessage[1]));

        }
        return m;
    }
}
