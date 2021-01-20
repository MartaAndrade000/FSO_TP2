
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;

public class GUIGravarFormas extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextArea textAreaComandos;


    /**
     * Create the frame.
     */
    public GUIGravarFormas(GravarFormas gravarFormas) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setBounds(10, 11, 160, 20);
        contentPane.add(textField);
        textField.setColumns(10);


        JFileChooser fc = new JFileChooser();
        JButton btnOpen = new JButton("Open");

        btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //O file directory fica definido para a mesma localização do projeto e para a pasta chat
                fc.setCurrentDirectory(new java.io.File("./"));

                //O file chooser só vai mostrar ficheiros e directories
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                if (fc.showSaveDialog(btnOpen) == JFileChooser.APPROVE_OPTION) {
                    gravarFormas.openFile(fc.getSelectedFile().getName());
                    btnOpen.setEnabled(true);
                    textField.setText(fc.getSelectedFile().getAbsolutePath());
                }

            }
        });
        btnOpen.setBounds(184, 10, 89, 23);
        contentPane.add(btnOpen);

        // TODO Paths relativos?
        JButton btnPlayPause = new JButton("");
        btnPlayPause.setIcon(new ImageIcon("C:\\Users\\Marta Andrade\\Desktop\\ISEL\\3Semestre\\FSO\\SuporteTP2\\playpause.png"));
        btnPlayPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravarFormas.toggleRecording();
            }
        });
        btnPlayPause.setBounds(112, 54, 40, 40);
        contentPane.add(btnPlayPause);

        JButton btnReplay = new JButton("");
        btnReplay.setIcon(new ImageIcon("C:\\Users\\Marta Andrade\\Desktop\\ISEL\\3Semestre\\FSO\\SuporteTP2\\replay.png"));
        btnReplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravarFormas.playBack();
            }
        });
        btnReplay.setBounds(233, 54, 40, 40);
        contentPane.add(btnReplay);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 134, 414, 116);
        contentPane.add(scrollPane);

        textAreaComandos = new JTextArea();
        scrollPane.setViewportView(textAreaComandos);

        setVisible(true);
    }



    public void log(String text) {
        textAreaComandos.setText(text);
    }

}