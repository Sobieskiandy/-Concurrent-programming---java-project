package org.example.pw_projekt_gui;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
public class MagazynNaZasoby extends Magazyn{
    public String rzecz;
    public int poziomKrytyczny;
    private static volatile boolean[] chce = new boolean[2];
    private static volatile int czyjaKolej = 0;
    private final Random los = new Random();
    private Pane root;
    int potrzeba=0;
    int x=100;
    int y=100;
    private volatile boolean running = true;
    public MagazynNaZasoby(int id, String rzecz, int poziomKrytyczny, int ilosc, int pojemnosc, Pane root) {
        super(id, ilosc, pojemnosc);
        this.rzecz=rzecz;
        this.poziomKrytyczny = poziomKrytyczny;
        this.root = root;
        y=id*100+(id-1)*50;
        /*if(id==1){//chcę by pierwszy magazyn od razu tworzył dwa samochody, jeden od "strafy A" do wielu MNZ oraz drugi od wielu MNZ do LP.
            //System.out.println("IloscSamochodowPrzed:"+Main.iloscSamochodow.get());
            Losuje.powiekszTablice(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get()+1);
            for(int i=HelloApplication.iloscSamochodow.get();i<HelloApplication.iloscSamochodow.get()+1;i++)
            {
                HelloApplication.listaSamochodow.get().set(i,new Samochod((i+1),false,1,0,null,root,x,y+200,false));
                //Powiększe tablicę listaSamochodowA
                Losuje.powiekszTablice(HelloApplication.listaSamochodowA,1);
                HelloApplication.listaSamochodowA.get().set(0,(i+1));
            }
            HelloApplication.iloscSamochodow.set(HelloApplication.iloscSamochodow.get()+1);
            Losuje.powiekszTablice(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get()+1);
            for(int i=HelloApplication.iloscSamochodow.get();i<HelloApplication.iloscSamochodow.get()+1;i++)
            {
                HelloApplication.listaSamochodow.get().set(i,new Samochod((i+1),false,2,0,null,root,x,y+100,true));
                //Powiększe tablicę listaSamochodowB
                Losuje.powiekszTablice(HelloApplication.listaSamochodowB,1);
                HelloApplication.listaSamochodowB.get().set(0,(1));
            }
            HelloApplication.iloscSamochodow.set(HelloApplication.iloscSamochodow.get()+1);
            //System.out.println("IloscSamochodowPo:"+Main.iloscSamochodow.get());
        }*/
        String s = getClass().getResource("/org/example/pw_projekt_gui/mnz/" + rzecz).toExternalForm();

        Image image = new Image(s);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        root.getChildren().add(imageView);
        imageView.setLayoutX(x);imageView.setLayoutY(y);
        //System.out.println("IloscSamochodowpO:"+HelloApplication.iloscSamochodow.get());
        System.out.print("Jestem magazynem na zasób "+rzecz+" o id="+id+", pk="+poziomKrytyczny+", mam="+ilosc+", max="+pojemnosc+"\n");
    }
    @Override
    public void run(){
        while(running){//!Thread.currentThread().isInterrupted()){
            if(HelloApplication.iloscMagazynowNaZasoby.get()<=2){
                if(ilosc<poziomKrytyczny){
                    //Algorytm Petersona
                    potrzeba=pojemnosc-ilosc;
                    System.out.println("MNZ"+id+": pojemnosc: "+pojemnosc+" ilosc: "+ ilosc+" potrzebuję "+potrzeba+" zasobu "+rzecz+".");
                    try {
                        Thread.sleep(los.nextInt(10) + 1);
                    } catch (InterruptedException e) {
                        break;//e.printStackTrace();
                    }
                    int drugi = 1 - (id-1);
                    chce[(id-1)] = true;
                    czyjaKolej = drugi;
                    while (chce[drugi] && czyjaKolej == drugi) {
                        Thread.yield(); // oczekiwanie
                    }
                    // Sekcja krytyczna
                    for (int i = 0; i < HelloApplication.listaSamochodowA.get().length(); i++) {
                        int idSamochodu = HelloApplication.listaSamochodowA.get().get(i)-1;
                        Samochod samochod = HelloApplication.listaSamochodow.get().get(idSamochodu);
                        System.out.println("MNZ"+id+" sięga po S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                        MagazynNaZasoby MNZ = HelloApplication.listaMagazynowNaZasoby.get().get(id-1);
                        if(!samochod.pracuje&&samochod.obszar==1&&HelloApplication.semaMNZ.tryAcquire()){
                            System.out.println("MNZ"+id+" wywołuję S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                            samochod.sekcja1(potrzeba,rzecz,samochod,MNZ);
                            break;
                        }
                        System.out.println("MNZ"+id+" otrzymał od S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i ma teraz:"+ilosc+" przy pojemnosci:"+pojemnosc);
                    }
                    /*
                    try {
                        Thread.sleep(los.nextInt(10) + 1);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        break;
                        //Main.MW1.interrupt();//Main.MW1.join();
                    }
                    ilosc+=potrzeba;*/
                    // Wyjście z sekcji krytycznej
                    chce[(id-1)] = false;
                    potrzeba = pojemnosc - ilosc;
                    System.out.println("MNZ"+id+": pojemnosc: "+pojemnosc+" ilosc: "+ ilosc+" NIE potrzebuję "+potrzeba+" zasobu "+rzecz+".");
                }
            }else{ //Semafor
                if(ilosc<poziomKrytyczny) {
                    potrzeba = pojemnosc - ilosc;//określenie potrzebnej ilości
                    System.out.println("MNZ" + id + ": pojemnosc: " + pojemnosc + " ilosc: " + ilosc + " potrzebuję " + potrzeba + " zasobu " + rzecz + ".");
                    for(int i = 0; i < HelloApplication.listaSamochodowA.get().length(); i++) {
                        int idSamochodu = HelloApplication.listaSamochodowA.get().get(i)-1;
                        Samochod samochod = HelloApplication.listaSamochodow.get().get(idSamochodu);
                        System.out.println("MNZ"+id+" sięga po S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                        MagazynNaZasoby MNZ = HelloApplication.listaMagazynowNaZasoby.get().get(id-1);
                        if(!samochod.pracuje&&samochod.obszar==1&&HelloApplication.semaMNZ.tryAcquire()){
                            System.out.println("MNZ"+id+" wywołuję S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                            samochod.sekcja1(potrzeba,rzecz,samochod,MNZ);
                            break;
                        }
                        System.out.println("MNZ"+id+" otrzymał od S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i ma teraz:"+ilosc+" przy pojemnosci:"+pojemnosc);
                    }
                    /*
                    for (int i = 0; i < Main.listaSamochodowB.get().length(); i++) {
                        int idSamochodu = Main.listaSamochodowB.get().get(i);
                        Samochod samochod = Main.listaSamochodow.get().get(idSamochodu);
                        System.out.println("lSB" + idSamochodu + " pracuje: " + samochod.pracuje);
                    }*/
                    /*
                    try {
                        Main.semaMNZ.acquire();
                        Thread.sleep(1000);
                        ilosc += potrzeba;
                        Main.semaMNZ.release();
                    } catch (InterruptedException e) {
                        break;//e.printStackTrace();
                    }
                    potrzeba = pojemnosc - ilosc;
                    */
                    //System.out.println("MNZ" + id + ": pojemnosc: " + pojemnosc + " ilosc: " + ilosc + " NIE potrzebuję " + potrzeba + " zasobu " + rzecz + ".");
                }
            }
            System.out.println("MNZ"+id+" działa w pętli!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Koniec MNZ"+id+".");
    }
    public void stop() {
        running = false;
    }
}
