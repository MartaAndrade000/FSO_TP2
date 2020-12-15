package tp2;

import java.util.concurrent.Semaphore;

public class App {

	//ESTADOS
	private enum TIPO_ESTADO {
		ESPERAR, ESPACAR_E_DESENHAR, TERMINAR
	}
	private TIPO_ESTADO estado;

	// TODO desativar botoes de desenhar quando o robot ainda está a executar comandos

	// Ativa ou desativa o DEBUG
	static final boolean DEBUG = true;

	//Tipos de Direção
	public static final int DIRECAO_ESQ = 0;
	public static final int DIRECAO_DIR = 1;

	//Tipos de Desenho
	public static final int DESENHA_QUADRADO = 0;
	public static final int DESENHA_CIRCULO = 1;

	private int lastDim;
	private int nextDim;
	private Comportamento nextShape;

	GUIApp gui;

//	ClienteRobot cliente;
	ServidorRobot servidor;
	RobotDesenhador robot;
	//RobotLegoEV3 robot;

	BufferCircular buffer;

	DesenhaQuadrado quadrado;
	DesenhaCirculo circulo;
	EspacarFormasGeometricas espacarFormas;

	private Semaphore haTrabalho;

	public App() {
		this.gui = new GUIApp(this);

		this.buffer = new BufferCircular();

		this.robot = new RobotDesenhador();
//		this.cliente = new ClienteRobot(buffer);
		this.servidor = new ServidorRobot(buffer, robot);

		haTrabalho = new Semaphore(0);

		Semaphore sMutex = new Semaphore(1);
		Semaphore sStartDrawing = new Semaphore(0);

		this.quadrado = new DesenhaQuadrado(buffer, sMutex, sStartDrawing);
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
	}

	 public void stop() {
		servidor.stop();
	 }

	private void run() throws InterruptedException {
		while (true) {
			switch (estado) {
				case ESPERAR:
					/* TODO uma sugestao (adicionar checks para ter a certeza que há forma a ser desenhada) */
					esperarPeloDesenhoDaForma();

					haTrabalho.acquire();
					break;

				case ESPACAR_E_DESENHAR:
					espacarFormas.desenha(lastDim, nextDim, nextShape);

					lastDim = nextDim;

					/* TODO outra sugestao */
					esperarPeloDesenhoDaForma();

					if (estado == TIPO_ESTADO.ESPACAR_E_DESENHAR) {
						estado = TIPO_ESTADO.ESPERAR;
					}
					break;

				case TERMINAR:
					break;
			}
		}
	}

	/**
	 * Logica para bloquear os botoes das formas enquanto se espera pelo
	 * tempo teorico que demora a desenha-las.
	 *
	 * NOTA!!!!
	 * Isto vai bloquear a thread em que é chamado, por isso ter consciencia onde e quando no
	 * processo meter isto, as mensgens já têm de estar enviadas.
	 * Conselho: nao por nenhuma GUI a chamar isto ( obvio mas... :D )
	 */
	private void esperarPeloDesenhoDaForma() {
		// TODO BLOQUEAR botoes das formas
		gui.setEstadoBtnFormas(false); // MIAU :3
		try {
			Thread.sleep(nextShape.getTempoExecucao());
		} catch (InterruptedException e) {
			System.out.println("Nao foi possivel bloquear os botoes pelo tempo esperado");
		}
		// TODO DESBLOQUEAR os botoes das formas
		gui.setEstadoBtnFormas(true); // MIAU :3
	}

	// Abre diretamente
	public boolean ligarRobot(String nomeRobot) {
		return robot.OpenEV3(nomeRobot);
		//		if(cliente.OpenEV3(nomeRobot)) {
		//			System.out.println("nome: " + robot.getRobot());
		//			if(robot.getRobot() != null) {
		//				System.out.println("here");
		//				return true;
		//			}
		//			else {
		//				return false;
		//			}
		//		}
		//		else
		//			return false;
		// return cliente.OpenEV3(nomeRobot);
	}

	public void desligarRobot() {
		robot.CloseEV3();
	}

	public void desenharForma(int forma, int dim, int direcao) {
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
		} else {
			System.out.println("Forma desconhecida");
			return;
		}

		estado = TIPO_ESTADO.ESPACAR_E_DESENHAR;

	}
}
