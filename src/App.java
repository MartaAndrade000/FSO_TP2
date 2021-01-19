import java.util.concurrent.Semaphore;

public class App {

	//ESTADOS
	private enum TIPO_ESTADO {
		ESPERAR, ESPACAR_E_DESENHAR, TERMINAR
	}
	private TIPO_ESTADO estado;

	// Ativa ou desativa o DEBUG
	static final boolean DEBUG = true;

	//Robot ligado ou desligado
	public boolean robotOn = false;

	//Tipos de Direção
	public static final int DIRECAO_ESQ = 0;
	public static final int DIRECAO_DIR = 1;

	//Tipos de Desenho
	public static final int DESENHA_QUADRADO = 0;
	public static final int DESENHA_CIRCULO = 1;

	private int lastDim;
	private int nextDim;
	private Comportamento nextShape;

	private boolean jaDesenhou = false;

	GUIApp gui;

	ServidorRobot servidor;
	GravarFormas gravador;
	RobotDesenhador robot;
//	RobotLegoEV3 robot;

	BufferCircular buffer;

	DesenhaQuadrado quadrado;
	DesenhaCirculo circulo;
	EspacarFormasGeometricas espacarFormas;

	private final Semaphore haTrabalho;

	public App() {
		this.gui = new GUIApp(this);

		this.buffer = new BufferCircular();

//		this.robot = new RobotLegoEV3();
		this.robot = new RobotDesenhador();
		this.servidor = new ServidorRobot(buffer, robot, gravador);
		this.gravador = new GravarFormas(buffer);

		// Para testar TODO
//		gravador.setRecording(true);

		haTrabalho = new Semaphore(0);

		Semaphore sMutex = new Semaphore(1);
		Semaphore sStartDrawing = new Semaphore(0);

		this.quadrado = new DesenhaQuadrado(this, buffer, sMutex, sStartDrawing);
		this.circulo = new DesenhaCirculo(buffer, sMutex, sStartDrawing);
		this.espacarFormas = new EspacarFormasGeometricas(buffer, sMutex, sStartDrawing);

		quadrado.start();
		circulo.start();
		espacarFormas.start();
		servidor.start();

		estado = TIPO_ESTADO.ESPERAR;

		lastDim = 0;
	}

	public static void main(String[] args) throws InterruptedException {
		App app = new App();
		app.run();
		System.out.println("ENDED");
	}

	 public void stop() {
		haTrabalho.release();
		estado = TIPO_ESTADO.TERMINAR;
	 }

	private void run() throws InterruptedException {
		while (true) {
			switch (estado) {
				case ESPERAR:
					jaDesenhou = false;
					haTrabalho.acquire();

					break;

				case ESPACAR_E_DESENHAR:
					if(!jaDesenhou) {
						espacarFormas.desenha(lastDim, nextDim, nextShape);
						lastDim = nextDim;
						jaDesenhou = true;
					}

					if (estado == TIPO_ESTADO.ESPACAR_E_DESENHAR && nextShape.isAcabouDesenho()) {
						gui.setEstadoBtnFormas(true);
						estado = TIPO_ESTADO.ESPERAR;
						nextShape.setAcabouDesenho(false);
					}
					break;

				case TERMINAR:
					System.out.println("A Terminar App");
					desligarRobot();
					espacarFormas.terminarComportamento();
					quadrado.terminarComportamento();
					circulo.terminarComportamento();
					servidor.terminaServidor();
					buffer.terminarBuffer();

//					if(robot != null) // Alterei aqui
						robot.terminarRobot();
					this.gui.dispose();
					return;
			}
		}
	}

	// Liga diretamente
	public void ligarRobot(String nomeRobot) {
		if(robot.OpenEV3(nomeRobot)) robotOn = true;
	}

	// Desliga diretamente
	public void desligarRobot() {
		robot.CloseEV3();
		robotOn = false;
	}

	public void desenharForma(int forma, int dim, int direcao) {
		gui.setEstadoBtnFormas(false);
		haTrabalho.release();
		nextDim = dim;

		if (forma == DESENHA_QUADRADO) {
			quadrado.setDimLado(dim);
			quadrado.setDirecao(direcao);
			nextShape = quadrado;
		} else if (forma == DESENHA_CIRCULO) {
			circulo.setRaio(dim);
			circulo.setDirecao(direcao);
			nextShape = circulo;
		}
		else {
			System.out.println("Forma desconhecida");
			return;
		}

		estado = TIPO_ESTADO.ESPACAR_E_DESENHAR;
	}
}
