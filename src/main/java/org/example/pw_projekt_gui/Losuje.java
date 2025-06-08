package org.example.pw_projekt_gui;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import javafx.scene.layout.Pane;
public class Losuje {
    //public void powiekszTablice(/*AtomicReferenceArray<LiniaProdukcyjna>*/AtomicReference<AtomicReferenceArray<LiniaProdukcyjna>> lista,int nowy) {AtomicReferenceArray<LiniaProdukcyjna> nowa = new AtomicReferenceArray<>(nowy);/*for (int i = 0; i < Math.min(lista.length(), nowa.length()); i++) {nowa.set(i, lista.get(i));}*/System.out.println("Rozmiar tablicy w=" + nowa.length());lista.set(nowa);}
    public static <T> void powiekszTablice(/*AtomicReferenceArray<T>*/AtomicReference<AtomicReferenceArray<T>> lista,int nowy) {
        //zmieniłem dla <T> by działało dla każdej listy którą dam.
        AtomicReferenceArray<T> nowa = new AtomicReferenceArray<>(nowy);
        AtomicReferenceArray<T> stara = lista.get();
        for (int i = 0; i < Math.min(stara.length(), nowa.length()); i++) {
            nowa.set(i, stara.get(i));
        }
        //System.out.println("Rozmiar tablicy w="+nowa.length());
        lista.set(nowa);
    }
    public void domyslne0(AtomicInteger iloscZasobow, AtomicInteger iloscLiniiProdukcyjnych, AtomicInteger iloscMagazynowNaZasoby,AtomicInteger iloscMagazynowWyjsciowych, AtomicReferenceArray<String> listaZasobow, AtomicReference<AtomicReferenceArray<MagazynNaZasoby>> listaMagazynowNaZasoby, /*AtomicReferenceArray<LiniaProdukcyjna>*/AtomicReference<AtomicReferenceArray<LiniaProdukcyjna>> listaLiniiProdukcyjnych, AtomicReference<AtomicReferenceArray<MagazynWyjsciowy>> listaMagazynowWyjsciowych, Pane root) {
        iloscZasobow.set(2);//los.nextInt(11)+1);
        listaZasobow = new AtomicReferenceArray<>(iloscZasobow.get());
        String[] NazwyZasobow={"Oak.png","Spruce.png","Birch.png","Jungle.png","Acacia.png","Dark_Oak.png","Mangrove.png","Cherry.png","Pale_Oak.png","Crimson.gif","Warped_Stem.gif"};
        for(int i=0;i<iloscZasobow.get();i++) {
            //System.out.print("Podaj nazwę dla zasobu "+(i+1)+":");
            listaZasobow.set(i,NazwyZasobow[i]);
        }
            iloscMagazynowNaZasoby.set(2);//los.nextInt(5)+iloscZasobow.get());
        System.out.print("Ilość magazynów na zasoby= "+iloscMagazynowNaZasoby+"\n");
        //listaMagazynowNaZasoby = new AtomicReferenceArray<>(iloscMagazynowNaZasoby.get());
        powiekszTablice(listaMagazynowNaZasoby, iloscMagazynowNaZasoby.get());
        /*for (int i = 0; i < iloscZasobow.get(); i++)
        {
            AtomicInteger id = new AtomicInteger(), pk= new AtomicInteger(), il= new AtomicInteger(), poj= new AtomicInteger();
            id.set(i+1);
            pk.set(los.nextInt(5)+2);
            il.set(los.nextInt(6)+1);
            do {poj.set(los.nextInt(10));}while(poj.get()<il.get()||poj.get()<pk.get());
            //listaMagazynowNaZasoby[i] = new MagazynNaZasoby(i+1, listaZasobow.get(i), pk, il, poj);//listaMagazynowNaZasoby.set(i, new MagazynNaZasoby(i+1, listaZasobow.get(i), pk, il, poj));
            listaMagazynowNaZasoby.get().set(i, new MagazynNaZasoby(id.get(), listaZasobow.get(i), pk.get(), il.get(), poj.get(), root));*/
        listaMagazynowNaZasoby.get().set(0, new MagazynNaZasoby(1, listaZasobow.get(0), 3, 1, 6, root));
        listaMagazynowNaZasoby.get().set(1, new MagazynNaZasoby(2, listaZasobow.get(1), 2, 1, 5, root));
        //}
        System.out.println("Linie produkcyjne");
        //LinieProdukcyjne
        //do {
            iloscLiniiProdukcyjnych.set(3);//los.nextInt(4)+iloscZasobow.get());
        //} while (iloscLiniiProdukcyjnych.get() < iloscZasobow.get());
        //listaLiniiProdukcyjnych = new Thread[iloscLiniiProdukcyjnych.get()]; listaLiniiProdukcyjnych = new LiniaProdukcyjna[iloscLiniiProdukcyjnych.get()];
        powiekszTablice(listaLiniiProdukcyjnych, iloscLiniiProdukcyjnych.get());
        /*
        for (int i = 0; i < iloscZasobow.get(); i++)
        {
            listaLiniiProdukcyjnych.get().set(i, new LiniaProdukcyjna(i+1, listaZasobow.get(i),false,root));
        }
        for (int i = iloscZasobow.get(); i < iloscLiniiProdukcyjnych.get(); i++) {//             TU ZMIENIĆ
            int wyborZasobu = los.nextInt(iloscZasobow.get());
            listaLiniiProdukcyjnych.get().set(i, new LiniaProdukcyjna(i+1, listaZasobow.get(wyborZasobu),false,root));
            //listaLiniiProdukcyjnych.get().get(i).start();
        }*/
        listaLiniiProdukcyjnych.get().set(0, new LiniaProdukcyjna(1, listaZasobow.get(0),false,root));
        listaLiniiProdukcyjnych.get().set(1, new LiniaProdukcyjna(2, listaZasobow.get(1),false,root));
        listaLiniiProdukcyjnych.get().set(2, new LiniaProdukcyjna(3, listaZasobow.get(0),false,root));
        //MagazynyWyjsciowe
        System.out.println("Magazyn wyjściowy");
        {
            iloscMagazynowWyjsciowych.set(1);
            HelloApplication.MW1=new MagazynWyjsciowy(1,1,0,7,10,root);
            powiekszTablice(listaMagazynowWyjsciowych, iloscMagazynowWyjsciowych.get());
            listaMagazynowWyjsciowych.get().set(0, HelloApplication.MW1);
        }
        //Samochody
        System.out.println("Samochody");
        HelloApplication.iloscSamochodow.set(4);
        Losuje.powiekszTablice(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get());
        //Losuje.powiekszTablice(HelloApplication.listaImageViewSamochodow, HelloApplication.iloscSamochodow.get());
        //<->MNZ
        HelloApplication.listaSamochodow.get().set(0,new Samochod(1,false,1,0,null,root,75,105,true));
        Losuje.powiekszTablice(HelloApplication.listaSamochodowA,1);HelloApplication.listaSamochodowA.get().set(0,(1));
        //MNZ<->LP
        HelloApplication.listaSamochodow.get().set(1,new Samochod(2,false,2,0,null,root,155,105,false));
        Losuje.powiekszTablice(HelloApplication.listaSamochodowB,1);HelloApplication.listaSamochodowB.get().set(0,(2));
        //LP<->MW
        HelloApplication.listaSamochodow.get().set(2,new Samochod(3,false,3,0,null,root,870,250,true));
        Losuje.powiekszTablice(HelloApplication.listaSamochodowC,1);HelloApplication.listaSamochodowC.get().set(0,(3));
        //MW<->
        HelloApplication.listaSamochodow.get().set(3,new Samochod(4,false,4,0,null,root,960,250,false));
        Losuje.powiekszTablice(HelloApplication.listaSamochodowD,1);HelloApplication.listaSamochodowD.get().set(0,(4));
        System.out.println("IloscSamochodow:"+HelloApplication.iloscSamochodow.get());
        System.out.println("Koniec parametrów domyślnych");
    }
    public void losuj(AtomicInteger iloscZasobow, AtomicInteger iloscLiniiProdukcyjnych, AtomicInteger iloscMagazynowNaZasoby, AtomicReferenceArray<String> listaZasobow, AtomicReference<AtomicReferenceArray<MagazynNaZasoby>> listaMagazynowNaZasoby, /*AtomicReferenceArray<LiniaProdukcyjna>*/AtomicReference<AtomicReferenceArray<LiniaProdukcyjna>> listaLiniiProdukcyjnych,Pane root){
        Random los = new Random();
        //Scanner scanner = new Scanner(System.in);
        iloscZasobow.set(los.nextInt(10)+2);
        listaZasobow = new AtomicReferenceArray<>(iloscZasobow.get());
        //scanner = new Scanner(System.in);
        String[] NazwyZasobow={"Oak_Log.png","Spruce_Log.png","Birch_Log.png","Jungle_Log.png","Acacia_Log.png","Dark_Oak_Log.png","Mangrove_Log.png","Cherry_Log.png","Pale_Oak.png","Crimson_Stem.gif","Warped_Stem.gif"};
        for(int i=0;i<iloscZasobow.get();i++) {
            //System.out.print("Podaj nazwę dla zasobu "+(i+1)+":");
            listaZasobow.set(i,NazwyZasobow[i]);
        }
        do {
            iloscMagazynowNaZasoby.set(los.nextInt(5)+iloscZasobow.get());
        }while(iloscMagazynowNaZasoby.get() < iloscZasobow.get());
        System.out.print("Ilość magazynów na zasoby= "+iloscMagazynowNaZasoby+"\n");
        //listaMagazynowNaZasoby = new AtomicReferenceArray<>(iloscMagazynowNaZasoby.get());
        powiekszTablice(listaMagazynowNaZasoby, iloscMagazynowNaZasoby.get());
        for (int i = 0; i < iloscZasobow.get(); i++)
        {
            AtomicInteger id = new AtomicInteger(), pk= new AtomicInteger(), il= new AtomicInteger(), poj= new AtomicInteger();
            id.set(i+1);
            pk.set(los.nextInt(5)+2);
            il.set(los.nextInt(6)+1);
            do {poj.set(los.nextInt(10));}while(poj.get()<il.get()||poj.get()<pk.get());
            //listaMagazynowNaZasoby[i] = new MagazynNaZasoby(i+1, listaZasobow.get(i), pk, il, poj);//listaMagazynowNaZasoby.set(i, new MagazynNaZasoby(i+1, listaZasobow.get(i), pk, il, poj));
            listaMagazynowNaZasoby.get().set(i, new MagazynNaZasoby(id.get(), listaZasobow.get(i), pk.get(), il.get(), poj.get(),root));
        }
        /*for (int i = iloscZasobow.get(); i < iloscLiniiProdukcyjnych.get(); i++) {
            int wyborZasobu = los.nextInt(iloscZasobow.get());
            listaLiniiProdukcyjnych.get().set(i, new LiniaProdukcyjna(i+1, listaZasobow.get(wyborZasobu)));
            //listaLiniiProdukcyjnych.get().get(i).start();
        }*/
        //powiekszTablice(listaMagazynowNaZasoby, iloscMagazynowNaZasoby.get());

        for (int i = iloscZasobow.get(); i < iloscMagazynowNaZasoby.get(); i++) {
            //scanner = new Scanner(System.in);
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
            listaMagazynowNaZasoby.get().set(i, new MagazynNaZasoby(id, listaZasobow.get(wyborZasobu), pk, il, poj,root));
            //listaMagazynowNaZasoby.get().get(i).start();
        }
        System.out.println("Linie produkcyjne");
        //LinieProdukcyjne
        do {
            iloscLiniiProdukcyjnych.set(los.nextInt(4)+iloscZasobow.get());
        } while (iloscLiniiProdukcyjnych.get() < iloscZasobow.get());
        //listaLiniiProdukcyjnych = new Thread[iloscLiniiProdukcyjnych.get()]; listaLiniiProdukcyjnych = new LiniaProdukcyjna[iloscLiniiProdukcyjnych.get()];
        powiekszTablice(listaLiniiProdukcyjnych, iloscLiniiProdukcyjnych.get());
        for (int i = 0; i < iloscZasobow.get(); i++)
        {
            listaLiniiProdukcyjnych.get().set(i, new LiniaProdukcyjna(i+1, listaZasobow.get(i),false,root));
        }
        for (int i = iloscZasobow.get(); i < iloscLiniiProdukcyjnych.get(); i++) {
            int wyborZasobu = los.nextInt(iloscZasobow.get());
            listaLiniiProdukcyjnych.get().set(i, new LiniaProdukcyjna(i+1, listaZasobow.get(wyborZasobu),false,root));
            //listaLiniiProdukcyjnych.get().get(i).start();
        }
        //MagazynyWyjsciowe
        /*
        System.out.println("Magazyn wyjściowy");
        {   int il, pk, poj;
            il=los.nextInt(6)+1;
            pk=los.nextInt(5)+2;
            do {poj=los.nextInt(10);}while(poj<il||poj<pk);
            HelloApplication.MW1=new MagazynWyjsciowy(1,il,pk,poj,root);
        }*/

        System.out.println("Koniec losowania parametrów");
    }
    public void domyslne(AtomicInteger iloscZasobow, AtomicInteger iloscLiniiProdukcyjnych, AtomicInteger iloscMagazynowNaZasoby, AtomicReferenceArray<String> listaZasobow, AtomicReference<AtomicReferenceArray<MagazynNaZasoby>> listaMagazynowNaZasoby, /*AtomicReferenceArray<LiniaProdukcyjna>*/AtomicReference<AtomicReferenceArray<LiniaProdukcyjna>> listaLiniiProdukcyjnych,Pane root) {
        iloscZasobow.set(2);//los.nextInt(11)+1);
        listaZasobow = new AtomicReferenceArray<>(iloscZasobow.get());
        //scanner = new Scanner(System.in);
        String[] NazwyZasobow={"Oak_Log.png","Spruce_Log.png","Birch_Log.png","Jungle_Log.png","Acacia_Log.png","Dark_Oak_Log.png","Mangrove_Log.png","Cherry_Log.png","Pale_Oak.png","Crimson_Stem.gif","Warped_Stem.gif"};
        for(int i=0;i<iloscZasobow.get();i++) {
            //System.out.print("Podaj nazwę dla zasobu "+(i+1)+":");
            listaZasobow.set(i,NazwyZasobow[i]);
        }
        do {
            iloscMagazynowNaZasoby.set(2);//los.nextInt(5)+iloscZasobow.get());
        }while(iloscMagazynowNaZasoby.get() < iloscZasobow.get());
        System.out.print("Ilość magazynów na zasoby= "+iloscMagazynowNaZasoby+"\n");
        //listaMagazynowNaZasoby = new AtomicReferenceArray<>(iloscMagazynowNaZasoby.get());
        powiekszTablice(listaMagazynowNaZasoby, iloscMagazynowNaZasoby.get());
        /*
        for (int i = 0; i < iloscZasobow.get(); i++)
        {
            AtomicInteger id = new AtomicInteger(), pk= new AtomicInteger(), il= new AtomicInteger(), poj= new AtomicInteger();
            id.set(i+1);
            pk.set(los.nextInt(5)+2);//}while(pk<1);
            il.set(los.nextInt(6)+1);//}while(il<0);
            do {poj.set(los.nextInt(10));}while(poj.get()<il.get()||poj.get()<pk.get());
            //listaMagazynowNaZasoby[i] = new MagazynNaZasoby(i+1, listaZasobow.get(i), pk, il, poj);//listaMagazynowNaZasoby.set(i, new MagazynNaZasoby(i+1, listaZasobow.get(i), pk, il, poj));
            listaMagazynowNaZasoby.get().set(i, new MagazynNaZasoby(id.get(), listaZasobow.get(i), pk.get(), il.get(), poj.get(),root));
        }*/
        listaMagazynowNaZasoby.get().set(0, new MagazynNaZasoby(1, listaZasobow.get(0), 3, 1, 6, root));
        listaMagazynowNaZasoby.get().set(1, new MagazynNaZasoby(2, listaZasobow.get(1), 2, 1, 5, root));
        /*for (int i = iloscZasobow.get(); i < iloscLiniiProdukcyjnych.get(); i++) {
            int wyborZasobu = los.nextInt(iloscZasobow.get());
            listaLiniiProdukcyjnych.get().set(i, new LiniaProdukcyjna(i+1, listaZasobow.get(wyborZasobu)));
            //listaLiniiProdukcyjnych.get().get(i).start();
        }*/
        //powiekszTablice(listaMagazynowNaZasoby, iloscMagazynowNaZasoby.get());
        /*
        for (int i = iloscZasobow.get(); i < iloscMagazynowNaZasoby.get(); i++) {
            scanner = new Scanner(System.in);
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
            listaMagazynowNaZasoby.get().set(i, new MagazynNaZasoby(id, listaZasobow.get(wyborZasobu), pk, il, poj));
            //listaMagazynowNaZasoby.get().get(i).start();
        }*/

        System.out.println("Linie produkcyjne");
        //LinieProdukcyjne
        //do {
            iloscLiniiProdukcyjnych.set(3);
        //} while (iloscLiniiProdukcyjnych.get() < iloscZasobow.get());
        powiekszTablice(listaLiniiProdukcyjnych, iloscLiniiProdukcyjnych.get());/*
        for (int i = 0; i < iloscZasobow.get(); i++)
        {
            listaLiniiProdukcyjnych.get().set(i, new LiniaProdukcyjna(i+1, listaZasobow.get(i),false,root));
        }
        for (int i = iloscZasobow.get(); i < iloscLiniiProdukcyjnych.get(); i++) {//             TU ZMIENIĆ
            int wyborZasobu = los.nextInt(iloscZasobow.get());
            listaLiniiProdukcyjnych.get().set(i, new LiniaProdukcyjna(i+1, listaZasobow.get(wyborZasobu),false,root));
            //listaLiniiProdukcyjnych.get().get(i).start();
        }*/
        listaLiniiProdukcyjnych.get().set(0, new LiniaProdukcyjna(1, listaZasobow.get(0),false,root));
        listaLiniiProdukcyjnych.get().set(1, new LiniaProdukcyjna(2, listaZasobow.get(1),false,root));
        listaLiniiProdukcyjnych.get().set(2, new LiniaProdukcyjna(3, listaZasobow.get(0),false,root));
        //MagazynyWyjsciowe
        System.out.println("Magazyn wyjściowy");
        HelloApplication.MW1=new MagazynWyjsciowy(1,1,0,7,10,root);
        System.out.println("Koniec losowania parametrów");
    }
}