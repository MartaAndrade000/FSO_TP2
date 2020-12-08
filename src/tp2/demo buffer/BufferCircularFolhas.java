package tp2;

import java.util.concurrent.Semaphore;

public class BufferCircularFolhas<E> extends BufferCircularBaseImpl<E> {

    private Semaphore sEspaço;
    private Semaphore sItems;
    private Semaphore sMutex;

    public BufferCircularFolhas(int size, IBufferCircularGUI gui){
        super(size, gui);

        this.sEspaço = new Semaphore(size);
        this.sItems = new Semaphore(0);
        this.sMutex = new Semaphore(1);
    }

    @Override
    public void put(E e) throws InterruptedException {
        this.sEspaço.acquire();


        try{
            this.sMutex.acquire();
            this.elements[this.idxPut] = e;
            ++this.idxPut;
            this.idxPut %= this.elements.length;
            this.gui.setIndexPut(this.idxPut);
            this.gui.displayNumberOfItems(this.numElements.incrementAndGet());
        }
        catch(Exception ex){
            ex.printStackTrace();
//            this.sEspaço.release();;ola
        }
        finally {
            this.sMutex.release();
        }
        this.sItems.release();
    }

    @Override
    public E get() throws InterruptedException {
        E result = null;
        this.sItems.acquire();
        try{
            this.sMutex.acquire();
            result = this.elements[this.idxGet];
            ++this.idxGet;
            this.idxGet %= this.elements.length;
            this.gui.setIndexGet(this.idxGet);
            this.gui.displayNumberOfItems(this.numElements.decrementAndGet());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            this.sMutex.release();
        }
        this.sEspaço.release();
        return result;
    }
}
