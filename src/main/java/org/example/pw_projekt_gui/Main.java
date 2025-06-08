/*
package org.example.pw_projekt_gui;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class Main {
    //metoda dostarczająca do MNZ kiedy zostanie wywołana. Jest ona sekcją krytyczną symbolizująca dostęp do zasobu jakim jest samochód dostawczy który przewozi pewną zmienną ilość określonych zasobów w określonej ilości do określonego MNZ.
    static Semaphore semaMNZ = new Semaphore(1);
    static Semaphore semaLP = new Semaphore(1);static Semaphore semaLP1 = new Semaphore(1);
    static Semaphore semaMW = new Semaphore(1);
    public static AtomicInteger iloscMagazynowNaZasoby=new AtomicInteger(0);
    public static MagazynWyjsciowy MW1;
    public static AtomicInteger iloscZasobow=new AtomicInteger(0);
    public static AtomicInteger iloscLiniiProdukcyjnych = new AtomicInteger(0);
    public static AtomicInteger iloscSamochodow = new AtomicInteger(0);
    public static AtomicReference<AtomicReferenceArray<Integer>> listaSamochodowA = new AtomicReference<>(new AtomicReferenceArray<>(0));
    public static AtomicReference<AtomicReferenceArray<Integer>> listaSamochodowB = new AtomicReference<>(new AtomicReferenceArray<>(0));
    public static AtomicReference<AtomicReferenceArray<Integer>> listaSamochodowC = new AtomicReference<>(new AtomicReferenceArray<>(0));
    public static AtomicReference<AtomicReferenceArray<Integer>> listaSamochodowD = new AtomicReference<>(new AtomicReferenceArray<>(0));
    public static AtomicReferenceArray<String> listaZasobow= new AtomicReferenceArray<String>(iloscZasobow.get());//String[] listaZasobow=null;
    public static AtomicReference<AtomicReferenceArray<Samochod>> listaSamochodow = new AtomicReference<>(new AtomicReferenceArray<>(iloscSamochodow.get()));
    public static AtomicReference<AtomicReferenceArray<MagazynNaZasoby>> listaMagazynowNaZasoby = new AtomicReference<>(new AtomicReferenceArray<>(iloscMagazynowNaZasoby.get()));//AtomicReferenceArray<MagazynNaZasoby> listaMagazynowNaZasoby=new AtomicReferenceArray<MagazynNaZasoby>(iloscMagazynowNaZasoby.get());//MagazynNaZasoby[] listaMagazynowNaZasoby=null;
    public  static AtomicReference<AtomicReferenceArray<LiniaProdukcyjna>> listaLiniiProdukcyjnych = new AtomicReference<>(new AtomicReferenceArray<>(iloscLiniiProdukcyjnych.get()));//Thread[] listaLiniiProdukcyjnych=null;

    public static void main() throws InterruptedException {

        System.out.print("Witaj,\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wybierz dostępną opcję:\nWciśnij '1' by wpisać wszystkie parametry ręcznie\nWciśnij '2' by wylosować wszystkie parametry\nWciśnij dowolny inny klawisz by wybrać opcję standardową\nWybrano:");
        char opcja= scanner.next().charAt(0);
        switch(opcja){
            case '1':
                System.out.println("Wybrano opcję 1");
                //new Recznie().rob(iloscZasobow, iloscLiniiProdukcyjnych, iloscMagazynowNaZasoby, listaZasobow, listaMagazynowNaZasoby, listaLiniiProdukcyjnych);
                break;
            case '2':
                System.out.println("Wybrano opcję 2");
                new Losuje().losuj(iloscZasobow, iloscLiniiProdukcyjnych, iloscMagazynowNaZasoby, listaZasobow, listaMagazynowNaZasoby, listaLiniiProdukcyjnych);
                break;
            default:
                System.out.println("Wybrano inną opcję");
        }
        if(iloscMagazynowNaZasoby.get()<=2){
            System.out.println("Algorytm Petersona");//dekkera w MW
        }
        else{
            System.out.println("Semafory");
        }
        for(int i=3;i>0;i--){System.out.println("Start za "+i);Thread.sleep(1000);}
        //MagazynyNaZasoby
        for (int i = 0; i < iloscLiniiProdukcyjnych.get(); i++) {
            LiniaProdukcyjna lp = listaLiniiProdukcyjnych.get().get(i);
            if (lp != null) {
                lp.start();
            }
        }
        for (int i = 0; i < iloscMagazynowNaZasoby.get(); i++) {
            MagazynNaZasoby lmnz = listaMagazynowNaZasoby.get().get(i);
            if (lmnz != null) {
                lmnz.start();
            }
        }
        MW1.start();
        for (int i = 0; i < iloscSamochodow.get(); i++) {
            Samochod sa = listaSamochodow.get().get(i);
            if (sa != null) {
                sa.start();
            }
        }
        Thread.sleep(10000);
        //System.out.println(iloscZasobow+", "+iloscLiniiProdukcyjnych);
        /*for (Thread f : listaLiniiProdukcyjnych) {
            f.interrupt();
        }*/
        //System.out.println("iloscSamochodow: "+iloscSamohodow.get());
/*
        for (int i = 0; i < iloscSamochodow.get(); i++) {
            Samochod sa = listaSamochodow.get().get(i);
            if (sa != null) {
                sa.interrupt();
                sa.join();
            }
        }
        for (int i = 0; i < iloscLiniiProdukcyjnych.get(); i++) {
            LiniaProdukcyjna lp = listaLiniiProdukcyjnych.get().get(i);
            if (lp != null) {
                lp.interrupt();
                lp.join();
            }
        }
        for (int i = 0; i < iloscMagazynowNaZasoby.get(); i++) {
            MagazynNaZasoby lp = listaMagazynowNaZasoby.get().get(i);
            if (lp != null) {
                lp.interrupt();
                lp.join();
            }
        }
        MW1.interrupt();MW1.join();
        /*for(int i = 0; i < iloscMagazynowNaZasoby.get(); i++) {//ZRÓB U MAGAZYNÓW TO CO ZROBIŁEŚ DLA LINIIPRODUKCYJNYCH, BO W MAINIE SĄ PUSTE MagazynNaZasoby lp=listaMagazynowNaZasoby.get(i);if(lp != null){lp.interrupt();lp.join();}}/*for(int i = 0; i < iloscLiniiProdukcyjnych.get();i++){MagazynWyjsciowy lp =;if(lp != null){lp.interrupt();lp.join();}}*/
        /*System.out.println("Bezpieczny koniec wszystkich wątków.");
    }
}*/