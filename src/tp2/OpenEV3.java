package tp2;

public class OpenEV3 extends Mensagem {
	String nomeRobot;
	
	public OpenEV3(String nomeRobot) {
		super(Mensagem.OPEN, String.format("OpenEV3 (nome %s) \n", nomeRobot));
		this.nomeRobot = nomeRobot;
	}
}
