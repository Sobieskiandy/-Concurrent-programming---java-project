package org.example.pw_projekt_gui;
import javafx.stage.Modality;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
public class HelloController {
    @FXML
    private Pane Pejn;
    @FXML
    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {/*
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
    public void onSettingsButtonClick() {/*
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
    public void onStopButtonClick() {
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
}