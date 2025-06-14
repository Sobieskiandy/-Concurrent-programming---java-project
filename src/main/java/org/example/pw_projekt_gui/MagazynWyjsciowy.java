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
import javafx.scene.text.Text;
public class MagazynWyjsciowy extends Magazyn{
    private Pane root;
    public int iloscNaPakiet;
    private final Random los = new Random();
    int x=800;
    int y=150;
    public Image image;
    public ImageView imageView;
    private volatile boolean running = true;
    Text napisIl;
    Text napisPoj;
    Text napisPk;
    public MagazynWyjsciowy(int id, int iloscProduktow, int iloscNaPakiet, int pojemnosc, Pane root){
        super(id, iloscProduktow, pojemnosc);
        this.iloscNaPakiet=iloscNaPakiet;
        this.root = root;
            image = new Image(getClass().getResource("/org/example/pw_projekt_gui/mw.png").toExternalForm());
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
        Text napisPk = new Text();napisPk.setText("PK:"+iloscNaPakiet);
        napisPk.setX(x+55);napisPk.setY(y-10);
        root.getChildren().add(napisPk);
        this.napisPk=napisPk;
        System.out.print("Jestem magazynem wyjsciowym o id="+id+", mam="+ilosc+" z ilością potrzebną do pakietu="+iloscNaPakiet+", max="+pojemnosc+"\n");
    }
    @Override
    public void run(){
        while(running){
            if(ilosc>=iloscNaPakiet){
                for (int i = 0; i < HelloApplication.listaSamochodowD.get().length(); i++) {
                    int idSamochodu = HelloApplication.listaSamochodowD.get().get(i)-1;
                    Samochod samochod = HelloApplication.listaSamochodow.get().get(idSamochodu);
                    System.out.println("MW"+id+" sięga po S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                    MagazynWyjsciowy MW = HelloApplication.listaMagazynowWyjsciowych.get().get(id-1);
                    if(!samochod.pracuje&&samochod.obszar==4/*&&HelloApplication.semaMW.tryAcquire()*/){
                        System.out.println("MW"+id+" wywołuję S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);
                        samochod.sekcja4(samochod,MW,iloscNaPakiet);
                        break;
                    }
                    System.out.println("MW"+id+" otrzymał od S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i ma teraz:"+MW.ilosc+" przy pojemnosci:"+MW.pojemnosc);
                }
            }
            System.out.println("MW działa w pętli!");
            try {
                Thread.sleep(1000*(los.nextInt(4)+1));
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
        root.getChildren().remove(napisIl);root.getChildren().remove(napisPoj);root.getChildren().remove(napisPk);
        image=null;
    }
}
