package tp2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class GUIRobot extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea robotTextArea;

    /**
     * Create the frame.
     */
    public GUIRobot() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 515, 368);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JLabel lblBuffer = new JLabel("Comandos recebidos pelo Robot");
        lblBuffer.setBounds(19, 22, 123, 16);
        contentPane.add(lblBuffer, BorderLayout.NORTH);

        JScrollPane robotScrollPane = new JScrollPane();
        contentPane.add(robotScrollPane, BorderLayout.CENTER);

        robotTextArea = new JTextArea();
        robotTextArea.setEditable(false);

        robotScrollPane.setViewportView(robotTextArea);
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
                        robotTextArea.append(texto);
                    }
                });
            }
            catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            String texto = String.format( "[%s] %s", Thread.currentThread().getName(), m.getTexto() ) ;
            robotTextArea.append(texto);
        }
    }
}

