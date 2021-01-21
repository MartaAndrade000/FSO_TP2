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

    public void toggleRecording() {
        this.setRecording(!this.recording);
    }

    public void setRecording(boolean state) {
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

    // function: playBack()
    // lock/stop inputs
    // ReadCommands -> give them to robot
    public void playBack() {
        this.recording = false;
        estado = REPLAY;
        haTrabalho.release();

    }

    public void readCommands () {
        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)))) {

            String line;
            while((line = inputStream.readLine()) != null) {
//                          System.out.println(line);
                // line -> message
                Mensagem msg = CommandSerializer.deserialize(line);
                String[] split = line.split(",");
                Long millis = Long.parseLong(split[split.length -1]);

                try {
                    Thread.sleep(millis);
                } catch (InterruptedException ignored) {}

                buffer.putMensagem(msg);

            }
        } catch (IOException e) {
            System.out.println("Miau");
        }
    }



    public void openFile(String filename) {
        this.file = new File(filename);
    }

    public boolean isRecording() {
        return recording;
    }

    public boolean isReplaying() {
        return (estado==REPLAY);
    }

    public void terminaGravador() {
        System.out.println("Terminou Gravador");
        estado = TERMINAR;
        haTrabalho.release();
    }
}
