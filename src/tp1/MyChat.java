package tp1;

import java.awt.*;
import java.io.File;

public class MyChat {
	
	// Ativa ou desativa o DEGUB
    static boolean DEBUG = true;

    private final CanalDeComunicacao canal;
    private final BD bd;
    private GUI gui;
    
    // Para verificar quando app terminou
    private boolean running;
    
    // ID que vai sendo atualizado pelo buffer
    private int lastMsgId = -1;

    
    /**
     * Inicializa o chat
     */
    public MyChat() {
        this.canal = new CanalDeComunicacao();
        this.bd = new BD();
        this.running = true;

        startGUI();
    }

    
    public boolean isRunning() {
        return running;
    }

    
    /**
     * Permite terminar os processos da app
     */
    public void stop() {
    	fecharCanal();
        this.running = false;
    }

    
    /**
     * Abre o canal de comunicação
     * @return true ou false se abriu o canal com sucesso
     */
    public boolean abrirCanal() {
    	
        if (canal.abrirCanal()) {
        	
            // Ao abrir o canal atualizar lastMsgId com o id da última mensagem no buffer
            Mensagem mensagem = canal.receberMensagem();
            
            // Se não existir mensagem, o id = 0, se houver mensagem atualiza lastMsgId
            lastMsgId = (mensagem != null && mensagem.getId() > 0) ? mensagem.getId() : 0;
            
            return true;
        } 
        else 
            return false;
    }

    
    /**
     * Fecha o canal de comunicação
     */
    public void fecharCanal() {
        canal.fecharCanal();
    }

 
    public void setTipoMensagem(int tipo) {
        this.bd.setTipo(tipo);
    }

    public int getTipoMensagem() {
        return this.bd.getTipo();
    }

    public void setFicheiro(File file) {
        this.canal.setFicheiro(file);
    }

    
    /**
     * Envia mensagem a partir do texto vindo da GUI
     */
    public void enviarMensagem(String text) {

    	if (canal.isCanalOk()) {
    		
            this.gui.logInfo(""); // Limpa a consola 
            text = text.trim();

            if (text.isEmpty()) { // Ignorar mensagens em branco
                this.gui.logInfo("Mensagem está vazia");
                return;
            }

            // Faz truncagem avisa que a msg foi cortada 
            if (text.length() > Mensagem.MAX_LENGTH) {
            	
                this.gui.logInfo(String.format("Mensagem maior que %d caracteres, fez-se uma truncagem", Mensagem.MAX_LENGTH));
                text = text.substring(0, Mensagem.MAX_LENGTH);
            }

            // Envia com lastMsgId + 1 mas não o atualiza (para ser "apanhado" pela thread que lê)
            Mensagem novaMensagem = new Mensagem(lastMsgId + 1, bd.getTipo(), text);

            canal.enviarMensagem(novaMensagem); // Enviar pelo canal da mancha
            if (MyChat.DEBUG) System.out.println("Enviou: " + text);
        } 
    	else {
            gui.logInfo("Falta abrir o canal");
        }
    }
    
    
    private void startGUI() {
        EventQueue.invokeLater(() -> {
            MyChat.this.gui = new GUI(MyChat.this);
        });
    }
    
    /**
     * Método Run, enquanto a gui estiver a correr e o canal estiver Ok
     * vai ler e mostrar mensagens
     */
    private void run() {
    	
        while (isRunning()) { 
        	
            try {
                Thread.sleep(500);
                
                if (canal.isCanalOk()) { // Canal está aberto
                    Mensagem mensagem = canal.receberMensagem();

                    if (mensagem == null) continue; // Leitura falhou
                    if (mensagem.getId() <= lastMsgId) continue; // Mensagem antiga

                    // Mensagem nova
                    if (bd.getTipo() == Mensagem.TODAS ||
                        bd.getTipo() == mensagem.getTipoMensagem()) {
                        // Só lê-mos mensagens se estivermos modo "TODAS" ou do mesmo tipo
                        // da mensagem que chegou
                    	
                        gui.mostraMensagem(mensagem);
                    }

                    lastMsgId = mensagem.getId(); // Atualiza  último id lido com este novo
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        System.out.printf("[%s] método main a começar.\n", Thread.currentThread().getName());

        // Para ter debug correr:
        // > java MyChat true
        // > java MyChat [debugActive]


        // Opcional para correr a aplicação com logs completos
        if (args.length > 0) {
            String arg = args[0];
            MyChat.DEBUG = Boolean.parseBoolean(arg);
        }

        MyChat chat = new MyChat();
        chat.run();

        System.out.printf("[%s] método main a terminar.\n", Thread.currentThread().getName());
    }
}
