package TP2;

import java.util.concurrent.Semaphore;

public class App {
	
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
  	
	GUIApp gui;

	ClienteRobot cliente;
	ServidorRobot servidor;
	RobotDesenhador robot;
	
	BufferCircular buffer;
	
	DesenhaQuadrado quadrado;
	DesenhaCirculo circulo;
	EspacarFormasGeometricas espacarFormas;
	
	int lastDim;
	
	public App() {
		this.gui = new GUIApp(this);
		
		this.buffer = new BufferCircular();
		
		this.robot = new RobotDesenhador();
		this.cliente = new ClienteRobot(buffer);
		this.servidor = new ServidorRobot(buffer, robot);
		
		Semaphore sReady = new Semaphore(1);
		
		this.quadrado = new DesenhaQuadrado(buffer, sReady);
		this.circulo = new DesenhaCirculo(buffer, sReady);
		this.espacarFormas = new EspacarFormasGeometricas(buffer, sReady);	
		
		this.lastDim = 0;
		}
	
	 public static void main(String[] args) {
		 App app = new App();
		 app.run();
	 }
	 
	 private void run() {
		 quadrado.start();
		 circulo.start();
		 espacarFormas.start();
	 }

	public boolean ligarRobot(String nomeRobot) {
		// TODO não funciona nem com null nem com o "", mas fiz debug e o nomeRobor == "", o que é estranho
		// Ir a GUIApp linha 83
		if(cliente.OpenEV3(nomeRobot)) 
			return true;
		else
			return false;
	}

	public void desligarRobot() {
		cliente.CloseEV3();
	}

	public void desenhaQuadrado(int lado, int direcao) {
		// Espaça formas após primeira forma
		if(lastDim > 0) 
			espacarFormas.desenha(lastDim);
		quadrado.desenha(lado, direcao);
		lastDim = lado;
	}

	public void desenhaCirculo(int raio, int direcao) {
		if(lastDim > 0) 
			espacarFormas.desenha(lastDim+raio);
		circulo.desenha(raio, direcao);
		lastDim = raio;
	}
}
