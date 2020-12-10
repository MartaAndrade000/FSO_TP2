package tp2;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import javax.swing.*;

public class GUIServidor {

    private JFrame frmServidorDoRobot;
    private JTextArea comandostextArea;
    private JScrollPane comandosScrollPane;

    /**
     * Launch the application.
     */
    /*public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUIServidor window = new GUIServidor();
                    window.frmServidorDoRobot.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    /**
     * Create the application.
     */
    public GUIServidor() {
        initialize();

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmServidorDoRobot = new JFrame();
        frmServidorDoRobot.setTitle("Servidor do Robot");
        frmServidorDoRobot.setBounds(100, 100, 348, 330);
        frmServidorDoRobot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmServidorDoRobot.getContentPane().setLayout(null);

        JLabel lblComandos = new JLabel("Comandos Lidos Do Buffer:");
        lblComandos.setBounds(10, 23, 140, 14);
        frmServidorDoRobot.getContentPane().add(lblComandos);

        comandostextArea = new JTextArea();
        comandostextArea.setBounds(10, 53, 312, 213);
        frmServidorDoRobot.getContentPane().add(comandostextArea);

        comandosScrollPane = new JScrollPane();
        comandosScrollPane.setBounds(305, 53, 17, 213);
        frmServidorDoRobot.getContentPane().add(comandosScrollPane);
        
        frmServidorDoRobot.setVisible(true);
    }

    public void mostraComando(Mensagem m) {
        comandostextArea.append(m + "\n");

        // Puxar scroll para baixo
        JScrollBar verticalScrollPane = comandosScrollPane.getVerticalScrollBar();
        verticalScrollPane.setValue(verticalScrollPane.getMaximum());
    }
}
