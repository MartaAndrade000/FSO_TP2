package tp2;

import java.util.concurrent.Semaphore;

public class App {

	protected int estado;

	//ESTADOS
	private static final int ESPERAR = 0;
	private static final int DESENHAR  = 1;
	private static final int ESPACAR  = 2;

	// TODO o prof falou de uma maq de estados nos comportamentos e na propria App
	// Nos comportamentos não vejo como é necessário, eu usei a variavel "podeDesenhar"
	
	// TODO começar a desenhar apenas quando o robot estiver ligado
	
	// TODO desativar botoes de desenhar quando o robot ainda está a executar comandos
	
	// Ativa ou desativa o DEBUG
    static final boolean DEBUG = true;
    
    //Tipos de Direção
  	public static final int DIRECAO_ESQ = 0;
  	public static final int DIRECAO_DIR  = 1;
  	
  	//Tipos de Desenho
  	public static final int DESENHA_QUADRADO = 0;
  	public static final int DESENHA_CIRCULO  = 1;

  	private int forma;

	GUIApp gui;

	ClienteRobot cliente;
	ServidorRobot servidor;
	RobotDesenhador robot;
	
	BufferCircular buffer;
	
	DesenhaQuadrado quadrado;
	DesenhaCirculo circulo;
	EspacarFormasGeometricas espacarFormas;
	
	int lastDim;

	private Semaphore haTrabalho;

	public App() {
		this.gui = new GUIApp(this);
		
		this.buffer = new BufferCircular();
		
		this.robot = new RobotDesenhador();
		this.cliente = new ClienteRobot(buffer);
		this.servidor = new ServidorRobot(buffer, robot);

		haTrabalho = new Semaphore(0);

		Semaphore sReady = new Semaphore(1);
		
		this.quadrado = new DesenhaQuadrado(buffer, sReady);
		this.circulo = new DesenhaCirculo(buffer, sReady);
		this.espacarFormas = new EspacarFormasGeometricas(buffer, sReady);	
		
		this.lastDim = 0;

		quadrado.start();
		circulo.start();
		espacarFormas.start();
		servidor.start();

		estado = ESPERAR;
	}
	
	 public static void main(String[] args) throws InterruptedException {
		 App app = new App();
		 app.run();
	 }

	 private void run() throws InterruptedException {
		 while (true) {
			 switch (estado) {
				 case ESPERAR:
				 	System.out.println("A esperar");
//				 	Thread.sleep(0);
				 	haTrabalho.acquire();
				 	break;

				 case ESPACAR:

				 	System.out.println("A espaçar");
				 	espacarFormas.desenha(10);

				 	if(estado == ESPACAR) {
						estado = DESENHAR;
						break;
					}

				 case DESENHAR:
				 	System.out.println("A desenhar");

				 	if(forma == DESENHA_QUADRADO) {
//						System.out.println("Forma = Desenha quadrado");
						desenhaQuadrado(gui.getQuadrado());
					}

				 	else if(forma == DESENHA_CIRCULO) {
//						System.out.println("Forma = Desenha circulo");
						desenhaCirculo(gui.getCirculo());
				 	}

				 	if(estado == DESENHAR) {
						estado = ESPERAR;
						break;
					}

			 }
		 }
	 }

	public boolean ligarRobot(String nomeRobot) {
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
		return cliente.OpenEV3(nomeRobot);
	}

	public void desligarRobot() {
		cliente.CloseEV3();
	}

	public void desenharForma(int forma) {
//		System.out.println("Forma = " + forma);
		haTrabalho.release();
		this.forma = forma;
		estado = ESPACAR;
	}

	public void desenhaQuadrado(int[] dimQuadrado) {
		// Espaça formas após primeira forma
		quadrado.desenha(dimQuadrado[0], dimQuadrado[1]);
	}

	public void desenhaCirculo(int[] dimCirculo) {
		// Espaça formas após primeira forma
		circulo.desenha(dimCirculo[0], dimCirculo[1]);
	}
}
