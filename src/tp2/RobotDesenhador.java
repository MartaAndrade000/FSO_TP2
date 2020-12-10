package tp2;

public class RobotDesenhador {
	
	GUIRobot gui;
	
	public RobotDesenhador() {
		this.gui = new GUIRobot();
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
	public boolean OpenEV3(String name) {
		return true;
	}


	/**
	 * Encerra o canal de comunicação
	 */
	public void CloseEV3() {}

	public void Reta(int dist) {}

	public void CurvarEsq(int raio, int angulo){}

	void CurvarDir(float raio, int angulo){}

	void Parar(boolean assincrono){}
}
