import java.awt.*;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GUICliente extends JFrame {

    private static int xPos = 20;
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final JTextArea ClienteTextArea;

    /**
     * Create the frame.
     * @param tipoCliente
     */
    public GUICliente(String tipoCliente) {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(tipoCliente);
        setBounds(xPos, 440, 300, 300);
        xPos+=310;
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JLabel lblBuffer = new JLabel("Comandos escritos no Buffer");
        lblBuffer.setBounds(19, 22, 123, 16);
        contentPane.add(lblBuffer, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        ClienteTextArea = new JTextArea();
        ClienteTextArea.setEditable(false);
//		bufferTextArea.setBounds(19, 48, 476, 278);
//		contentPane.add(bufferTextArea);

        scrollPane.setViewportView(ClienteTextArea);
        setVisible(true);
    }

    public void printCommand(Mensagem m) {
        // Porque dá erro quando já é a tarefa gráfica a fazer append
        if (!EventQueue.isDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait( new Runnable() {
                    @Override
                    public void run() {
                        String texto = m.getTexto();
                        ClienteTextArea.append(texto);
                    }
                });
            }
            catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            String texto = m.getTexto();
            ClienteTextArea.append(texto);
        }
    }
}
