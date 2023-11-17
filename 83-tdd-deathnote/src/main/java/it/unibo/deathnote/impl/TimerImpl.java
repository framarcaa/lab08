package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.Timer;

public class TimerImpl implements Timer{

    private long startTime;
    private boolean isWorking;

    @Override
    public void reset() {
        isWorking = true;
        startTime = System.currentTimeMillis();
    }

    @Override
    public long stop() {
        isWorking = false;
        return getTime();
    }

    @Override
    public long getTime() {
        if (!isWorking) {
            throw new IllegalStateException("Timer is not working");
        }
        return System.currentTimeMillis() - startTime;
    }
    
}
