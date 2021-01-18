import java.io.*;

public class GravarFormas extends Thread {

    private final RobotLegoEV3 robot;
    private final ServidorRobot rbServer;
    private boolean recording;
    private String filename;
    private File file;
    private BufferedReader inputStream;
    private OutputStream outputStream;
    private PrintWriter output;
    private long lastMessageTS = -1;

    public GravarFormas(RobotLegoEV3 robot, ServidorRobot rbServer) {
        this.robot = robot;
        this.rbServer = rbServer;
    }

    public void setRecording(boolean state) {
        this.recording = state;
        this.lastMessageTS = System.currentTimeMillis();
        if (this.recording) {
            try {
                this.output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.file)));
            } catch (FileNotFoundException e) {
                System.out.println("Failed to open file stream");
            }
        } else {
            try {
                this.outputStream.close();
            } catch (IOException e) {
                System.out.println("Failed to close output stream");
            }
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
    }

    // function: playBack()
    // lock/stop inputs
    // ReadCommands -> give them to robot
    public void playBack() {
        this.rbServer.setEstado(ServidorRobot.TIPO_ESTADO.REPLAY);
        this.recording = false;

        try (BufferedReader inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(this.file)))) {


            String line;
            while((line = inputStream.readLine()) != null) {
                System.out.println(line);
                // line -> message
                Mensagem msg = CommandSerializer.deserialize(line);

            }


        } catch (IOException e) {
            System.out.println("Miau");
        }


        this.rbServer.setEstado(ServidorRobot.TIPO_ESTADO.LER);
    }



    public void openFile() {
        this.file = new File(this.filename);
    }

    public void bloquear() {

    }

}
