package com.example.vinh.wumpus_hvinh;

import android.widget.ImageView;

/**
 * Created by Vinh on 4/18/2018.
 */

public class Square {
    boolean Hole = false;
    boolean Wumpus = false;
    boolean Gold = false;
    boolean Breeze = false;
    boolean Stench = false;
    boolean Agent = false;
    boolean Safe = false;
    boolean Visited = false;
    int DangerHole = 0;
    int DangerWumpus = 0;
    int id;
    int count = 0;
    public Square(int id)
    {
        Agent = false;
        Hole = false;
        Wumpus = false;
        Gold = false;
        Breeze = false;
        Stench = false;
        Safe = false;
        Visited = false;
        DangerHole = 0;
        DangerWumpus = 0;
        this.id = id;
        this.count = count;
    }


    public boolean isGold() {
        return Gold;
    }

    public boolean isHole() {
        return Hole;
    }

    public boolean isWumpus() {
        return Wumpus;
    }

    public void setGold(boolean gold) {
        Gold = gold;
    }

    public void setHole(boolean hole) {
        Hole = hole;
    }

    public void setWumpus(boolean wumpus) {
        Wumpus = wumpus;
    }

    public boolean isBreeze() {
        return Breeze;
    }

    public void setBreeze(boolean breeze) {
        Breeze = breeze;
    }

    public boolean isStench() {
        return Stench;
    }

    public void setStench(boolean stench) {
        Stench = stench;
    }
    public boolean isAgent()
    {
        return  Agent;
    }
    public void setAgent(boolean agent)
    {
        Agent = agent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSafe() {
        return Safe;
    }

    public void setSafe(boolean safe) {
        Safe = safe;
    }

    public boolean isVisited() {
        return Visited;
    }

    public void setVisited(boolean visited) {
        Visited = visited;
    }

    public int getDangerHole() {
        return DangerHole;
    }

    public void setDangerHole() {
        DangerHole++;
    }

    public int getDangerWumpus() {
        return DangerWumpus;
    }

    public void setDangerWumpus() {
        DangerWumpus++;
    }
    public void setWhenWumpusdie()
    {
        DangerWumpus = 0;
    }
    public void setCount()
    {
        count++;
    }
    public int getCount()
    {
        return count;
    }
}
