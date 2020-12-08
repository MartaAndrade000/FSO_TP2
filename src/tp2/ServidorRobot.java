package tp2;

public class ServidorRobot {

	GUIServidor gui;
	
	BufferCircular buffer;
	RobotDesenhador robot;
	
	public ServidorRobot(BufferCircular buffer, RobotDesenhador robot) {
		this.gui = new GUIServidor();
		this.buffer = buffer;
		this.robot = robot;
	}

}
