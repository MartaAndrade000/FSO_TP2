public class RobotDesenhador extends RobotLegoEV3 {
	
	private final GUIRobot gui;
	private final String robot;
	
	public RobotDesenhador() {
		this.gui = new GUIRobot();
		this.robot = null;
	}


	public boolean OpenEV3(String name) {
		return true;
	}

	/**
	 * Encerra o canal de comunicação
	 */
	public void CloseEV3() {
	}

	public void Reta(int dist) {
		Mensagem m = new Reta(dist);
		gui.printCommand(m);
	}

	public void CurvarEsquerda(int raio, int angulo){
		Mensagem m = new CurvarEsquerda(raio, angulo);
		gui.printCommand(m);
	}

	public void CurvarDireita(int raio, int angulo){
		Mensagem m = new CurvarDireita(raio, angulo);
		gui.printCommand(m);
	}

	public void Parar(boolean assincrono){
		Mensagem m = new Parar(assincrono);
		gui.printCommand(m);
	}

	public void terminarRobot() {
		System.out.println("Terminou Robot");
		gui.dispose();
	}
}
