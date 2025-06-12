package org.example.pw_projekt_gui;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class MagazynWyjsciowy extends Magazyn{
    private Pane root;
    public int iloscNaPakiet;
    public int pakiety;
    private static volatile boolean[] chce = new boolean[2];
    private static volatile int czyjaKolej = 0; //ustala kolejność
    private final Random los = new Random();
    int x=800;
    int y=150;
    public Image image;
    public ImageView imageView;
    private volatile boolean running = true;
    public MagazynWyjsciowy(int id, int iloscProduktow, int iloscPakietow, int iloscNaPakiet, int pojemnosc, Pane root){
        super(id, iloscProduktow, pojemnosc);
        this.iloscNaPakiet=iloscNaPakiet;
        this.root = root;
        /*if(id==1) {//chcę by pierwszy magazyn wyjściowy (MW) od razu tworzył samochód, jeżdżący od MW do "strefy B"
            System.out.println("IloscSamochodowPrZeD:" + HelloApplication.iloscSamochodow.get());
            Losuje.powiekszTablice(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get() + 1);
            for (int i = HelloApplication.iloscSamochodow.get(); i < HelloApplication.iloscSamochodow.get() + 1; i++) {
                HelloApplication.listaSamochodow.get().set(i, new Samochod((i + 1), false, 4, 0, null, root, x, y - 100, false));
                //Powiększe tablicę listaSamochodowD
                Losuje.powiekszTablice(HelloApplication.listaSamochodowD, 1);
                HelloApplication.listaSamochodowD.get().set(0, (1));
            }
            HelloApplication.iloscSamochodow.set(HelloApplication.iloscSamochodow.get() + 1);
            System.out.println("IloscSamochodowpO:"+HelloApplication.iloscSamochodow.get());
        }*/
            image = new Image(getClass().getResource("/org/example/pw_projekt_gui/mw.png").toExternalForm());
            imageView = new ImageView(image);
            imageView.setFitWidth(70);
            imageView.setPreserveRatio(true);
            root.getChildren().add(imageView);
            imageView.setLayoutX(x);imageView.setLayoutY(y);
        //ImageView imageView = new ImageView(image);
        //((Pane) HelloApplication.root).getChildren().add(imageView);
        System.out.print("Jestem magazynem wyjsciowym o id="+id+", mam="+ilosc+" z ilością potrzebną do pakietu="+iloscNaPakiet+", max="+pojemnosc+"\n");
    }
    @Override
    public void run(){
        while(running){//!Thread.currentThread().isInterrupted()){
            if(HelloApplication.iloscLiniiProdukcyjnych.get()<=2){
                //Algorytm Dekkera
                System.out.println("MW-Dekker");
                if(ilosc>=iloscNaPakiet){
                    System.out.println("MW"+id+": pojemnosc: "+pojemnosc+" ilosc: "+ ilosc+" z ilością potrzebną do pakietu: "+iloscNaPakiet+", ilość pakietów: "+pakiety);
                    try {
                        Thread.sleep(los.nextInt(10) + 1);
                    } catch (InterruptedException e) {
                        break;//e.printStackTrace();
                    }
                    int drugi = 1 - (id);
                    chce[(id)] = true;
                    while (chce[drugi]) {
                        if (czyjaKolej == drugi) {
                            chce[(id+1)] = false;
                            while (czyjaKolej == drugi) {
                                // Czekaj
                                Thread.yield();
                            }
                            chce[(id+1)] = true;
                        }
                    }
                    // Sekcja krytyczna
                    for (int i = 0; i < HelloApplication.listaSamochodowD.get().length(); i++) {
                        int idSamochodu = HelloApplication.listaSamochodowD.get().get(i)-1;
                        Samochod samochod = HelloApplication.listaSamochodow.get().get(idSamochodu);
                        System.out.println("MW"+id+" sięga po S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                        MagazynWyjsciowy MW = HelloApplication.listaMagazynowWyjsciowych.get().get(id-1);
                        if(!samochod.pracuje&&samochod.obszar==4&&HelloApplication.semaMW.tryAcquire()){
                            System.out.println("MW"+id+" wywołuję S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                            samochod.sekcja4(samochod,MW);
                            break;
                        }
                        System.out.println("MW"+id+" otrzymał od S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i ma teraz:"+ilosc+" przy pojemnosci:"+pojemnosc);
                    }
                    // Koniec sekcji krytycznej
                    czyjaKolej = drugi;
                    chce[(id)] = false;
                }
            }else{ //Semafor - może co innego
                if(ilosc<iloscNaPakiet) {
                    int potrzeba = pojemnosc - ilosc;
                    System.out.println("MW" + id + ": pojemnosc: " + pojemnosc + " ilosc: " + ilosc + " potrzebuję " + potrzeba);
                    try {
                        HelloApplication.semaMNZ.acquire();
                        Thread.sleep(1000);
                        ilosc += potrzeba;
                        HelloApplication.semaMNZ.release();
                    } catch (InterruptedException e) {
                        break;//e.printStackTrace();
                    }
                    potrzeba = pojemnosc - ilosc;
                    System.out.println("MW" + id + ": pojemnosc: " + pojemnosc + " ilosc: " + ilosc + " NIE potrzebuję " + potrzeba);
                }
            }
            if(ilosc>=iloscNaPakiet)
            {
                System.out.println("MW"+id+": pojemnosc: "+pojemnosc+" ilosc: "+ ilosc+" z ilością potrzebną do pakietu: "+iloscNaPakiet+", ilość pakietów: "+pakiety);
                try {
                    Thread.sleep(los.nextInt(10) + 1);
                } catch (InterruptedException e) {
                    break;//e.printStackTrace();
                }
                while(ilosc>=iloscNaPakiet){
                    ilosc-=iloscNaPakiet;
                    try {
                        Thread.sleep(los.nextInt(10) + 1);
                    } catch (InterruptedException e) {
                        break;//e.printStackTrace();
                    }
                    pakiety+=1;
                }
                System.out.println("MW"+id+": pojemnosc: "+pojemnosc+" ilosc: "+ ilosc+" z ilością potrzebną do pakietu: "+iloscNaPakiet+", ilość pakietów: "+pakiety);
                try {
                    Thread.sleep(los.nextInt(100) + 1);
                } catch (InterruptedException e) {
                    break;//e.printStackTrace();
                }
                pakiety-=pakiety;
                System.out.println("MW"+id+": pojemnosc: "+pojemnosc+" ilosc: "+ ilosc+" z ilością potrzebną do pakietu: "+iloscNaPakiet+", ilość pakietów: "+pakiety);

            }
            System.out.println("MW działa w pętli!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Koniec MW.");
    }
    public void stop() {
        running = false;
    }
    public void remove(){
        root.getChildren().remove(imageView);
        image=null;
    }
}
