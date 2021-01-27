import java.io.*;
import java.util.concurrent.Semaphore;

public class GravarFormas extends Thread {

    private boolean recording;
    private File file;
    private PrintWriter output;
    private long lastMessageTS = -1;
    private BufferCircular buffer;

    private Semaphore sMutex;

    private GUIGravarFormas gui;

    protected int estado;

    //ESTADOS
    protected static final int ESPERAR = 0;
    protected static final int REPLAY = 1;
    protected static final int TERMINAR = 2;

    protected Semaphore haTrabalho;

    public GravarFormas(BufferCircular buffer, Semaphore sMutex) {
        this.gui = new GUIGravarFormas(this);
        this.buffer = buffer;
        this.sMutex = sMutex;
        haTrabalho = new Semaphore(0);
        estado = ESPERAR;
    }


    public void run() {
        while(true) {

            switch(estado) {
                case TERMINAR:
                    gui.dispose();
                    return;
                case ESPERAR:
                    try {
                        haTrabalho.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case REPLAY:
                    try {
                        sMutex.acquire();
                        readCommands();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        sMutex.release();
                    }

                    if(estado == REPLAY) {
                        estado = ESPERAR;
                        break;
                    }
            }
        }
    }


    /**
	 * Altera o estado da variável recording para o valor oposto
	 */
    public void toggleRecording() {
        this.setRecording(!this.recording);
    }


    /**
     * Abre o ficheiro
     */
    public void setRecording(boolean state) {
        if (file != null) {
            this.recording = state;
            this.lastMessageTS = System.currentTimeMillis();
            gui.logString((this.recording ? "Começou" : "Parou") + " a gravação");
            if (this.recording) {
                try {
                    this.output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.file)));
                } catch (FileNotFoundException e) {
                    System.out.println("Failed to open file stream");
                }
            } else {
                this.output.close();
            }
        }
        else{
            gui.logString("Insira um ficheiro");
        }
    }

    /**
     * Determina o tempo de execução de cada comando
     * Guarda os comandos encriptados e os seus tempos
     */
    public void recordCommand(Mensagem mensagem) {

        if (!this.recording) return;
        if (this.output == null) return;

        long millis = System.currentTimeMillis();
        long elapsed = millis - this.lastMessageTS;
        this.lastMessageTS = millis;

        String serializesMsg = CommandSerializer.serialize(mensagem, elapsed);
        output.println(serializesMsg);
        gui.printCommand(mensagem);
    }

    /**
     * Muda o estado para REPLAY
     */
    public void playBack() {
        this.recording = false;
        if (file != null) {
            estado = REPLAY;
            haTrabalho.release();
        }
        else{
            gui.logString("Insira um ficheiro");
        }
    }

    /**
     * Lê os comandos encriptados que estão no ficheiro
     * Converte-os em comados conhecidos pelo robot
     * Põe no buffer os comandos para serem lidos
     */
    public void readCommands () {
        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)))) {

            String line;
            while((line = inputStream.readLine()) != null) {
                Mensagem msg = CommandSerializer.deserialize(line);
                String[] split = line.split(",");
                Long millis = Long.parseLong(split[split.length -1]);

                try {
                    Thread.sleep(millis);
                } catch (InterruptedException ignored) {}

                buffer.putMensagem(msg);
            }
        } catch (IOException e) {
            System.out.println("Falha a ler o ficheiro");
        }
    }


    /**
     * Instância o ficheiro
     */
    public void setFile(String filename) {
        this.file = new File(filename);
    }

    /**
     * @Return recording
     */
    public boolean isRecording() {
        return recording;
    }

    /**
     * Muda o estado para terminar
     */
    public void terminaGravador() {
        System.out.println("Terminou Gravador");
        estado = TERMINAR;
        haTrabalho.release();
    }
}
