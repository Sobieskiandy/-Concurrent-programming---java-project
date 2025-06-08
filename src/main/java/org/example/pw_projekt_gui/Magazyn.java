package org.example.pw_projekt_gui;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
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
