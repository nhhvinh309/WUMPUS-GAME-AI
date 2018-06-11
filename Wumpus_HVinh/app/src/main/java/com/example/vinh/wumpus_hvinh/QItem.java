package com.example.vinh.wumpus_hvinh;

/**
 * Created by Vinh on 5/16/2018.
 */

public class QItem {
    int countStep = 0;
    int now = 0;
    public QItem(int now, int countStep)
    {
        this.now = now;
        this.countStep = countStep;
    }

    public int getCountStep() {
        return countStep;
    }

    public void setCountStep(){
        countStep++;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }
}
