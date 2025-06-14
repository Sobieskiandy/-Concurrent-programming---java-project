package org.example.pw_projekt_gui;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import javafx.scene.layout.Pane;
public class Losuje {
    public static String[] NazwyZasobow={"Oak.png","Spruce.png","Birch.png","Jungle.png","Acacia.png","Dark_Oak.png","Mangrove.png","Cherry.png","Pale_Oak.png","Crimson.gif","Warped_Stem.gif"};
    public static <T> void zmienRozmiarTablicy(AtomicReference<AtomicReferenceArray<T>> lista, int nowyRozmiar) {
        AtomicReferenceArray<T> stara = lista.get();
        AtomicReferenceArray<T> nowa = new AtomicReferenceArray<>(nowyRozmiar);

        int ileKopiowac = Math.min(stara.length(), nowyRozmiar);
        for (int i = 0; i < ileKopiowac; i++) {
            nowa.set(i, stara.get(i));
        }
        lista.set(nowa);
    }
    public void domyslne0(AtomicInteger iloscZasobow, AtomicInteger iloscLiniiProdukcyjnych, AtomicInteger iloscMagazynowNaZasoby,AtomicInteger iloscMagazynowWyjsciowych, AtomicReferenceArray<String> listaZasobow, AtomicReference<AtomicReferenceArray<MagazynNaZasoby>> listaMagazynowNaZasoby, AtomicReference<AtomicReferenceArray<LiniaProdukcyjna>> listaLiniiProdukcyjnych, AtomicReference<AtomicReferenceArray<MagazynWyjsciowy>> listaMagazynowWyjsciowych, AtomicReference<Pane> root) {
        iloscZasobow.set(2);
        HelloApplication.listaZasobow = new AtomicReferenceArray<>(iloscZasobow.get());
        for(int i=0;i<iloscZasobow.get();i++) {
            HelloApplication.listaZasobow.set(i,NazwyZasobow[i]);
        }
        iloscMagazynowNaZasoby.set(2);
        System.out.print("Ilość magazynów na zasoby= "+iloscMagazynowNaZasoby+"\n");
        zmienRozmiarTablicy(listaMagazynowNaZasoby, iloscMagazynowNaZasoby.get());
        listaMagazynowNaZasoby.get().set(0, new MagazynNaZasoby(1, HelloApplication.listaZasobow.get(0), 3, 1, 6, root.get()));
        listaMagazynowNaZasoby.get().set(1, new MagazynNaZasoby(2, HelloApplication.listaZasobow.get(1), 2, 1, 5, root.get()));
        //}
        System.out.println("Linie produkcyjne");
        //LinieProdukcyjne
        iloscLiniiProdukcyjnych.set(3);
        zmienRozmiarTablicy(listaLiniiProdukcyjnych, iloscLiniiProdukcyjnych.get());
        listaLiniiProdukcyjnych.get().set(0, new LiniaProdukcyjna(1, HelloApplication.listaZasobow.get(0),false,root.get()));
        listaLiniiProdukcyjnych.get().set(1, new LiniaProdukcyjna(2, HelloApplication.listaZasobow.get(1),false,root.get()));
        listaLiniiProdukcyjnych.get().set(2, new LiniaProdukcyjna(3, HelloApplication.listaZasobow.get(0),false,root.get()));
        //MagazynyWyjsciowe
        System.out.println("Magazyn wyjściowy");
        {
            iloscMagazynowWyjsciowych.set(1);
            HelloApplication.MW1=new MagazynWyjsciowy(1,1,7,10,root.get());
            zmienRozmiarTablicy(listaMagazynowWyjsciowych, iloscMagazynowWyjsciowych.get());
            listaMagazynowWyjsciowych.get().set(0, HelloApplication.MW1);
        }
        //Samochody
        System.out.println("Samochody");
        HelloApplication.iloscSamochodow.set(4);
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get());
        //<->MNZ
        HelloApplication.listaSamochodow.get().set(0,new Samochod(1,false,1,0,null,root.get(),75,HelloApplication.listaMagazynowNaZasoby.get().get(0).y,true));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowA,1);HelloApplication.listaSamochodowA.get().set(0,(1));
        //MNZ<->LP
        HelloApplication.listaSamochodow.get().set(1,new Samochod(2,false,2,0,null,root.get(),155,HelloApplication.listaMagazynowNaZasoby.get().get(0).y,false));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowB,1);HelloApplication.listaSamochodowB.get().set(0,(2));
        //LP<->MW
        HelloApplication.listaSamochodow.get().set(2,new Samochod(3,false,3,0,null,root.get(),770,HelloApplication.listaMagazynowWyjsciowych.get().get(0).y,true));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowC,1);HelloApplication.listaSamochodowC.get().set(0,(3));
        //MW<->
        HelloApplication.listaSamochodow.get().set(3,new Samochod(4,false,4,0,null,root.get(),860,HelloApplication.listaMagazynowWyjsciowych.get().get(0).y,false));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowD,1);HelloApplication.listaSamochodowD.get().set(0,(4));
        System.out.println("IloscSamochodow:"+HelloApplication.iloscSamochodow.get());
        System.out.println("Koniec parametrów domyślnych");
    }
    public void losuj(AtomicInteger iloscZasobow, AtomicInteger iloscLiniiProdukcyjnych, AtomicInteger iloscMagazynowNaZasoby, AtomicReferenceArray<String> listaZasobow, AtomicReference<AtomicReferenceArray<MagazynNaZasoby>> listaMagazynowNaZasoby, AtomicReference<AtomicReferenceArray<LiniaProdukcyjna>> listaLiniiProdukcyjnych,AtomicReference<Pane> root){
        usun(iloscZasobow,iloscLiniiProdukcyjnych,iloscMagazynowNaZasoby,listaZasobow,listaMagazynowNaZasoby,listaLiniiProdukcyjnych,root);
        Random los = new Random();
        HelloApplication.iloscZasobow.set(los.nextInt(5)+2);
        HelloApplication.listaZasobow = new AtomicReferenceArray<>(iloscZasobow.get());
        for(int i=0;i<iloscZasobow.get();i++) {
            //int j=los.nextInt(9)+2;
            HelloApplication.listaZasobow.set(i,NazwyZasobow[i]);
        }
        do {
            iloscMagazynowNaZasoby.set(los.nextInt(5)+iloscZasobow.get());
        }while(iloscMagazynowNaZasoby.get()>=9);

        System.out.print("Ilość magazynów na zasoby= "+iloscMagazynowNaZasoby+"\n");
        //listaMagazynowNaZasoby = new AtomicReferenceArray<>(iloscMagazynowNaZasoby.get());
        zmienRozmiarTablicy(HelloApplication.listaMagazynowNaZasoby, HelloApplication.iloscMagazynowNaZasoby.get());
        for (int i = 0; i < iloscZasobow.get(); i++)
        {
            AtomicInteger id = new AtomicInteger(), pk= new AtomicInteger(), il= new AtomicInteger(), poj= new AtomicInteger();
            id.set(i+1);
            pk.set(los.nextInt(5)+2);
            il.set(los.nextInt(6)+1);
            do {poj.set(los.nextInt(10));}while(poj.get()<il.get()||poj.get()<pk.get());
            //listaMagazynowNaZasoby[i] = new MagazynNaZasoby(i+1, listaZasobow.get(i), pk, il, poj);//listaMagazynowNaZasoby.set(i, new MagazynNaZasoby(i+1, listaZasobow.get(i), pk, il, poj));
            HelloApplication.listaMagazynowNaZasoby.get().set(i, new MagazynNaZasoby(id.get(), HelloApplication.listaZasobow.get(i), pk.get(), il.get(), poj.get(),root.get()));
        }
        for (int i = iloscZasobow.get(); i < iloscMagazynowNaZasoby.get(); i++){
            int wyborZasobu;
            do {
                wyborZasobu = los.nextInt(iloscZasobow.get())+1;
            } while (wyborZasobu < 1 || wyborZasobu > iloscZasobow.get());
            wyborZasobu = wyborZasobu - 1;
            int id, pk, il, poj;
            id = i+1;
            pk=los.nextInt(5)+2;
            il=los.nextInt(6)+1;
            do {poj=los.nextInt(10);}while(poj<il||poj<pk);
            HelloApplication.listaMagazynowNaZasoby.get().set(i, new MagazynNaZasoby(id, HelloApplication.listaZasobow.get(wyborZasobu), pk, il, poj,root.get()));
        }
        System.out.println("Linie produkcyjne");
        //LinieProdukcyjne
        do {
            iloscLiniiProdukcyjnych.set(los.nextInt(4)+iloscZasobow.get());
        } while (iloscLiniiProdukcyjnych.get()>=9) ;
        //listaLiniiProdukcyjnych = new Thread[iloscLiniiProdukcyjnych.get()]; listaLiniiProdukcyjnych = new LiniaProdukcyjna[iloscLiniiProdukcyjnych.get()];
        zmienRozmiarTablicy(listaLiniiProdukcyjnych, iloscLiniiProdukcyjnych.get());
        for (int i = 0; i < iloscZasobow.get(); i++)
        {
            listaLiniiProdukcyjnych.get().set(i, new LiniaProdukcyjna(i+1, HelloApplication.listaZasobow.get(i),false,root.get()));
        }
        for (int i = iloscZasobow.get(); i < iloscLiniiProdukcyjnych.get(); i++) {
            int wyborZasobu = los.nextInt(iloscZasobow.get());
            listaLiniiProdukcyjnych.get().set(i, new LiniaProdukcyjna(i+1, HelloApplication.listaZasobow.get(wyborZasobu),false,root.get()));
            //listaLiniiProdukcyjnych.get().get(i).start();
        }
        //MagazynyWyjsciowe
        System.out.println("Magazyn wyjściowy");
        {   int il, pk, poj;
            il=los.nextInt(6)+1;
            pk=los.nextInt(5)+2;
            do {poj=los.nextInt(10);}while(poj<il||poj<pk);
            HelloApplication.iloscMagazynowWyjsciowych.set(1);
            HelloApplication.MW1=new MagazynWyjsciowy(1,il,pk,poj,root.get());
            zmienRozmiarTablicy(HelloApplication.listaMagazynowWyjsciowych, HelloApplication.iloscMagazynowWyjsciowych.get());
            HelloApplication.listaMagazynowWyjsciowych.get().set(0, HelloApplication.MW1);
        }
        System.out.println("Samochody");
        HelloApplication.iloscSamochodow.set(4);
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get());
        //<->MNZ
        HelloApplication.listaSamochodow.get().set(0,new Samochod(1,false,1,0,null,root.get(),75,HelloApplication.listaMagazynowNaZasoby.get().get(0).y,true));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowA,1);HelloApplication.listaSamochodowA.get().set(0,(1));
        //MNZ<->LP
        HelloApplication.listaSamochodow.get().set(1,new Samochod(2,false,2,0,null,root.get(),155,HelloApplication.listaMagazynowNaZasoby.get().get(0).y,false));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowB,1);HelloApplication.listaSamochodowB.get().set(0,(2));
        //LP<->MW
        HelloApplication.listaSamochodow.get().set(2,new Samochod(3,false,3,0,null,root.get(),770,HelloApplication.listaMagazynowWyjsciowych.get().get(0).y,true));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowC,1);HelloApplication.listaSamochodowC.get().set(0,(3));
        //MW<->
        HelloApplication.listaSamochodow.get().set(3,new Samochod(4,false,4,0,null,root.get(),860,HelloApplication.listaMagazynowWyjsciowych.get().get(0).y,false));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowD,1);HelloApplication.listaSamochodowD.get().set(0,(4));
        System.out.println("IloscSamochodow:"+HelloApplication.iloscSamochodow.get());
        System.out.println("Koniec losowania parametrów");
    }
    public void domyslne(AtomicInteger iloscZasobow, AtomicInteger iloscLiniiProdukcyjnych, AtomicInteger iloscMagazynowNaZasoby, AtomicReferenceArray<String> listaZasobow, AtomicReference<AtomicReferenceArray<MagazynNaZasoby>> listaMagazynowNaZasoby, /*AtomicReferenceArray<LiniaProdukcyjna>*/AtomicReference<AtomicReferenceArray<LiniaProdukcyjna>> listaLiniiProdukcyjnych,AtomicReference<Pane> root) {
        usun(iloscZasobow,iloscLiniiProdukcyjnych,iloscMagazynowNaZasoby,listaZasobow,listaMagazynowNaZasoby,listaLiniiProdukcyjnych,root);
        iloscZasobow.set(2);
        listaZasobow = new AtomicReferenceArray<>(iloscZasobow.get());
        for(int i=0;i<iloscZasobow.get();i++) {
            listaZasobow.set(i,NazwyZasobow[i]);
        }
        do {
            iloscMagazynowNaZasoby.set(2);
        }while(iloscMagazynowNaZasoby.get() < iloscZasobow.get());
        System.out.print("Ilość magazynów na zasoby= "+iloscMagazynowNaZasoby+"\n");
        zmienRozmiarTablicy(listaMagazynowNaZasoby, iloscMagazynowNaZasoby.get());

        listaMagazynowNaZasoby.get().set(0, new MagazynNaZasoby(1, listaZasobow.get(0), 3, 1, 6, root.get()));
        listaMagazynowNaZasoby.get().set(1, new MagazynNaZasoby(2, listaZasobow.get(1), 2, 1, 5, root.get()));
        System.out.println("Linie produkcyjne");
        iloscLiniiProdukcyjnych.set(3);
        zmienRozmiarTablicy(listaLiniiProdukcyjnych, iloscLiniiProdukcyjnych.get());

        listaLiniiProdukcyjnych.get().set(0, new LiniaProdukcyjna(1, listaZasobow.get(0),false,root.get()));
        listaLiniiProdukcyjnych.get().set(1, new LiniaProdukcyjna(2, listaZasobow.get(1),false,root.get()));
        listaLiniiProdukcyjnych.get().set(2, new LiniaProdukcyjna(3, listaZasobow.get(0),false,root.get()));
        //MagazynyWyjsciowe
        System.out.println("Magazyn wyjściowy");
        HelloApplication.iloscMagazynowWyjsciowych.set(1);
        HelloApplication.MW1=new MagazynWyjsciowy(1,1,7,10,root.get());
        zmienRozmiarTablicy(HelloApplication.listaMagazynowWyjsciowych, HelloApplication.iloscMagazynowWyjsciowych.get());
        HelloApplication.listaMagazynowWyjsciowych.get().set(0, HelloApplication.MW1);
        System.out.println("Samochody");
        HelloApplication.iloscSamochodow.set(4);
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get());
        //<->MNZ
        HelloApplication.listaSamochodow.get().set(0,new Samochod(1,false,1,0,null,root.get(),75,HelloApplication.listaMagazynowNaZasoby.get().get(0).y,true));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowA,1);HelloApplication.listaSamochodowA.get().set(0,(1));
        //MNZ<->LP
        HelloApplication.listaSamochodow.get().set(1,new Samochod(2,false,2,0,null,root.get(),155,HelloApplication.listaMagazynowNaZasoby.get().get(0).y,false));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowB,1);HelloApplication.listaSamochodowB.get().set(0,(2));
        //LP<->MW
        HelloApplication.listaSamochodow.get().set(2,new Samochod(3,false,3,0,null,root.get(),770,HelloApplication.listaMagazynowWyjsciowych.get().get(0).y,true));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowC,1);HelloApplication.listaSamochodowC.get().set(0,(3));
        //MW<->
        HelloApplication.listaSamochodow.get().set(3,new Samochod(4,false,4,0,null,root.get(),860,HelloApplication.listaMagazynowWyjsciowych.get().get(0).y,false));
        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowD,1);HelloApplication.listaSamochodowD.get().set(0,(4));
        System.out.println("IloscSamochodow:"+HelloApplication.iloscSamochodow.get());

        System.out.println("Koniec przywracania parametrów domyślnych");
    }
    public static void usun(AtomicInteger iloscZasobow, AtomicInteger iloscLiniiProdukcyjnych, AtomicInteger iloscMagazynowNaZasoby, AtomicReferenceArray<String> listaZasobow, AtomicReference<AtomicReferenceArray<MagazynNaZasoby>> listaMagazynowNaZasoby, /*AtomicReferenceArray<LiniaProdukcyjna>*/AtomicReference<AtomicReferenceArray<LiniaProdukcyjna>> listaLiniiProdukcyjnych,AtomicReference<Pane> root){
        //Usuwanie poprzednich
        for(int i=0;i<HelloApplication.iloscSamochodow.get();i++){
            Samochod s = HelloApplication.listaSamochodow.get().get(i);
            s.remove();
            HelloApplication.listaSamochodow.get().set(i,null);
        }
        HelloApplication.iloscSamochodow.set(0);
        zmienRozmiarTablicy(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get());
        for(int i=0; i<HelloApplication.listaSamochodowA.get().length(); i++){
            HelloApplication.listaSamochodowA.get().set(i,null);
        }
        zmienRozmiarTablicy(HelloApplication.listaSamochodowA, 0);
        for(int i=0; i<HelloApplication.listaSamochodowB.get().length(); i++){
            HelloApplication.listaSamochodowB.get().set(i,null);
        }
        zmienRozmiarTablicy(HelloApplication.listaSamochodowB, 0);
        for(int i=0; i<HelloApplication.listaSamochodowC.get().length(); i++){
            HelloApplication.listaSamochodowC.get().set(i,null);
        }
        zmienRozmiarTablicy(HelloApplication.listaSamochodowC, 0);
        for(int i=0; i<HelloApplication.listaSamochodowD.get().length(); i++){
            HelloApplication.listaSamochodowD.get().set(i,null);
        }
        zmienRozmiarTablicy(HelloApplication.listaSamochodowD, 0);
        for(int i=0;i<iloscMagazynowNaZasoby.get();i++){
            MagazynNaZasoby s = listaMagazynowNaZasoby.get().get(i);
            s.remove();
            listaMagazynowNaZasoby.get().set(i,null);
        }
        iloscMagazynowNaZasoby.set(0);
        zmienRozmiarTablicy(HelloApplication.listaMagazynowNaZasoby, HelloApplication.iloscMagazynowNaZasoby.get());
        for(int i=0;i<HelloApplication.iloscLiniiProdukcyjnych.get();i++){
            LiniaProdukcyjna s = listaLiniiProdukcyjnych.get().get(i);
            s.remove();
            listaLiniiProdukcyjnych.get().set(i,null);
        }
        HelloApplication.iloscLiniiProdukcyjnych.set(0);
        zmienRozmiarTablicy(HelloApplication.listaLiniiProdukcyjnych, HelloApplication.iloscLiniiProdukcyjnych.get());
        for(int i=0;i<HelloApplication.iloscMagazynowWyjsciowych.get();i++){
            MagazynWyjsciowy s = HelloApplication.listaMagazynowWyjsciowych.get().get(i);
            s.remove();
            HelloApplication.listaMagazynowWyjsciowych.get().set(i,null);
        }
        HelloApplication.iloscMagazynowWyjsciowych.set(0);
        zmienRozmiarTablicy(HelloApplication.listaMagazynowWyjsciowych, HelloApplication.iloscMagazynowWyjsciowych.get());
        for(int i=0;i<iloscZasobow.get();i++){
            HelloApplication.listaZasobow.set(i,null);
        }
        HelloApplication.iloscZasobow.set(0);
        //Usunięto poprzednie
    }
}