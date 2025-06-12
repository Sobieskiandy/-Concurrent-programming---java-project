package org.example.pw_projekt_gui;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.concurrent.atomic.AtomicReferenceArray;
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
    LiniaProdukcyjna(int id, String zasob, boolean produkt, Pane root) {
        this.id = id;
        this.zasob = zasob;
        this.produkt=produkt;
        this.root = root;
        //System.out.println("Linia Produkcyjna id:"+id+" używa zasobu "+zasob);
        y=(id*70+(id-1)*20)-50;
        /*if(id==1){//chcę by pierwsza linia produkcyjna (LP) od razu tworzyła samochód,jeżdżący od LP do magazynu wyjściowego (MW)
            //System.out.println("IloscSamochodowPRZED:"+Main.iloscSamochodow.get());
            Losuje.powiekszTablice(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get()+1);
            for(int i=HelloApplication.iloscSamochodow.get();i<HelloApplication.iloscSamochodow.get()+1;i++)
            {
                //System.out.println("i:"+i);
                HelloApplication.listaSamochodow.get().set(i,new Samochod((i+1),false,3,0,null,root,x,y,false));
                //Powiększe tablicę listaSamochodowC
                Losuje.powiekszTablice(HelloApplication.listaSamochodowC,1);
                HelloApplication.listaSamochodowC.get().set(0,(1));
            }
            HelloApplication.iloscSamochodow.set(HelloApplication.iloscSamochodow.get()+1);
            //System.out.println("IloscSamochodowPO:"+Main.iloscSamochodow.get());
        }*/
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
    }
    @Override
    public void run(){
        while(running){//!Thread.currentThread().isInterrupted()){
            if(!pracuje&&!produkt){
                // UWAGA ! TUTAJ JEST BŁĄD KTÓRY POWODUJE BRAK KOŃCA WĄTKÓW
                System.out.println("LP"+id+": potrzebuję zasobu "+zasob+".");
                for (int i = 0; i < HelloApplication.listaSamochodowB.get().length(); i++){
                    int idSamochodu = HelloApplication.listaSamochodowB.get().get(i);
                    Samochod samochod = HelloApplication.listaSamochodow.get().get(idSamochodu-1);
                    LiniaProdukcyjna LP = HelloApplication.listaLiniiProdukcyjnych.get().get(id-1);//zmieniłem
                    System.out.println("LP"+LP.id+" sięga po S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);//+", LP: "+LP.id+", MNZ: "+MNZ.id);
                    if(!samochod.pracuje&&samochod.obszar==2&&HelloApplication.semaLP.tryAcquire()){
                        System.out.println("LP"+LP.id+" wywołuję S>"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+", LP: "+LP.id);
                        for (int j = 0; j < HelloApplication.listaMagazynowNaZasoby.get().length(); j++) {
                            MagazynNaZasoby MNZ = HelloApplication.listaMagazynowNaZasoby.get().get(j);
                            System.out.print("MNZZasob:"+MNZ.rzecz+", LPZasob:"+LP.zasob);
                            //MNZ.set(j, Main.listaMagazynowNaZasoby.get().get(id));
                            if(MNZ.rzecz.equals(LP.zasob)){
                                if(!samochod.pracuje&&samochod.obszar==2&&HelloApplication.semaLP1.tryAcquire()){
                                    System.out.println("LP"+LP.id+" wywołuję S%"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+", LP: "+LP.id+", MNZ: "+MNZ.id);
                                    samochod.sekcja2(zasob,samochod,MNZ,LP);
                                    LP.imageView1.setOpacity(0.5);
                                    LP.imageView2.setOpacity(1);
                                    System.out.println("LP"+LP.id+" otrzymał od S$"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i teraz pracuje:"+LP.pracuje);
                                    pracuje=true;
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
            else if(!pracuje&&produkt)
            {
                // UWAGA ! TUTAJ JEST BŁĄD KTÓRY POWODUJE BRAK KOŃCA WĄTKÓW
                System.out.println("LP"+id+": posiadam produkt!");
                for (int i = 0; i < HelloApplication.listaSamochodowC.get().length(); i++){
                    int idSamochodu = HelloApplication.listaSamochodowC.get().get(i);
                    Samochod samochod = HelloApplication.listaSamochodow.get().get(idSamochodu-1);
                    LiniaProdukcyjna LP = HelloApplication.listaLiniiProdukcyjnych.get().get(id-1);//zmieniłem
                    System.out.println("LP"+LP.id+" sięga po S"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar);//+", LP: "+LP.id+", MNZ: "+MNZ.id);
                    System.out.println("LP"+LP.id+" wywołuję S#"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+", LP: "+LP.id);
                    if(!samochod.pracuje&&samochod.obszar==3&&HelloApplication.semaMW.tryAcquire()){
                        MagazynWyjsciowy MW = HelloApplication.MW1;
                        System.out.println("LP"+LP.id+" wywołuję S@"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+", LP: "+LP.id+", MW: "+MW.id);
                        samochod.sekcja3(samochod,LP,MW);
                        System.out.println("LP"+LP.id+" otrzymał od S!"+samochod.id+" pracuje:"+samochod.pracuje+" obszar:"+samochod.obszar+" i teraz pracuje:"+LP.pracuje);
                        break;
                    }
                }
                produkt=false;pracuje=false;
            }
            System.out.println("LP"+id+" działa w pętli!");
            try {
                Thread.sleep(1000);  // śpij 1 sekundę
            } catch (InterruptedException e) {
                break;  // przerwij pętlę jeśli ktoś wywoła .interrupt()
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
