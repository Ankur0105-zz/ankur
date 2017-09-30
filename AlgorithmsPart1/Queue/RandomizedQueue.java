import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;
    
    public RandomizedQueue() {
        size = 0;
        queue = (Item[]) new Object[1];
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) copy[i] = queue[i];
        queue = copy;
    }
    
    public void enqueue(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();
        queue[size++] = item;
        if (size == queue.length) resize(2 * queue.length);
    }
    
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int index = StdRandom.uniform(size);
        Item item = queue[index];
        queue[index] = queue[size-1];
        queue[--size] = null;
        if (size > 0 && size == queue.length/4) resize(queue.length/2);
        return item;
    }
    
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        int index = StdRandom.uniform(size);
        Item item = queue[index];
        return item;
    }
    
    private class RandomQequeIterator implements Iterator<Item> {
        private int length = size;
        private int[] order;
        public RandomQequeIterator() {
            order = new int[length];
            for (int j = 0; j < length; ++j) {
                order[j] = j;
            }
            StdRandom.shuffle(order);
        }
        
        public boolean hasNext() {
            return length > 0;
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return queue[order[--length]];
        }
    }
    
    public Iterator<Item> iterator() {
        return new RandomQequeIterator();
    }
}