import java.io.*;

public class GravarFormas extends Thread {

    private boolean recording;
    private File file;
    private PrintWriter output;
    private long lastMessageTS = -1;
    private BufferCircular buffer;

    private GUIGravarFormas gui;


    public GravarFormas(BufferCircular buffer) {
        this.gui = new GUIGravarFormas(this);
        this.buffer = buffer;
    }

    public void toggleRecording() {
        this.setRecording(!this.recording);
    }

    public void setRecording(boolean state) {
        this.recording = state;
        this.lastMessageTS = System.currentTimeMillis();
        gui.log((this.recording ? "Started" : "Stopped") + " recording");
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

    // function: recordCommand(Command c)
    // if (this.isRecording) ...magia ( Serializer ) ...

    // TODO Semaphore to regulate concurrent writes
    public void recordCommand(Mensagem mensagem) {

        if (!this.recording) return;
        if (this.output == null) return;


        long millis = System.currentTimeMillis();
        long elapsed = millis - this.lastMessageTS;
        this.lastMessageTS = millis;

        String serialize = CommandSerializer.serialize(mensagem, elapsed);
        output.println(serialize);
        gui.log(serialize);
    }

    // function: playBack()
    // lock/stop inputs
    // ReadCommands -> give them to robot
    public void playBack() {
        this.recording = false;

        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)))) {


            String line;
            while((line = inputStream.readLine()) != null) {
//                System.out.println(line);
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

    public void bloquear() {

    }

}
