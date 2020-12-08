package tp1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class CanalDeComunicacao {
	
	//Ficheiro a ser mapeado
	private File ficheiro;
	
	//Canal que liga o conteúdo do ficheiro ao Buffer
	private FileChannel canal;
	
	//Buffer
	private MappedByteBuffer buffer;
	
	//Dimensão máxima em bytes do buffer
	// ID(int) + Tipo(int) + Mensagem(256 char) + "\0"(char)
	final int BUFFER_MAX = 4 + 4 + (2 * Mensagem.MAX_LENGTH) + 2;
	//Offset
	final int OFFSET = 0;

	//Exclusão mútua
	FileLock fl;

	
	/**
	 * Construtor onde se cria o canal
	 */
	public CanalDeComunicacao() { 
		ficheiro = null;
		canal    = null;
		buffer   = null; 
	}
	
	
	/**
	 * Abre o canal de comunicação 
	 */
	@SuppressWarnings("resource")
	public boolean abrirCanal() {

		// Se não existir ficheiro usa um default, caso contrário usa o que estiver
		ficheiro = ficheiro == null ? new File("default.txt") : ficheiro;

		// Cria um canal de comunicação de leitura e escrita
		try {
			canal = new RandomAccessFile(ficheiro, "rw").getChannel();
		}
		catch (FileNotFoundException e) { return false; }

		// Mapeia para memória o conteúdo do ficheiro
		try {
			buffer = canal.map(FileChannel.MapMode.READ_WRITE, OFFSET, BUFFER_MAX);
			}
		catch (IOException e) { return false;}

		return true;
	}
	
	
	/**
	 * Lê informação do buffer e retorna mensagem que lá estiver.
	 *
	 * Nota: Respeita exclusão mutua.
	 *
	 * @return uma Mensagem
	 */
	Mensagem receberMensagem() {
		
		// Trata dos erros da aplicação para prevenir que o ficheiro fique preso/locked
		try {
			fl = canal.lock();
		
			Mensagem lida;
	
			buffer.position(0); // Começa no início
			int id = buffer.getInt(); //posição = 4;
			int tipo = buffer.getInt(); //posição = 8;
	
			String texto = "";
			char c;
			while ((c = buffer.getChar()) != '\0') {
				texto += c;
			}
	
			lida = new Mensagem(id, tipo, texto);
			
			return lida;
			
		} catch(Exception e) {
			
			System.out.println("Couves");
			System.out.println("O Receber a Mensagem falhou");
		
		} finally {
			try {
				
				fl.release();
				
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	/**
	 * Escreve uma mensagem no buffer
	 */
	void enviarMensagem(Mensagem m) {

		// Trata dos erros da aplicação para prevenir que o ficheiro fique preso/locked
		try {

			fl = canal.lock();
			
			//Desde o início do buffer
			buffer.position(0);

			//Escreve o ID e o Tipo
			buffer.putInt(m.getId());
			buffer.putInt(m.getTipoMensagem());

			//Escreve o conteúdo da mensagem
			char c;
			
			for (int i= 0; i < m.getTexto().length(); ++i) {
				
				c = m.getTexto().charAt(i);
				buffer.putChar(c);
			}
			
			buffer.putChar('\0');


		} catch(Exception e) {
			
			System.out.println("Couves");
			System.out.println("Envio da mensagem falhou");
		
		} finally {
			try {
				
				fl.release();
			
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Fecha o canal
	 */
	void fecharCanal() {
		try {
			
			if (canal != null) canal.close();
			
		} catch (IOException e) {
			System.out.println("Couves: O canal não fechou bem");
		} finally {
			
			buffer = null;
			canal = null;
		}
	}

	
	public void setFicheiro(File file) {
		this.ficheiro = file;
	}

	
	/**
	 * Retorna verdadeiro se o canal e o buffer estiverem instancializados (!= null)
	 * @return estado do canal
	 */
	public boolean isCanalOk() {
		return canal != null && buffer != null;
	}
}
