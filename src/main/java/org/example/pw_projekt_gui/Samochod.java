package org.example.pw_projekt_gui;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;

import java.util.Random;
public class Samochod implements Runnable{
    public int id;
    public boolean pracuje;//czy aktualnie jeździ(true) czy nie(false)
    public int obszar;
    private int ilosc;
    private String zasob;
    private int polozenie;
    private Pane root;
    private int x;
    private int y;
    Image image;
    public ImageView imageView;
    private volatile boolean running = true;
    public Samochod(int id, boolean pracuje, int obszar, int ilosc, String zasob, Pane root,int x,int y, boolean PL){
        String s=getClass().getResource("/org/example/pw_projekt_gui/samochod.jpg").toExternalForm();
        image = new Image(s);
        this.id = id;
        this.pracuje=pracuje;
        this.obszar=obszar;
        this.root=root;
        this.ilosc = ilosc;
        this.zasob = zasob;
        this.x=x;
        this.y=y;
        System.out.println("S"+id+" pracuje w obszarze "+obszar);
        this.imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        root.getChildren().add(imageView);
        imageView.setLayoutX(x);imageView.setLayoutY(y);
        if(PL==true){this.imageView.setRotationAxis(Rotate.Y_AXIS);this.imageView.setRotate(180);}
    }
    public void sekcja1(int ilos, String zasob, Samochod samochod,MagazynNaZasoby MNZ) {
        samochod.pracuje=true;
        samochod.zasob=zasob;
        try {
            TranslateTransition tt = new TranslateTransition();
            tt.setNode(samochod.imageView);
            tt.setByX(-MNZ.x);
            tt.setDuration(Duration.seconds(1.5));
            tt.setAutoReverse(true);
            tt.setCycleCount(1);
            tt.play();
            Thread.sleep(3000);//jedzie po zasob
            samochod.imageView.setRotationAxis(Rotate.Y_AXIS);
            samochod.imageView.setRotate(0);
            System.out.println("S"+id +" w obszarze "+obszar+" przyjechałem po zasób "+zasob+" dla magazynu "+MNZ.id+", mam go teraz "+ilosc);
            ilosc+=ilos;
            Thread.sleep(3000);;//bierze zasób
            System.out.println("S"+id+" w obszarze "+obszar+" bierze zasób "+zasob+" dla magazynu "+MNZ.id+", mam go teraz "+ilosc);
            tt.setNode(samochod.imageView);
            System.out.println("S1y:"+samochod.y);
            System.out.println("S2MNZ"+MNZ.id+"y:"+MNZ.y);
            if(samochod.y>MNZ.y){int a=-(samochod.y-MNZ.y);samochod.y+=a;System.out.println("S1y:"+a);tt.setByY(a);}
            else if(samochod.y<MNZ.y){int a=MNZ.y-samochod.y;samochod.y+=a;System.out.println("S1y:"+a);tt.setByY(a);}
            else if(samochod.y==MNZ.y){tt.setByY(0);}
            tt.setByX(MNZ.x);
            tt.setDuration(Duration.seconds(1.5));
            tt.setAutoReverse(true);
            tt.setCycleCount(1);
            tt.play();
            Thread.sleep(3000);//wraca z zasobem
            System.out.println("S"+id+" w obszarze "+obszar+" wracam z zasobem "+zasob+" dla magazynu "+MNZ.id+", mam go teraz "+ilosc);
            Thread.sleep(3000);//rozładowuje zasób
            samochod.imageView.setRotationAxis(Rotate.Y_AXIS);
            samochod.imageView.setRotate(180);
            MNZ.ilosc+=ilosc;
            samochod.pracuje=false;
            ilosc-=ilos;
            samochod.zasob=null;
            System.out.println("S"+id+" w obszarze "+obszar+" przetransportował zasób "+zasob+" do magazynu "+MNZ.id+", mam go teraz "+ilosc);
            HelloApplication.semaMNZ.release();
        } catch (InterruptedException e) {
            return;
        }
    }
    public void sekcja2(String zasob, Samochod samochod,MagazynNaZasoby MNZ, LiniaProdukcyjna LP){
        samochod.pracuje=true;
        samochod.zasob=zasob;
        try {
            TranslateTransition tt = new TranslateTransition();
            tt.setNode(samochod.imageView);
            if(samochod.y>MNZ.y){int a=-(samochod.y-MNZ.y);samochod.y+=a;System.out.println("S3y:"+a);tt.setByY(a);}
            else if(samochod.y<MNZ.y){int a=MNZ.y-samochod.y;samochod.y+=a;System.out.println("S3y:"+a);tt.setByY(a);}
            else if(samochod.y==MNZ.y){tt.setByY(0);}
            tt.setByX(0);
            tt.setDuration(Duration.seconds(1.5));
            tt.setAutoReverse(true);
            tt.setCycleCount(1);
            tt.play();
            System.out.println("S"+samochod.id +" w obszarze "+samochod.obszar+" jedzie do magazynu "+MNZ.id+" po zasób "+zasob+" dla linii produkcyjnej "+LP.id+", mam go teraz "+samochod.ilosc);
            Thread.sleep(3000);//jedzie po produkt
            MNZ.ilosc-=1; MNZ.napisIl.setText("il:"+MNZ.ilosc); samochod.ilosc+=1;
            System.out.println("S"+samochod.id +" w obszarze "+samochod.obszar+" przyjechałem do magazynu "+MNZ.id+" po zasób "+zasob+" dla linii produkcyjnej "+LP.id+", mam go teraz "+samochod.ilosc);
            tt = new TranslateTransition();
            tt.setNode(samochod.imageView);
            if(samochod.y>LP.y){int a=-(samochod.y-LP.y);samochod.y+=a;System.out.println("S3y:"+a);tt.setByY(a);}
            else if(samochod.y<LP.y){int a=LP.y-samochod.y;samochod.y+=a;System.out.println("S3y:"+a);tt.setByY(a);}
            else if(samochod.y==LP.y){tt.setByY(0);}
            tt.setByX(150);
            tt.setDuration(Duration.seconds(1.5));
            tt.setAutoReverse(true);
            tt.setCycleCount(1);
            tt.play();
            System.out.println("S"+samochod.id +" w obszarze "+samochod.obszar+" jedzie do linii produkcyjnej "+LP.id+" z zasobem "+zasob+", mam go teraz "+samochod.ilosc);
            System.out.println("S"+samochod.id +" w obszarze "+samochod.obszar+" przyjechałem do magazynu "+MNZ.id+" po zasób "+zasob+" dla linii produkcyjnej "+LP.id+", mam go teraz "+samochod.ilosc);
            Thread.sleep(3000);//rozładowuje zasób
            LP.imageView1.setOpacity(1);
            samochod.imageView.setRotationAxis(Rotate.Y_AXIS);
            samochod.imageView.setRotate(180);
            samochod.ilosc-=1;
            LP.pracuje=true;
            System.out.println("S"+samochod.id+" w obszarze "+samochod.obszar+" wraca do magazynu "+MNZ.id+", S"+samochod.id+" mam  teraz "+ilosc+"zasobu "+samochod.zasob+".");
            tt.setNode(samochod.imageView);
            tt.setDuration(Duration.seconds(1.5));
            System.out.println("S2y:"+samochod.y);
            System.out.println("S2MNZ"+MNZ.id+"y:"+MNZ.y);
            if(samochod.y>MNZ.y){int a=-(samochod.y-MNZ.y);samochod.y+=a;System.out.println("S2y:"+a);tt.setByY(a);}
            else if(samochod.y<MNZ.y){int a=MNZ.y-samochod.y;samochod.y+=a;System.out.println("S2y:"+a);tt.setByY(a);}
            else if(samochod.y==MNZ.y){tt.setByY(0);}
            tt.setByX(-150);
            tt.setAutoReverse(true);
            tt.setCycleCount(1);
            tt.play();
            Thread.sleep(3000);//jedzie z zasobem
            samochod.imageView.setRotationAxis(Rotate.Y_AXIS);
            samochod.imageView.setRotate(0);
            System.out.println("S"+samochod.id+" w obszarze "+samochod.obszar+" przetransportował zasób "+samochod.zasob+" do linii produkcyjnej "+LP.id+", mam go teraz "+samochod.ilosc);
            HelloApplication.semaLP.release();HelloApplication.semaLP1.release();
        } catch (InterruptedException e) {
            return;
        }
        samochod.pracuje=false;
        samochod.zasob=null;
    }
    public void sekcja3(Samochod samochod,LiniaProdukcyjna LP, MagazynWyjsciowy MW) {
        samochod.pracuje=true;
        samochod.zasob=zasob;
        try {
            TranslateTransition tt = new TranslateTransition();
            tt.setNode(samochod.imageView);
            if(samochod.y>LP.y){int a=-(samochod.y-LP.y);samochod.y+=a;System.out.println("S3y:"+a);tt.setByY(a);}
            else if(samochod.y<LP.y){int a=LP.y-samochod.y;samochod.y+=a;System.out.println("S3y:"+a);tt.setByY(a);}
            else if(samochod.y==LP.y){tt.setByY(0);}
            tt.setByX(-220);
            tt.setDuration(Duration.seconds(1.5));
            tt.setAutoReverse(true);
            tt.setCycleCount(1);
            tt.play();
            Thread.sleep(3000);//jedzie po produkt
            samochod.imageView.setRotationAxis(Rotate.Y_AXIS);
            samochod.imageView.setRotate(0);
            System.out.println("S"+id +" w obszarze "+obszar+" przyjechałem po produkt od linii produkcyjnej "+LP.id+", mam go teraz "+ilosc);
            LP.produkt=false; ilosc+=1;
            Thread.sleep(3000);//bierze produkt
            System.out.println("S"+id+" w obszarze "+obszar+" bierze produkt z linii produkcyjnej "+LP.id+", mam go teraz "+ilosc);
            tt.setNode(samochod.imageView);
            System.out.println("S3y:"+samochod.y);
            System.out.println("S3MNZ"+LP.id+"y:"+MW.y);
            if(samochod.y>MW.y){int a=-(samochod.y-MW.y);samochod.y+=a;System.out.println("S3y:"+a);tt.setByY(a);}
            else if(samochod.y<MW.y){int a=MW.y-samochod.y;samochod.y+=a;System.out.println("S3y:"+a);tt.setByY(a);}
            else if(samochod.y==MW.y){tt.setByY(0);}
            tt.setByX(220);
            tt.setDuration(Duration.seconds(1.5));
            tt.setAutoReverse(true);
            tt.setCycleCount(1);
            LP.imageView2.setOpacity(0.5);
            tt.play();
            Thread.sleep(1500);//wraca z produktem
            Thread.sleep(1500);
            System.out.println("S"+id+" w obszarze "+obszar+" wracam z produktem od linii produkcyjnej "+LP.id+", mam go teraz "+ilosc);
            Thread.sleep(3000);//rozładowuje produkt
            samochod.imageView.setRotationAxis(Rotate.Y_AXIS);
            samochod.imageView.setRotate(180);
            MW.ilosc+=1; MW.napisIl.setText("il:"+MW.ilosc); ilosc-=1;
            samochod.pracuje=false;
            System.out.println("S"+id+" w obszarze "+obszar+" przetransportował produkt do linii produkcyjnej "+LP.id+", mam go teraz "+ilosc);
            HelloApplication.semaMW.release();
        } catch (InterruptedException e) {
            return;
        }
    }
    public void sekcja4(Samochod samochod, MagazynWyjsciowy MW,int iloscNaPakiet) {
        samochod.pracuje=true;
        samochod.zasob=zasob;
        try {
            System.out.println("S"+id+" w obszarze "+obszar+" załadowałem pakiet z magazynu wyjściowego "+MW.id+", mam go teraz "+ilosc);
            while(MW.ilosc>=iloscNaPakiet){MW.ilosc-=iloscNaPakiet; ilosc+=1;}
            MW.napisIl.setText("il:"+MW.ilosc);
            Thread.sleep(3000);//załadowanie pakietu
            TranslateTransition tt = new TranslateTransition();
            tt.setNode(samochod.imageView);
            tt.setByX(100);
            tt.setDuration(Duration.seconds(1.5));
            tt.setAutoReverse(true);
            tt.setCycleCount(1);
            tt.play();
            Thread.sleep(3000);//zawożę pakiet
            System.out.println("S"+id +" w obszarze "+obszar+" zawożę pakiet z magazynu wyjściowego "+MW.id+", mam go teraz "+ilosc);
            samochod.imageView.setRotationAxis(Rotate.Y_AXIS);
            samochod.imageView.setRotate(180);
            tt.setNode(samochod.imageView);
            tt.setByX(-100);
            ilosc-=ilosc;
            tt.setNode(samochod.imageView);
            tt.setDuration(Duration.seconds(1.5));
            tt.setAutoReverse(true);
            tt.setCycleCount(1);
            tt.play();
            System.out.println("S"+id+" w obszarze "+obszar+" wracam, przetransportował pakiet, mam go teraz "+ilosc);
            Thread.sleep(3000);//rozładowuje pakiet
            samochod.imageView.setRotationAxis(Rotate.Y_AXIS);
            samochod.imageView.setRotate(0);
            samochod.pracuje=false;
            System.out.println("S"+id+" w obszarze "+obszar+" wróciłem do lmagazynu wyjściowego "+MW.id+", mam go teraz "+ilosc);
            HelloApplication.semaMW.release();
        } catch (InterruptedException e) {
            return;
        }
    }
    @Override
    public void run(){
        while(running||!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Koniec Samochodu"+id+".");
    }
    public void stop() {
        running = false;
    }
    public void remove(){
        root.getChildren().remove(imageView);
        image=null;
    }
}