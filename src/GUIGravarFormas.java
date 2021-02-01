
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class GUIGravarFormas extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextArea gravadorTextArea;
    private JButton btnRec = new JButton("");



    /**
     * Create the frame.
     */
    public GUIGravarFormas(GravarFormas gravarFormas) {
    	setTitle("Gravador de Formas");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(950, 440, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setBounds(94, 29, 262, 22);
        contentPane.add(textField);
        textField.setColumns(10);


        JFileChooser fc = new JFileChooser();
        JButton btnOpen = new JButton("Open");

        btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //O file directory fica definido para a mesma localização do projeto e para a pasta chat
                fc.setCurrentDirectory(new File("./"));

                //O file chooser só vai mostrar ficheiros e directories
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                if (fc.showSaveDialog(btnOpen) == JFileChooser.APPROVE_OPTION) {
                    gravarFormas.setFile(fc.getSelectedFile().getName());
                    btnOpen.setEnabled(true);
                    textField.setText(fc.getSelectedFile().getAbsolutePath());
                }

            }
        });
        btnOpen.setBounds(358, 29, 68, 23);
        contentPane.add(btnOpen);

        JButton btnRec = new JButton("");
        btnRec.setBorderPainted(false);
        btnRec.setContentAreaFilled(false);
        btnRec.setFocusPainted(false);
        btnRec.setOpaque(false);
        btnRec.setIcon(new ImageIcon("notRecording.png"));
        btnRec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravarFormas.toggleRecording();
                if(gravarFormas.isRecording())
                    btnRec.setIcon(new ImageIcon("recording.png"));
                else
                    btnRec.setIcon(new ImageIcon("notRecording.png"));
            }
        });
        btnRec.setBounds(10, 11, 40, 40);
        contentPane.add(btnRec);

        JButton btnReplay = new JButton("");
        btnReplay.setBorderPainted(false);
        btnReplay.setContentAreaFilled(false);
        btnReplay.setFocusPainted(false);
        btnReplay.setOpaque(false);
        btnReplay.setIcon(new ImageIcon("play.png"));
        btnReplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravadorTextArea.append("Está a reproduzir");
                gravarFormas.playBack();

               // if(gravarFormas.isReplaying())
               //     btnReplay.setIcon(new ImageIcon("stop.png"));
               // else
               //     btnReplay.setIcon(new ImageIcon("play.png"));
            }
        });
        btnReplay.setBounds(50, 11, 40, 40);
        contentPane.add(btnReplay);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane);
        scrollPane.setBounds(10, 62, 416, 191);


        gravadorTextArea = new JTextArea();
        gravadorTextArea.setEditable(false);

        scrollPane.setViewportView(gravadorTextArea);
        JLabel lblFileChooser = new JLabel("Escolha o Ficheiro para Grava\u00E7\u00E3o:");
        lblFileChooser.setBounds(94, 11, 246, 14);
        contentPane.add(lblFileChooser);

        setVisible(true);
    }

    public void logString(String text) {
        if (!EventQueue.isDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait( new Runnable() {
                    @Override
                    public void run() {
                        gravadorTextArea.append(text + "\n");
                    }
                });
            }
            catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            gravadorTextArea.append(text + "\n");
        }

    }
    public void printCommand(Mensagem m) {
        // Porque dá erro quando já é a tarefa gráfica a fazer append
        if (!EventQueue.isDispatchThread()) {
            try {
                SwingUtilities.invokeAndWait( new Runnable() {
                    @Override
                    public void run() {
                        String texto = m.toString();
                        gravadorTextArea.append("Gravou: " + texto);
                    }
                });
            }
            catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            String texto = m.toString();
            gravadorTextArea.append("Gravou: " + texto);
        }
    }
}