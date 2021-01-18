import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;


public class GUIGravarFormas extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final JTextArea bufferTextArea;

    /**
     * Create the frame.
     */
    public GUIGravarFormas() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gravador");
        setBounds(950, 440, 300, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JLabel lblBuffer = new JLabel("Comandos Gravados");
        lblBuffer.setBounds(19, 22, 123, 16);
        contentPane.add(lblBuffer, BorderLayout.NORTH);

        JScrollPane xpto = new JScrollPane();
        contentPane.add(xpto, BorderLayout.CENTER);

        bufferTextArea = new JTextArea();
        bufferTextArea.setEditable(false);
//		bufferTextArea.setBounds(19, 48, 476, 278);
//		contentPane.add(bufferTextArea);

        xpto.setViewportView(bufferTextArea);
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
                        bufferTextArea.append(texto);
                    }
                });
            }
            catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            String texto = m.getTexto();
            bufferTextArea.append(texto);
        }
    }
}
