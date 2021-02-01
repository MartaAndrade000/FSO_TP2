public class RobotDesenhador extends RobotLegoEV3 {

	private final GUIRobot gui;

	public RobotDesenhador() {
		this.gui = new GUIRobot();
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
		Mensagem m = new MsgReta(dist);
		gui.printCommand(m);
	}

	public void CurvarEsquerda(int raio, int angulo){
		Mensagem m = new MsgCurvarEsquerda(raio, angulo);
		gui.printCommand(m);
	}

	public void CurvarDireita(int raio, int angulo){
		Mensagem m = new MsgCurvarDireita(raio, angulo);
		gui.printCommand(m);
	}

	public void Parar(boolean assincrono){
		Mensagem m = new MsgParar(assincrono);
		gui.printCommand(m);
	}

	public void terminarRobot() {
		System.out.println("Terminou Robot");
		gui.dispose();
	}
}
