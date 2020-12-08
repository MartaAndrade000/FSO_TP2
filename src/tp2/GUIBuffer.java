package TP2;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;

public class GUIBuffer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea bufferTextArea;

	/**
	 * Create the frame.
	 */
	public GUIBuffer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 515, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBuffer = new JLabel("BufferCircular");
		lblBuffer.setBounds(19, 22, 123, 16);
		contentPane.add(lblBuffer);
		
		bufferTextArea = new JTextArea();
		bufferTextArea.setEditable(false);
		bufferTextArea.setBounds(19, 48, 476, 278);
		contentPane.add(bufferTextArea);
		
		setVisible(true);
	}
	
	public void printCommand(Mensagem m) {
		// Porque dá erro quando já é a tarefa gráfica a fazer append
		if (!EventQueue.isDispatchThread()) {
			try {
				SwingUtilities.invokeAndWait( new Runnable() {
					@Override
					public void run() {
						 // TODO devia fazer scroll e não faz, quero perceber porquê
						 String texto = String.format( "[%s] %s", Thread.currentThread().getName(), m.getTexto() ) ;	
			     		 bufferTextArea.append(texto);	
					}
				});
			} 
			catch (InvocationTargetException | InterruptedException e) { 
				e.printStackTrace(); 
			}
		}
		else {
			 String texto = String.format( "[%s] %s", Thread.currentThread().getName(), m.getTexto() ) ;	
     		 bufferTextArea.append(texto);	
		}
	}
}
