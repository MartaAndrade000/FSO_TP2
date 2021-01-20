public class CommandSerializer {


    // todo feeling lazy, might optimize later
    public static String serialize(Mensagem m, long elapsed) {
        String msg = "";

        msg += m.getTipo().ordinal();

        switch (m.getTipo()) {

            case RETA:
                msg += "," + ((Reta) m).getDist();
                break;
            case CURVA_ESQ:
                msg += "," + ((CurvarEsquerda) m).raio + "," + ((CurvarEsquerda) m).angulo;
                break;
            case CURVA_DIR:
                msg += "," + ((CurvarDireita) m).raio + "," + ((CurvarDireita) m).angulo;
                break;
            case PARAR:
                msg += "," + ((Parar) m).getAssincrono();
            default:
                break;
        }

        msg += "," + elapsed;
        return msg;
    }

    public static Mensagem deserialize(String in) {
        String[] splitMessage = in.split(",");

        Mensagem.TIPO_MENSAGEM type = Mensagem.TIPO_MENSAGEM.values()[Integer.parseInt(splitMessage[0])];

        Mensagem m = null;

        switch (type) {

            case RETA:
                m = new Reta(Integer.parseInt(splitMessage[1]));
                break;
            case CURVA_ESQ:
                m = new CurvarEsquerda(Integer.parseInt(splitMessage[1]),Integer.parseInt(splitMessage[2]));
                break;
            case CURVA_DIR:
                m = new CurvarDireita(Integer.parseInt(splitMessage[1]),Integer.parseInt(splitMessage[2]));
                break;
            case PARAR:
                m = new Parar(Boolean.parseBoolean(splitMessage[1]));

        }
        return m;

    }
}
