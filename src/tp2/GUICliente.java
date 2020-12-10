package tp2;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GUICliente extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea ClienteTextArea;

    /**
     * Create the frame.
     */
    public GUICliente() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 368);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JLabel lblBuffer = new JLabel("Comandos escritos no Buffer");
        lblBuffer.setBounds(19, 22, 123, 16);
        contentPane.add(lblBuffer, BorderLayout.NORTH);

        JScrollPane xpto = new JScrollPane();
        contentPane.add(xpto, BorderLayout.CENTER);

        ClienteTextArea = new JTextArea();
        ClienteTextArea.setEditable(false);
//		bufferTextArea.setBounds(19, 48, 476, 278);
//		contentPane.add(bufferTextArea);

        xpto.setViewportView(ClienteTextArea);
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
                        ClienteTextArea.append(texto);
                    }
                });
            }
            catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            String texto = String.format( "[%s] %s", Thread.currentThread().getName(), m.getTexto() ) ;
            ClienteTextArea.append(texto);
        }
    }
}
