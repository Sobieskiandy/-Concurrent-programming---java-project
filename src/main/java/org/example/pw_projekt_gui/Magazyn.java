package org.example.pw_projekt_gui;
public abstract class Magazyn implements Runnable{
    public int id;
    int ilosc;
    int pojemnosc;
    private volatile boolean running = true;
    public Magazyn(int id, int ilosc, int pojemnosc) {
        this.id = id;
        this.ilosc = ilosc;
        this.pojemnosc = pojemnosc;
    }
    public void stop() {
        running = false;
    }
}
