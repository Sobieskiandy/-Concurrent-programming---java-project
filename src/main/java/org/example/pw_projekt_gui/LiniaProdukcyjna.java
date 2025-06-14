package org.example.pw_projekt_gui;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;

public class LiniaProdukcyjna implements Runnable{
    int id;
    String zasob;
    boolean pracuje=false;
    boolean produkt=false;
    private volatile boolean running = true;
    private Pane root;
    int x=350;
    int y;
    Image image;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    private static volatile boolean[] chce = new boolean[2];
    private static volatile int czyjaKolej = 0; //ustala kolejność
    LiniaProdukcyjna(int id, String zasob, boolean produkt, Pane root) {
        this.id = id;
        this.zasob = zasob;
        this.produkt=produkt;
        this.root = root;
        y=(id*70+(id-1)*20)-50;
        image = new Image(getClass().getResource("/org/example/pw_projekt_gui/lp.jpg").toExternalForm());
        imageView = new ImageView(image);
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);
        root.getChildren().add(imageView);
        imageView.setLayoutX(x);imageView.setLayoutY(y);
        Image image1 = new Image(getClass().getResource("/org/example/pw_projekt_gui/log/" + zasob).toExternalForm());
        imageView1 = new ImageView(image1);
        imageView1.setFitWidth(40);
        imageView1.setPreserveRatio(true);
        imageView1.setOpacity(0.5);
        root.getChildren().add(imageView1);
        imageView1.setLayoutX(x-20);imageView1.setLayoutY(y-35);
        Image image2 = new Image(getClass().getResource("/org/example/pw_projekt_gui/door/" + zasob).toExternalForm());
        imageView2 = new ImageView(image2);
        imageView2.setFitWidth(40);
        imageView2.setPreserveRatio(true);
        imageView2.setOpacity(0.5);
        root.getChildren().add(imageView2);
        imageView2.setLayoutX(x+245);imageView2.setLayoutY(y-55);
        String d=" ";
        if(!pracuje)d=" nie ";
        System.out.print("Jestem linią produkcyjną na zasób "+zasob+" o id="+id+",obecnie"+d+"pracuję\n");
    }
    @Override
    public void run(){
        while(running){
            if(!pracuje&&!produkt){
                //System.out.println("LP"+id+": potrzebuję zasobu "+zasob+".");
                for (int i = 0; i < HelloApplication.listaSamochodowB.get().length(); i++){
                    int idSamochodu = HelloApplication.listaSamochodowB.get().get(i);
                    Samochod samochod = HelloApplication.listaSamochodow.get().get(idSamochodu-1);
                    LiniaProdukcyjna LP = HelloApplication.listaLiniiProdukcyjnych.get().get(id-1);
                    //System.out.println("LP"+LP.id+" sięga po S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                    if(!samochod.pracuje&&!produkt&&samochod.obszar==2&&HelloApplication.semaLP.tryAcquire()){
                        System.out.println("LP"+LP.id+" wywołuje S>"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+", LP: "+LP.id);
                        for (int j = 0; j < HelloApplication.listaMagazynowNaZasoby.get().length(); j++) {
                            MagazynNaZasoby MNZ = HelloApplication.listaMagazynowNaZasoby.get().get(j);
                            System.out.print("MNZZasob:"+MNZ.rzecz+"MNZIlosc:"+MNZ.ilosc+", LPZasob:"+LP.zasob);
                            if((MNZ.rzecz.equals(LP.zasob))&&(MNZ.ilosc>0)){
                                if(!samochod.pracuje&&!produkt&&samochod.obszar==2&&HelloApplication.semaLP1.tryAcquire()){
                                    System.out.println("LP"+LP.id+" wywołuję S%"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+", LP: "+LP.id+", MNZ: "+MNZ.id);
                                    samochod.sekcja2(zasob,samochod,MNZ,LP);
                                    LP.imageView1.setOpacity(0.5);
                                    LP.imageView2.setOpacity(1);
                                    System.out.println("LP"+LP.id+" otrzymał od S$"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i teraz pracuje:"+LP.pracuje);
                                    pracuje=true;
                                    HelloApplication.semaLP.release();HelloApplication.semaLP1.release();
                                    break;
                                }
                            }
                            else{System.out.println("NIE");HelloApplication.semaLP.release();}
                        }
                    }
                }
            }
            else if(pracuje&&!produkt){
                System.out.println("LP"+id+": zaczynam pracę!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                System.out.println("LP"+id+": kończę pracę!");
                pracuje=false;produkt=true;
            }
            else if(!pracuje&&produkt){
                System.out.println("LP"+id+": posiadam produkt!");
                if(HelloApplication.iloscLiniiProdukcyjnych.get()<=2){
                    //Algorytm Dekkera
                    //System.out.println("LP-Dekker");
                    //System.out.println("MW"+id+": pojemnosc: "+pojemnosc+" ilosc: "+ ilosc+" z ilością potrzebną do pakietu: "+iloscNaPakiet+", ilość pakietów: "+pakiety);
                    Random los = new Random();
                    try {
                        Thread.sleep(los.nextInt(10) + 1);
                    } catch (InterruptedException e) {
                        break;
                    }
                    int drugi = 1 - (id);
                    chce[(id)] = true;
                    while (chce[drugi]) {
                        if (czyjaKolej == drugi) {
                            chce[(id+1)] = false;
                            while (czyjaKolej == drugi) {
                                Thread.yield();
                            }
                            chce[(id+1)] = true;
                        }
                    }
                    // Sekcja krytyczna
                    MagazynWyjsciowy MW = HelloApplication.listaMagazynowWyjsciowych.get().get(0);
                    for (int i = 0; i < HelloApplication.listaSamochodowC.get().length(); i++) {
                        int idSamochodu = HelloApplication.listaSamochodowC.get().get(i)-1;
                        Samochod samochod = HelloApplication.listaSamochodow.get().get(idSamochodu);
                        System.out.println("MW"+id+" sięga po S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                        LiniaProdukcyjna LP = HelloApplication.listaLiniiProdukcyjnych.get().get(id-1);
                        if(!samochod.pracuje&&samochod.obszar==3&&HelloApplication.semaMW.tryAcquire()){
                            System.out.println("MW"+id+" wywołuję S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                            samochod.sekcja3(samochod,LP,MW);
                            produkt=false;pracuje=false;
                            break;
                        }
                        System.out.println("MW"+id+" otrzymał od S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i ma teraz:"+MW.ilosc+" przy pojemnosci:"+MW.pojemnosc);
                    }
                    // Koniec sekcji krytycznej
                    czyjaKolej = drugi;
                    chce[(id)] = false;
                }
                else{
                    for (int i = 0; i < HelloApplication.listaSamochodowC.get().length(); i++){
                        int idSamochodu = HelloApplication.listaSamochodowC.get().get(i);
                        Samochod samochod = HelloApplication.listaSamochodow.get().get(idSamochodu-1);
                        LiniaProdukcyjna LP = HelloApplication.listaLiniiProdukcyjnych.get().get(id-1);
                        System.out.println("LP"+LP.id+" sięga po S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                        System.out.println("LP"+LP.id+" wywołuję S#"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+", LP: "+LP.id);
                        if(!samochod.pracuje&&samochod.obszar==3&&HelloApplication.semaMW.tryAcquire()){
                            MagazynWyjsciowy MW = HelloApplication.MW1;
                            System.out.println("LP"+LP.id+" wywołuję S@"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+", LP: "+LP.id+", MW: "+MW.id);
                            samochod.sekcja3(samochod,LP,MW);
                            produkt=false;pracuje=false;
                            System.out.println("LP"+LP.id+" otrzymał od S!"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i teraz pracuje:"+LP.pracuje);
                            break;
                        }
                    }
                }
            }
            System.out.println("LP" + id + " działa w pętli!");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Koniec LP"+id+".");
    }
    public void stop() {
        running = false;
    }
    public void remove(){
        root.getChildren().remove(imageView);
        root.getChildren().remove(imageView1);
        root.getChildren().remove(imageView2);
        image=null;
    }
}
