package org.example.pw_projekt_gui;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Random;
public class MagazynNaZasoby extends Magazyn{
    private Pane root;
    public String rzecz;
    public int poziomKrytyczny;
    private static volatile boolean[] chce = new boolean[2];
    private static volatile int czyjaKolej = 0;
    private final Random los = new Random();
    int potrzeba=0;
    int x=100;
    int y;
    ImageView imageView;
    Image image;
    private volatile boolean running = true;
    Text napisIl;
    Text napisPoj;
    Text napisPk;
    public MagazynNaZasoby(int id, String rzecz, int poziomKrytyczny, int ilosc, int pojemnosc, Pane root) {
        super(id, ilosc, pojemnosc);
        this.rzecz=rzecz;
        this.poziomKrytyczny = poziomKrytyczny;
        this.root=root;
        y=(id*70+(id-1)*20)-50;
        String s = getClass().getResource("/org/example/pw_projekt_gui/mnz/" + rzecz).toExternalForm();
        image = new Image(s);
        imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setPreserveRatio(true);
        root.getChildren().add(imageView);
        imageView.setLayoutX(x);imageView.setLayoutY(y);
        Text napisIl = new Text();napisIl.setText("il:"+ilosc);
        napisIl.setX(x);napisIl.setY(y-10);
        root.getChildren().add(napisIl);
        this.napisIl=napisIl;
        Text napisPoj = new Text();napisPoj.setText("Poj:"+pojemnosc);
        napisPoj.setX(x+20);napisPoj.setY(y-10);
        root.getChildren().add(napisPoj);
        this.napisPoj=napisPoj;
        Text napisPk = new Text();napisPk.setText("PK:"+poziomKrytyczny);
        napisPk.setX(x+55);napisPk.setY(y-10);
        root.getChildren().add(napisPk);
        this.napisPk=napisPk;
        System.out.print("Jestem magazynem na zasób "+rzecz+" o id="+id+", pk="+poziomKrytyczny+", mam="+ilosc+", max="+pojemnosc+"\n");
    }
    @Override
    public void run(){
        while(running){
            if(HelloApplication.iloscMagazynowNaZasoby.get()<=2){
                if(ilosc<poziomKrytyczny){
                    //Algorytm Petersona
                    potrzeba=pojemnosc-ilosc;
                    System.out.println("MNZ"+id+": pojemnosc: "+pojemnosc+" ilosc: "+ ilosc+" potrzebuję "+potrzeba+" zasobu "+rzecz+".");
                    try {
                        Thread.sleep(los.nextInt(10) + 1);
                    } catch (InterruptedException e) {
                        break;
                    }
                    int drugi = 1 - (id-1);
                    chce[(id-1)] = true;
                    czyjaKolej = drugi;
                    while (chce[drugi] && czyjaKolej == drugi) {
                        Thread.yield();
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
                            napisIl.setText("il:"+ilosc);
                            break;
                        }
                        System.out.println("MNZ"+id+" otrzymał od S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i ma teraz:"+ilosc+" przy pojemnosci:"+pojemnosc);
                    }
                    // Wyjście z sekcji krytycznej
                    chce[(id-1)] = false;
                    potrzeba = pojemnosc - ilosc;
                    System.out.println("MNZ"+id+": pojemnosc: "+pojemnosc+" ilosc: "+ ilosc+" NIE potrzebuję "+potrzeba+" zasobu "+rzecz+".");
                }
            }else{ //Semafor
                if(ilosc<poziomKrytyczny) {
                    potrzeba = pojemnosc - ilosc;
                    System.out.println("MNZ" + id + ": pojemnosc: " + pojemnosc + " ilosc: " + ilosc + " potrzebuję " + potrzeba + " zasobu " + rzecz + ".");
                    for(int i = 0; i < HelloApplication.listaSamochodowA.get().length(); i++) {
                        int idSamochodu = HelloApplication.listaSamochodowA.get().get(i)-1;
                        Samochod samochod = HelloApplication.listaSamochodow.get().get(idSamochodu);
                        System.out.println("MNZ"+id+" sięga po S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                        MagazynNaZasoby MNZ = HelloApplication.listaMagazynowNaZasoby.get().get(id-1);
                        if(!samochod.pracuje&&samochod.obszar==1&&HelloApplication.semaMNZ.tryAcquire()){
                            System.out.println("MNZ"+id+" wywołuję S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                            samochod.sekcja1(potrzeba,rzecz,samochod,MNZ);
                            napisIl.setText("il:"+ilosc);
                            break;
                        }
                        System.out.println("MNZ"+id+" otrzymał od S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i ma teraz:"+ilosc+" przy pojemnosci:"+pojemnosc);
                    }
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
    public void stop(){
        running = false;
    }
    public void remove(){
        root.getChildren().remove(imageView);
        image=null;
        root.getChildren().remove(napisIl);root.getChildren().remove(napisPoj);root.getChildren().remove(napisPk);
    }
}
