package tp2;

public class RobotDesenhador {
	
	private GUIRobot gui;
	private String robot;
	
	public RobotDesenhador() {
		this.gui = new GUIRobot();
		this.robot = null;
	}

	/**
	 * Estabelece o canal de comunicação entre o robot e o
	 * computador. O método retorna o valor true em caso
	 * de sucesso no estabelecimento da ligação entre o
	 * robot e o computador.
	 *
	 * O método retorna false quando
	 * a conexão falha. A falha da conexão pode ter vários
	 * motivos, o robot não estar emparelhado, o robot estar
	 * desligado, o nome do robot não ser o correto, etc.
	 *
	 * @param name Robots name
	 * @return Returns whether connection was successfully established
	 */
	public void OpenEV3(String name) {
		// if(Robot.OpenEV3(String name)) {
			// Robot = new Robot();
		// }
		Mensagem m = new OpenEV3(name);
		gui.printCommand(m);
//		robot = "Robot";
//		System.out.println(robot);
//		System.out.println("entrou no robot");
	}

	public String getRobot() {
		return robot;
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

	public void CurvarEsquerda(int raio, float angulo){
		Mensagem m = new CurvarEsquerda(raio, angulo);
		gui.printCommand(m);
	}

	void CurvarDireita(int raio, float angulo){
		Mensagem m = new CurvarDireita(raio, angulo);
		gui.printCommand(m);
	}

	void Parar(boolean assincrono){
		Mensagem m = new Parar(assincrono);
		gui.printCommand(m);
	}
}
