package tp1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variáveis
	private final JPanel contentPane;
	private final JTextField caixaCorreioTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final JTextField consolaTextField;
	private final JTextField mensagemEnviarTextField;
	private final JTextArea mensagensRecebidas;
	JScrollPane paiMensagensRecebidas;
	
	private final MyChat chat;
	
	
	/**
	 * Cria a janela
	 * 
	 * @param chat Referência ao MyChat
	 */
	public GUI(MyChat chat) {	
		this.chat = chat;
		
		setTitle("My Chat");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				chat.stop();
			}
		});
		
		//Quando fechar a janela, o processo vai sair da lista de processos do sistema operativo.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 575, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*
		 * CAIXA DE CORREIO
		 */
		JLabel lblCaixaCorreio = new JLabel("Caixa de Correio");
		lblCaixaCorreio.setBounds(12, 18, 109, 16);
		contentPane.add(lblCaixaCorreio);
		
		caixaCorreioTextField = new JTextField();
		caixaCorreioTextField.setBounds(122, 13, 236, 26);
		contentPane.add(caixaCorreioTextField);
		caixaCorreioTextField.setColumns(10);
		
		/*
		 * CONSOLA
		 */
		consolaTextField = new JTextField();
		consolaTextField.setBounds(12, 262, 537, 26);
		contentPane.add(consolaTextField);
		consolaTextField.setColumns(10);
		consolaTextField.setEditable(false);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Tipo de Mensagem", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 46, 153, 214);
		contentPane.add(panel);
		panel.setLayout(null);
		
		/*
		 * RADIO BUTTON "TODAS"
		 */
		JRadioButton rdbtnTodas = new JRadioButton("Todas");
		rdbtnTodas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					chat.setTipoMensagem(Mensagem.TODAS);
					logInfo("O tipo de mensagem passou a ser <" + chat.getTipoMensagem() + ">");
				}
			}
		});
		rdbtnTodas.setSelected(true);

		rdbtnTodas.setBounds(6, 32, 141, 23);
		panel.add(rdbtnTodas);
		buttonGroup.add(rdbtnTodas);
		
		/*
		 * RADIO BUTTON "FSO"
		 */
		JRadioButton rdbtnFso = new JRadioButton("FSO");
		rdbtnFso.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					chat.setTipoMensagem(Mensagem.FSO);
					logInfo("O tipo de mensagem passou a ser <" + chat.getTipoMensagem() + ">");
				}
			}
		});
		rdbtnFso.setBounds(6, 79, 141, 23);
		panel.add(rdbtnFso);
		buttonGroup.add(rdbtnFso);
		
		/*
		 * RADIO BUTTON "ROBOTS"
		 */
		JRadioButton rdbtnRobots = new JRadioButton("Robots");
		rdbtnRobots.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					chat.setTipoMensagem(Mensagem.ROBOTS);
					logInfo("O tipo de mensagem passou a ser <" + chat.getTipoMensagem() + ">");
				}
			}
		});
		rdbtnRobots.setBounds(6, 126, 141, 23);
		panel.add(rdbtnRobots);
		buttonGroup.add(rdbtnRobots);
		
		/*
		 * RADIO BUTTON "JAVA"
		 */
		JRadioButton rdbtnJava = new JRadioButton("Java");
		rdbtnJava.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					chat.setTipoMensagem(Mensagem.JAVA);
					logInfo("O tipo de mensagem passou a ser <" + chat.getTipoMensagem() + ">");
				}
			}
		});
		rdbtnJava.setBounds(6, 173, 141, 23);
		panel.add(rdbtnJava);
		buttonGroup.add(rdbtnJava);
		
		/*
		 * RADIO BUTTON "ABRIR" 
		 */
		JRadioButton rdbtnAbrir = new JRadioButton("Abrir");
		rdbtnAbrir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(rdbtnAbrir.isSelected()) {

					if (chat.abrirCanal()) {
						logInfo("O canal foi aberto");
					}
					else {
						logInfo("O canal não foi aberto");
						// Não pinta se não conseguir abrir
						rdbtnAbrir.setSelected(false);
					}

				} 
				else {
					chat.fecharCanal();
					logInfo("O canal foi fechado");
				}
			}
		});
		rdbtnAbrir.setEnabled(false);
		rdbtnAbrir.setBounds(480, 16, 141, 23);
		contentPane.add(rdbtnAbrir);
		
		/*
		 * FILECHOOSER E BOTÃO "BROWSE"
		 */
		JFileChooser fc = new JFileChooser();
		JButton btnBrowse = new JButton("Browse");
		
		btnBrowse.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {

				//O file directory fica definido para a mesma localização do projeto e para a pasta chat
				fc.setCurrentDirectory(new java.io.File("./chat"));
			
				//O file chooser só vai mostrar ficheiros e directories
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				//Quando não existe file, ele permite que eu crie um
				if (fc.showSaveDialog(btnBrowse) == JFileChooser.APPROVE_OPTION) {
					handleSelecionarFicheiro(fc.getSelectedFile());
					rdbtnAbrir.setEnabled(true);
				}
			}
		});
		btnBrowse.setBounds(363, 13, 117, 29);
		contentPane.add(btnBrowse);
		
		/*
		 * MENSAGEM A ENVIAR
		 */
		JLabel lblMensagemAEnviar = new JLabel("Mensagem a Enviar");
		lblMensagemAEnviar.setBounds(177, 46, 161, 16);
		contentPane.add(lblMensagemAEnviar);
		
		mensagemEnviarTextField = new JTextField();
		mensagemEnviarTextField.setBounds(175, 65, 254, 26);
		contentPane.add(mensagemEnviarTextField);
		mensagemEnviarTextField.setColumns(10);
		
		/*
		 * BOTÃO "ENVIAR"
		 */
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleEnviar();
			}
		});
		btnEnviar.setBounds(432, 65, 117, 25);
		contentPane.add(btnEnviar);
		
		/*
		 * BOTÃO "LIMPAR"
		 */
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mensagensRecebidas.setText("");
			}
		});
		btnLimpar.setBounds(432, 91, 117, 25);
		contentPane.add(btnLimpar);
		
		/*
		 * MENSAGENS RECEBIDAS
		 */
		JLabel lblMensagensRecebidas = new JLabel("Mensagens Recebidas");
		lblMensagensRecebidas.setBounds(177, 97, 161, 16);
		contentPane.add(lblMensagensRecebidas);

		mensagensRecebidas = new JTextArea();
		mensagensRecebidas.setEditable(false);
		paiMensagensRecebidas = new JScrollPane(mensagensRecebidas);
		paiMensagensRecebidas.setBounds(177, 117, 372, 143);
		contentPane.add(paiMensagensRecebidas);
		
		// Display da GUI
		setVisible(true);
	}
	
	
	/**
	 * Trata de enviar o ficheiro selecionado para o MyChat
	 */
	private void handleSelecionarFicheiro(File selectedFile) {

		chat.setFicheiro(selectedFile);

		logInfo("O ficheiro passou a ser <" + selectedFile.getName() + ">");
		caixaCorreioTextField.setText(selectedFile.getAbsolutePath());
	}
	
	
	/**
	 * Trata de enviar o a mensagem escrita para o MyChat
	 */
	private void handleEnviar() {
		String text = mensagemEnviarTextField.getText();
		chat.enviarMensagem(text);
		mensagemEnviarTextField.setText(""); // Limpa a mensagem quando é enviada
	}

	
	/**
	 * Trata de receber mensagens, mostrá-las no ecrã e puxar o scroll para
	 * baixo.
	 *
	 * @param m Mensagem recebida a tratar
	 */
	public void mostraMensagem(Mensagem m) {
		mensagensRecebidas.append(m + "\n");

		// Puxar scroll para baixo
		JScrollBar verticalScrollBar = paiMensagensRecebidas.getVerticalScrollBar();
		verticalScrollBar.setValue(verticalScrollBar.getMaximum());
	}
	
	
	/**
	 * Escreve na consola para debug
	 * 
	 * @param txt Texto a escrever
	 */
	public void logInfo(String txt) {
		if (MyChat.DEBUG)
			consolaTextField.setText(txt);
	}

}
