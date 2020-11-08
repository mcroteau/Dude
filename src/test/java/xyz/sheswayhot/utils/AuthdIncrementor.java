package xyz.sheswayhot.utils;

public class AuthdIncrementor {
    private int count;

    public AuthdIncrementor(){
        this.count = 0;
    }

    public void increment(){
        count++;
    }

    public int getCount(){
        return this.count;
    }
}