import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class GUIApp extends JFrame {

	private int lado;
	private int raio;
	private App app;
	private int direcaoQuadrado = App.DIRECAO_ESQ;
	private int direcaoCirculo  = App.DIRECAO_ESQ;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField nomeRobotTextField;
	private final JTextField consolaTextField;

	JButton btnDesenharQuadrado;
	JButton btnDesenharCirculo;

	/**
	 * Create the frame.
	 */
	public GUIApp(App app) {
		this.app = app;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				app.stop();
			}
		});

		//Quando fechar a janela, o processo vai sair da lista de processos do sistema operativo.
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("Desenhador de Formas");
		setBounds(70, 70, 462, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*
		 * LIGAR E DESLIGAR ROBOT
		 */
		JLabel lblNomeDoRobot = new JLabel("Nome do Robot:");
		lblNomeDoRobot.setBounds(70, 28, 114, 16);
		contentPane.add(lblNomeDoRobot);
		
		nomeRobotTextField = new JTextField();
		nomeRobotTextField.setBounds(177, 23, 130, 26);
		contentPane.add(nomeRobotTextField);
		nomeRobotTextField.setColumns(10);
		
		JRadioButton rdbtnOnoff = new JRadioButton("On/Off");
		rdbtnOnoff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if(rdbtnOnoff.isSelected()) {

					// Se robot estiver desligado
					if(!app.robotOn){
						// Tirar nome
						String nomeRobot = nomeRobotTextField.getText();
						// Tentar ligar
						app.ligarRobot(nomeRobot);
						// Se continuar desligado
						if(!app.robotOn){
							logInfo("O robot não foi ligado");
							// Não pinta se não conseguir abrir
							rdbtnOnoff.setSelected(false);
						}
						else
							logInfo("O robot foi ligado");
					}
				}
				else {
					logInfo("O robot foi desligado");
					app.desligarRobot();
				}
			}
		});
		rdbtnOnoff.setBounds(319, 24, 77, 23);
		contentPane.add(rdbtnOnoff);
		
		/*
		 * DESENHAR QUADRADO
		 */
		JPanel panelQuadrado = new JPanel();
		panelQuadrado.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Desenhar um Quadrado", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelQuadrado.setBounds(26, 64, 197, 147);
		contentPane.add(panelQuadrado);
		panelQuadrado.setLayout(null);
		
		JLabel lblLado = new JLabel("Lado:");
		lblLado.setBounds(36, 31, 40, 16);
		panelQuadrado.add(lblLado);
		
		JSpinner spinnerLado = new JSpinner(new SpinnerNumberModel(25, 20, null, 1));
		spinnerLado.setBounds(77, 26, 61, 26);
		panelQuadrado.add(spinnerLado);
		
		JLabel lblLadoCm = new JLabel("cm");
		lblLadoCm.setBounds(142, 31, 19, 16);
		panelQuadrado.add(lblLadoCm);
		
		btnDesenharQuadrado = new JButton("Desenhar!");
		btnDesenharQuadrado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				try {
					// Ensure manually typed values with the editor are propagated to the model
				    spinnerLado.commitEdit();
				}
				catch ( java.text.ParseException e ) {
					System.out.println("Erro Spinner");
				}

				if(rdbtnOnoff.isSelected()) {
					lado = (Integer) spinnerLado.getValue();

					handleDesenhar(App.DESENHA_QUADRADO, lado, direcaoQuadrado);
				} else{
					logInfo("Impossível desenhar, Robot desligado");
				}
			}
		});
		btnDesenharQuadrado.setBounds(40, 99, 117, 29);
		panelQuadrado.add(btnDesenharQuadrado);
		
		JRadioButton rdbtnQuadradoEsquerda = new JRadioButton("Esquerda");
		rdbtnQuadradoEsquerda.setSelected(true);
		rdbtnQuadradoEsquerda.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					direcaoQuadrado = App.DIRECAO_ESQ;
					logInfo("O tipo de direção do quadrado passou a ser para a esquerda");
				}
			}
		});
		ButtonGroup buttonGroupQuadrado = new ButtonGroup();
		buttonGroupQuadrado.add(rdbtnQuadradoEsquerda);
		rdbtnQuadradoEsquerda.setBounds(16, 64, 89, 23);
		panelQuadrado.add(rdbtnQuadradoEsquerda);
		
		JRadioButton rdbtnQuadradoDireita = new JRadioButton("Direita");
		rdbtnQuadradoDireita.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					direcaoQuadrado = App.DIRECAO_DIR;
					logInfo("O tipo de direção do quadrado passou a ser para a direita");
				}
			}
		});
		buttonGroupQuadrado.add(rdbtnQuadradoDireita);
		rdbtnQuadradoDireita.setBounds(106, 64, 74, 23);
		panelQuadrado.add(rdbtnQuadradoDireita);
		
		/*
		 * DESENHAR CÍRCULO
		 */
		JPanel panelCirculo = new JPanel();
		panelCirculo.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Desenhar um C\u00EDrculo", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCirculo.setBounds(235, 64, 197, 147);
		contentPane.add(panelCirculo);
		panelCirculo.setLayout(null);
		
		JLabel lblRaioCm = new JLabel("cm");
		lblRaioCm.setBounds(134, 32, 19, 16);
		panelCirculo.add(lblRaioCm);
		
		JSpinner spinnerRaio = new JSpinner(new SpinnerNumberModel(25, 20, null, 1));
		spinnerRaio.setBounds(69, 27, 61, 26);
		panelCirculo.add(spinnerRaio);
		
		JLabel lblRaio = new JLabel("Raio:");
		lblRaio.setBounds(28, 32, 40, 16);
		panelCirculo.add(lblRaio);
		
		btnDesenharCirculo = new JButton("Desenhar!");
		btnDesenharCirculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					// Ensure manually typed values with the editor are propagated to the model
				    spinnerRaio.commitEdit();
				} 
				catch ( java.text.ParseException e ) { 
					System.out.println("Erro Spinner");
				}
				if(rdbtnOnoff.isSelected()) {
					raio = (Integer) spinnerRaio.getValue();
					handleDesenhar(App.DESENHA_CIRCULO, raio, direcaoCirculo);
				} else {
					logInfo("Impossível desenhar, Robot desligado");
				}
			}
		});
		btnDesenharCirculo.setBounds(40, 98, 117, 29);
		panelCirculo.add(btnDesenharCirculo);
		
		JRadioButton rdbtnCirculoDireita = new JRadioButton("Direita");
		rdbtnCirculoDireita.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					direcaoCirculo = App.DIRECAO_DIR;
					logInfo("O tipo de direção do circulo passou a ser para a direita");
				}
			}
		});
		ButtonGroup buttonGroupCirculo = new ButtonGroup();
		buttonGroupCirculo.add(rdbtnCirculoDireita);
		rdbtnCirculoDireita.setBounds(106, 63, 74, 23);
		panelCirculo.add(rdbtnCirculoDireita);
		
		JRadioButton rdbtnCirculoEsquerda = new JRadioButton("Esquerda");
		rdbtnCirculoEsquerda.setSelected(true);
		rdbtnCirculoEsquerda.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					direcaoCirculo = App.DIRECAO_ESQ;
					logInfo("O tipo de direção do circulo passou a ser para a esquerda");
				}
			}
		});
		buttonGroupCirculo.add(rdbtnCirculoEsquerda);
		rdbtnCirculoEsquerda.setBounds(16, 63, 89, 23);
		panelCirculo.add(rdbtnCirculoEsquerda);
		
		/*
		 * CONSOLA
		 */	
		consolaTextField = new JTextField();
		consolaTextField.setEditable(false);
		consolaTextField.setBounds(6, 246, 449, 26);
		contentPane.add(consolaTextField);
		consolaTextField.setColumns(10);
		
		JLabel lblConsola = new JLabel("Consola");
		lblConsola.setBounds(16, 228, 61, 16);
		contentPane.add(lblConsola);
		
		setVisible(true);
	}
	
	protected void handleDesenhar(int tipoDeDesenho, int dim, int direcao) {
		app.desenharForma(tipoDeDesenho, dim, direcao);
	}

	/**
	 * Escreve na consola para debug
	 * 
	 * @param txt Texto a escrever
	 */
	public void logInfo(String txt) {
		if (app.DEBUG)
			consolaTextField.setText(txt);
	}
}
