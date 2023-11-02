package org.JEX.Core.Levels;

public class LevelIterator<T> {

    private final T[] arr;
    private int index = 0;

    public LevelIterator(T[] arr){
        this.arr = arr;
    }

    public boolean hasNext(){
        return index < arr.length;
    }

    public T next(){
        return arr[index++];
    }
}
