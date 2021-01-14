import java.io.*;
import java.nio.charset.Charset;

public class GravarFormas extends Thread {

    private final RobotDesenhador robot;
//  private final RobotLegoEV3 robot;
    private final ServidorRobot rbServer;
    private boolean recording;
    private String filename;
    private File file;
    private BufferedReader inputStream;
    private OutputStream outputStream;

    private GUIGravarFormas gui;

    public GravarFormas(RobotDesenhador robot, ServidorRobot rbServer) {
        filename = "ola.txt";
        openFile();

        this.gui = new GUIGravarFormas();
        this.robot = robot;
        this.rbServer = rbServer;
    }

    public void setRecording(boolean state) {
        this.recording = state;
        if (this.recording) {
            try {
                this.outputStream = new BufferedOutputStream(new FileOutputStream(this.file));
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

        String serializedMsg = CommandSerializer.serialize(mensagem);

        gui.printCommand(mensagem);


        // Escrever no output usando uma printer
//        PrintWriter p = new PrintWriter(outputStream);
//        p.println("Hello");


        // Escrever no output por bytes ???
//        try {
//            outputStream.write(serializedMsg.getBytes(Charset.forName("UTF-8")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    // function: playBack()
    // lock/stop inputs
    // ReadCommands -> give them to robot
    public void playBack() {
        this.rbServer.setEstado(ServidorRobot.TIPO_ESTADO.REPLAY);

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


    // Create gui CLASSE á parte
    // this.fileName = fileName
    // this.file = file; ??

    // this.robot = robot;

    // this.isRecording = false;






    // function: openFile()
    // function: closeFile()

}
