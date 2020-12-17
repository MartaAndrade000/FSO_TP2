package tp2;

import java.util.concurrent.Semaphore;

public class BufferCircular {
	
	GUIBuffer gui;
	
	final int dimBuffer = 16;
	Mensagem[] buffer;
	
	int putIndex, getIndex;
	
	private final Semaphore sComandos; // Número de mensagens para o robot no buffer
    private final Semaphore sMutex; 	 // Garante exclusão mútua
    
    public BufferCircular() {
    	this.gui = new GUIBuffer();
    	
    	this.buffer = new Mensagem[dimBuffer];
    	this.putIndex = 0;
    	this.getIndex = 0;
    	
    	this.sComandos = new Semaphore(0);
        this.sMutex = new Semaphore(1);
    }
    
    public void putMensagem(Mensagem m) {
    	try{
            this.sMutex.acquire(); // Exclusão mútua
            
            buffer[putIndex] = m;
            putIndex = ++putIndex % dimBuffer; // Dar a volta ao array
            
            gui.printCommand(m);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            this.sMutex.release();
        }
        this.sComandos.release();
	}
    
    public Mensagem getMensagem() {
    	Mensagem m = null;
    	try {
        	sComandos.acquire();
    		sMutex.acquire(); // Exclusão mútua
    		
    		m = buffer[getIndex];
    		getIndex = ++getIndex % dimBuffer; // Dar a volta ao array
    	}
		catch(InterruptedException ex){
			System.out.println("Thread '" + Thread.currentThread().getName() + "' was interrupted");
		}
    	catch(Exception ex){
    	    ex.printStackTrace();
    	}
    	finally {
    	    this.sMutex.release();
    	}
		return m;
	}

	public void terminarBuffer() {
		System.out.println("Terminou Buffer");
		gui.dispose();
	}
}

