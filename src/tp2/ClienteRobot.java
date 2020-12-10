package tp2;

public class ClienteRobot {
	
	GUICliente gui;
	
	BufferCircular buffer;
	
	public ClienteRobot(BufferCircular buffer) {
		this.gui = new GUICliente();
		this.buffer = buffer;
	}
	
	public void setMensagem(Mensagem m) {
		buffer.putMensagem(m);
	}

	public void Reta(int dimLado) {
		setMensagem(new Reta(dimLado));
	}

	public void CurvarEsquerda(int raio, int angulo) {
		setMensagem(new CurvarEsquerda(raio, angulo));
	}
	
	public void CurvarDireita(int raio, int angulo) {
		setMensagem(new CurvarDireita(raio, angulo));		
	}

	public void parar(boolean assincrono) {
		setMensagem(new Parar(assincrono));
	}

	// TODO como verificar que o robot foi aberto?
	public boolean OpenEV3(String nomeRobot) {
		if(!nomeRobot.equals("")) {
			// Impede que dê se a pessoa não der nome ao robot
			setMensagem(new OpenEV3(nomeRobot));
			return true;
		}
		else
			return false;
	}

	public void CloseEV3() {
		setMensagem(new CloseEV3());
	}
}
