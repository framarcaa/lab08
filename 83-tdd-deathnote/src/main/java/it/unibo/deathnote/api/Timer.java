package it.unibo.deathnote.api;

public interface Timer {

    void reset();

    long stop();

    long getTime();
    
}
