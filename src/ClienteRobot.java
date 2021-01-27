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
		Mensagem m = new MsgReta(dimLado);
		gui.printCommand(m);
		setMensagem(m);
	}

	public void CurvarEsquerda(int raio, int angulo) {
		Mensagem m = new MsgCurvarEsquerda(raio, angulo);
		gui.printCommand(m);
		setMensagem(m);
	}
	
	public void CurvarDireita(int raio, int angulo) {
		Mensagem m = new MsgCurvarDireita(raio, angulo);
		gui.printCommand(m);
		setMensagem(m);
	}

	public void Parar(boolean assincrono) {
		Mensagem m = new MsgParar(assincrono);
		gui.printCommand(m);
		setMensagem(m);
	}
}
