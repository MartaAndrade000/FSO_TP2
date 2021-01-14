public class ClienteRobot {
	
	GUICliente gui;
	BufferCircular buffer;

	public ClienteRobot(BufferCircular buffer, String tipoCliente) {
		this.gui = new GUICliente(tipoCliente);
		this.buffer = buffer;
	}
	
	public void setMensagem(Mensagem m) {
		buffer.putMensagem(m);
	}

	public void Reta(int dimLado) {
		Mensagem m = new Reta(dimLado);
		gui.printCommand(m);
		setMensagem(m);
	}

	public void CurvarEsquerda(int raio, int angulo) {
		Mensagem m = new CurvarEsquerda(raio, angulo);
		gui.printCommand(m);
		setMensagem(m);
	}
	
	public void CurvarDireita(int raio, int angulo) {
		Mensagem m = new CurvarDireita(raio, angulo);
		gui.printCommand(m);
		setMensagem(m);
	}

	public void parar(boolean assincrono) {
		Mensagem m = new Parar(assincrono);
		gui.printCommand(m);
		setMensagem(m);
	}

/*	public boolean OpenEV3(String nomeRobot) {
		Mensagem m = new OpenEV3(nomeRobot);
		gui.printCommand(m);
		setMensagem(m);
		return true;
	}*/

/*	public void CloseEV3() {
		Mensagem m = new CloseEV3();
		gui.printCommand(m);
		setMensagem(m);
	}*/
}
