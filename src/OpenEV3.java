public class OpenEV3 extends Mensagem {
	String nomeRobot;
	
	public OpenEV3(String nomeRobot) {
		super(TIPO_MENSAGEM.OPEN, String.format("OpenEV3 (nome %s) \n", nomeRobot));
		this.nomeRobot = nomeRobot;
	}

	public String getRobotName() {
		return this.nomeRobot;
	}
}
