package tp2;

import java.util.concurrent.Semaphore;

public class BufferCircularSemaforos<E> extends BufferCircularBaseImpl<E> {

    // Representa espaços livres no buffer
    Semaphore sProdutores;
    int numProdutoresBlocked;
    // Representa items no buffer
    Semaphore sConsumidores;
    int numConsumidoresBlocked;
    // Controlo de escrita e leitura
    // Garante que enquanto existe escrita de um, os outros não lêm nem escrevem
    Semaphore sMutex;

    public BufferCircularSemaforos(int size, IBufferCircularGUI gui) {
        super(size, gui);

        // Inicialmente existem tantos espaços livres quantas as posições no buffer
        this.sProdutores = new Semaphore(size);
        // Inicialmente não existem items para consumo
        this.sConsumidores = new Semaphore(0);
        this.sMutex = new Semaphore(1);
    }


    @Override
    public void put(E e) throws InterruptedException {
        this.numProdutoresBlocked++;

        // Esperar até existir espaço livre
        this.sProdutores.acquire();

        this.numProdutoresBlocked--;

        try{
            // Obter exclusão para actualizar as estruturas dos dados
            this.sMutex.acquire();
            // Colocar o novo elemeno na posição correspondente
            this.elements[this.idxPut] = e;
            // Atualizar o próximo indice do put (posição do próximo elemento)
            ++this.idxPut;
            this.idxPut %= this.elements.length;
            // Atualizar a interface gráfica (posição do próximo elemnto)
            this.gui.setIndexPut(this.idxPut);
            // Atualizar o número de elemntos no buffer
            //this.gui.displayNumberOfItems(this.numElements.incrementAndGet());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            // Libertar a exclusão
            this.sMutex.release();
        }
        // Atualizar o número de elemntos no buffer
        //this.gui.displayNumberOfItems(this.numElements.incrementAndGet());

        // Indicar que existe mais um elemento para consumo
        this.sConsumidores.release();
    }

    @Override
    public E get() throws InterruptedException {
        E result = null;
        this.numConsumidoresBlocked++;

        // Esperar até existir um elemnto
        this.sConsumidores.acquire();

        this.numConsumidoresBlocked--;

        try{

            //TODO sMutex não está a bloquear também a escrita de outros produtores

            // Obter exclusão para actualizar as estruturas dos dados
            this.sMutex.acquire();
            // Obter elemento mais antigo
            result = this.elements[this.idxGet];
            // Atualizar o próximo indice do put (posição do próximo elemento)
            ++this.idxGet;
            this.idxGet %= this.elements.length;
            // Atualizar a interface gráfica (posição do próximo elemnto)
            this.gui.setIndexGet(this.idxGet);
            // Atualizar o número de elemntos no buffer
            this.gui.displayNumberOfItems(this.numElements.decrementAndGet());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            // Libertar a exclusão
            this.sMutex.release();
        }
        // Indicar que existe mais espaço livre
        this.sProdutores.release();
        return result;
    }
}
