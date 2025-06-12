package org.example.pw_projekt_gui;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
public class HelloController {
    int s=0;
    static boolean st=false;//zmienna niepozwala na zamknięcie programu lub wejście do ustawień podczas działania symulacji
    public static List<ExecutorService> executors = new ArrayList<>();
    public static Pane settingsRoot;
    public static Stage settingsStage;
    @FXML
    public Pane Pejn;
    @FXML
    public void onStartButtonClick(){
        st=true;
        if (executors.stream().anyMatch(exec -> !exec.isShutdown())) {
            return; // Już działa coś — nie uruchamiaj ponownie
        }
        // Linie Produkcyjne
        ExecutorService ex1 = Executors.newFixedThreadPool(HelloApplication.iloscLiniiProdukcyjnych.get());
        executors.add(ex1);
        AtomicReferenceArray<LiniaProdukcyjna> linie = HelloApplication.listaLiniiProdukcyjnych.get();
        for (int i = 0; i < HelloApplication.iloscLiniiProdukcyjnych.get(); i++) {
            LiniaProdukcyjna lp = linie.get(i);
            ex1.submit(lp);
        }
        // Magazyny na zasoby
        ExecutorService ex2 = Executors.newFixedThreadPool(HelloApplication.iloscMagazynowNaZasoby.get());
        executors.add(ex2);
        AtomicReferenceArray<MagazynNaZasoby> linie1 = HelloApplication.listaMagazynowNaZasoby.get();
        for (int i = 0; i < HelloApplication.iloscMagazynowNaZasoby.get(); i++) {
            MagazynNaZasoby mnz = linie1.get(i);
            ex2.submit(mnz);
        }
        // Magazyny wyjściowe
        ExecutorService ex3 = Executors.newFixedThreadPool(HelloApplication.iloscMagazynowWyjsciowych.get());
        executors.add(ex3);
        AtomicReferenceArray<MagazynWyjsciowy> linie2 = HelloApplication.listaMagazynowWyjsciowych.get();
        for (int i = 0; i < HelloApplication.iloscMagazynowWyjsciowych.get(); i++) {
            MagazynWyjsciowy mw = linie2.get(i);
            ex3.submit(mw);
        }
        // Samochody
        ExecutorService ex4 = Executors.newFixedThreadPool(HelloApplication.iloscSamochodow.get());
        executors.add(ex4);
        AtomicReferenceArray<Samochod> linie3 = HelloApplication.listaSamochodow.get();
        for (int i = 0; i < HelloApplication.iloscSamochodow.get(); i++) {
            Samochod s1 = linie3.get(i);
            ex4.submit(s1);
        }
        s++; // tylko jeden raz
        /*
        welcomeText.setText("Welcome to JavaFX Application!");
        //MagazynyNaZasoby
        for (int i = 0; i < HelloApplication.iloscLiniiProdukcyjnych.get(); i++) {
            LiniaProdukcyjna lp = HelloApplication.listaLiniiProdukcyjnych.get().get(i);
            if (lp != null) {
                lp.start();
            }
        }
        for (int i = 0; i < HelloApplication.iloscMagazynowNaZasoby.get(); i++) {
            MagazynNaZasoby lmnz = HelloApplication.listaMagazynowNaZasoby.get().get(i);
            if (lmnz != null) {
                lmnz.start();
            }
        }
        HelloApplication.MW1.start();
        for (int i = 0; i < HelloApplication.iloscSamochodow.get(); i++) {
            Samochod sa = HelloApplication.listaSamochodow.get().get(i);
            if (sa != null) {
                sa.start();
            }
        }*/
    }
    @FXML
    public void onStopButtonClick(){
        if(settingsStage == null){//w settingsStage != null jest to samo co tu
            if (st == true) {
                // Zatrzymanie wszystkich obiektów
                for (int i = 0; i < HelloApplication.iloscSamochodow.get(); i++) {
                    Samochod sa = HelloApplication.listaSamochodow.get().get(i);
                    if (sa != null) {
                        sa.stop();
                    }
                }

                for (int i = 0; i < HelloApplication.iloscLiniiProdukcyjnych.get(); i++) {
                    LiniaProdukcyjna lp = HelloApplication.listaLiniiProdukcyjnych.get().get(i);
                    if (lp != null) {
                        lp.stop();
                    }
                }

                for (int i = 0; i < HelloApplication.iloscMagazynowNaZasoby.get(); i++) {
                    MagazynNaZasoby mnz = HelloApplication.listaMagazynowNaZasoby.get().get(i);
                    if (mnz != null) {
                        mnz.stop();
                    }
                }

                for (int i = 0; i < HelloApplication.iloscMagazynowWyjsciowych.get(); i++) {
                    MagazynWyjsciowy mw = HelloApplication.listaMagazynowWyjsciowych.get().get(i);
                    if (mw != null) {
                        mw.stop();
                    }
                }
                /*
                // Zamknięcie wszystkich executorów z listy
                for (ExecutorService exec : executors) {
                    exec.shutdownNow();
                    try {
                        if (!exec.awaitTermination(1, TimeUnit.SECONDS)) {
                            System.out.println("Wątki nie zakończyły się na czas");
                        }
                    } catch (InterruptedException e) {
                        exec.shutdownNow();
                        Thread.currentThread().interrupt();
                    }
                }

                // Można wyczyścić listę, jeśli chcesz uruchomić później jeszcze raz
                executors.clear();*/
                st = false;
            }
        }else {
            if (st == true && (!settingsStage.isShowing())) {
                // Zatrzymanie wszystkich obiektów
                for (int i = 0; i < HelloApplication.iloscSamochodow.get(); i++) {
                    Samochod sa = HelloApplication.listaSamochodow.get().get(i);
                    if (sa != null) {
                        sa.stop();
                    }
                }

                for (int i = 0; i < HelloApplication.iloscLiniiProdukcyjnych.get(); i++) {
                    LiniaProdukcyjna lp = HelloApplication.listaLiniiProdukcyjnych.get().get(i);
                    if (lp != null) {
                        lp.stop();
                    }
                }

                for (int i = 0; i < HelloApplication.iloscMagazynowNaZasoby.get(); i++) {
                    MagazynNaZasoby mnz = HelloApplication.listaMagazynowNaZasoby.get().get(i);
                    if (mnz != null) {
                        mnz.stop();
                    }
                }

                for (int i = 0; i < HelloApplication.iloscMagazynowWyjsciowych.get(); i++) {
                    MagazynWyjsciowy mw = HelloApplication.listaMagazynowWyjsciowych.get().get(i);
                    if (mw != null) {
                        mw.stop();
                    }
                }
                st = false;
            }
        }
        /*
        try {
            welcomeText.setText("Welcome to Settings!");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings-view.fxml"));
            Parent root = fxmlLoader.load();
            HelloApplication.settingsStage = new Stage();
            HelloApplication.settingsStage .setTitle("Ustawienia");
            HelloApplication.settingsStage .setScene(new Scene(root, 640, 480));
            HelloApplication.settingsStage .initModality(Modality.APPLICATION_MODAL);

            // Możesz też ustawić właściciela, jeśli chcesz (opcjonalnie):
            // settingsStage.initOwner(welcomeText.getScene().getWindow());

            HelloApplication.settingsStage .showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    @FXML
    public void onSettingsButtonClick(){
        if(st==false)
        {
            st=true;
            FXMLLoader fxmlSettingsLoader = new FXMLLoader(HelloApplication.class.getResource("settings-view.fxml"));
            try{
                settingsRoot = fxmlSettingsLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene1 = new Scene(settingsRoot, 900, 320);
            settingsStage = new Stage();
            settingsStage.setTitle("Zakład produkcyjny");
            settingsStage.setScene(scene1);
            settingsStage.show();
        }
        /*
        for (int i = 0; i < HelloApplication.iloscSamochodow.get(); i++) {
            Samochod sa = HelloApplication.listaSamochodow.get().get(i);
            if (sa != null) {
                sa.interrupt();
                try{
                    sa.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (int i = 0; i < HelloApplication.iloscLiniiProdukcyjnych.get(); i++) {
            LiniaProdukcyjna lp = HelloApplication.listaLiniiProdukcyjnych.get().get(i);
            if (lp != null) {
                lp.interrupt();
                try{
                    lp.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (int i = 0; i < HelloApplication.iloscMagazynowNaZasoby.get(); i++) {
            MagazynNaZasoby lp = HelloApplication.listaMagazynowNaZasoby.get().get(i);
            if (lp != null) {
                lp.interrupt();
                try{
                    lp.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        HelloApplication.MW1.interrupt();
        try{
            HelloApplication.MW1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
    @FXML
    public void onExitButtonClick(){
        if(st==false)
        {
            // Zamknięcie wszystkich executorów z listy
            for (ExecutorService exec : executors) {
                exec.shutdownNow();
                try {
                    if (!exec.awaitTermination(1, TimeUnit.SECONDS)) {
                        System.out.println("Wątki nie zakończyły się na czas");
                    }
                } catch (InterruptedException e) {
                    exec.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
            // Można wyczyścić listę, jeśli chcesz uruchomić później jeszcze raz
            executors.clear();
            for (ExecutorService exec : executors) {
                exec.shutdownNow();
                try {
                    exec.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.exit();
        }
    }
}