package org.example.pw_projekt_gui;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import javafx.stage.FileChooser;
import org.xml.sax.SAXException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.databind.JsonNode;

public class SettingsController {
    File Fxml;
    File Fproperties;
    File Fjson;
    int c=0;//ostateczny wybór podczas zapisywania zmian. 0-brak,1-default,2-random,3-xml,4-properties,5-json
    @FXML
    protected void onDefaultButtonClick() {
        c=1;
    }
    @FXML
    public void onRandomButtonClick(){
        c=2;
    }
    @FXML
    public void onXMLButtonClick() {
        c=3;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Pliki XML", "*.xml")
        );
        Fxml = fileChooser.showOpenDialog(HelloController.settingsStage);
    }
    @FXML
    public void onPropertiesButtonClick() {
        c=4;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Pliki Properties", "*.properties")
        );
        Fproperties = fileChooser.showOpenDialog(HelloController.settingsStage);
    }
    @FXML
    public void onJSONButtonClick() {
        c=5;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Pliki JSON", "*.json")
        );
        Fjson = fileChooser.showOpenDialog(HelloController.settingsStage);
    }
    @FXML
    public void onSaveButtonClick() {
        // Zamknięcie wszystkich executorów z listy
        /*for(int i=0; i<HelloApplication.iloscSamochodow.get();i++) {
            Samochod sa = HelloApplication.listaSamochodow.get().get(i);
            ImageView IVsa = sa.imageView;
            IVsa.setLayoutX(100*i); IVsa.setLayoutY(100*i);
        }*/
            for (ExecutorService exec : HelloController.executors) {
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
        if(c==1){//domyślnie
            new Losuje().domyslne(HelloApplication.iloscZasobow,HelloApplication.iloscLiniiProdukcyjnych,HelloApplication.iloscMagazynowNaZasoby,HelloApplication.listaZasobow,HelloApplication.listaMagazynowNaZasoby,HelloApplication.listaLiniiProdukcyjnych,HelloApplication.root);
        }
        if(c==2){//losuje
            new Losuje().losuj(HelloApplication.iloscZasobow, HelloApplication.iloscLiniiProdukcyjnych, HelloApplication.iloscMagazynowNaZasoby, HelloApplication.listaZasobow, HelloApplication.listaMagazynowNaZasoby, HelloApplication.listaLiniiProdukcyjnych,HelloApplication.root);
        }
        if(c==3){//xml poprzez DOM Parser
            if (Fxml != null) {
                File xmlFile = new File(Fxml.getAbsolutePath());
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = null;
                Document doc = null;
                try {
                    builder = factory.newDocumentBuilder();
                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                }
                try {
                    doc = builder.parse(xmlFile);
                } catch (SAXException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                doc.getDocumentElement().normalize();
                NodeList listaOsob = doc.getElementsByTagName("osoba");

                for (int i = 0; i < listaOsob.getLength(); i++) {
                    Node node = listaOsob.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element osoba = (Element) node;
                        String imie = osoba.getElementsByTagName("imie").item(0).getTextContent();
                        String wiek = osoba.getElementsByTagName("wiek").item(0).getTextContent();

                        System.out.println(imie + " ma " + wiek + " lat.");
                    }
                }
            }
        }
        if(c==4){//properties
            if (Fproperties != null) {
                //System.out.println(Fproperties.getAbsolutePath());
                try {
                    Properties props = new Properties();
                    FileInputStream fis = new FileInputStream(Fproperties);
                    props.load(fis);

                    for (int i = 1; i <= 2; i++) {
                        String imie = props.getProperty("osoba" + i + ".imie");
                        String wiek = props.getProperty("osoba" + i + ".wiek");

                        System.out.println(imie + " ma " + wiek + " lat.");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(c==5){//json
            if (Fjson != null) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root1 = null;
                try {
                    root1 = mapper.readTree(Fjson);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                for (JsonNode osoba : root1.get("osoby")) {
                    String imie = osoba.get("imie").asText();
                    int wiek = osoba.get("wiek").asInt();
                    System.out.println(imie + " ma " + wiek + " lat.");
                }
            }
        }
        c=0;
    }
    @FXML
    public void onExitButtonClick() {
        HelloController.settingsStage.close();
        HelloController.st=false;
    }
}