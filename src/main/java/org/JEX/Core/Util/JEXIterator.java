package org.JEX.Core.Util;

public class JEXIterator<T> {

    private final T[] arr;
    private int index = 0;

    public JEXIterator(T[] arr){
        this.arr = arr;
    }

    public boolean hasNext(){
        return index < arr.length;
    }

    public T next(){
        return arr[index++];
    }

    public void reset(){
        index = 0;
    }

    public void forEachBool(JEXIterableBool<T> iterable){
        for (T t : arr) {
            if(t == null)
                continue;
            if(iterable.run(t))
                break;
        }
    }
    public void forEach(JEXIterable<T> iterable){
        for (T t : arr) {
            if(t == null)
                continue;
            iterable.run(t);
        }
    }
}
