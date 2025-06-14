package org.example.pw_projekt_gui;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.xml.sax.SAXException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.File;
import java.net.URL;//sprawdza kiedy Slider zmieni wartość
import javafx.scene.control.Slider;//Slider
import javafx.scene.layout.HBox;//dodaje HBox
import javafx.scene.control.ComboBox;//dodaje ComboBox
import java.util.List;//dodaje listę
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceArray;
import com.fasterxml.jackson.databind.JsonNode;
public class SettingsController implements Initializable {
    File Fxml;
    File Fproperties;
    File Fjson;
    int c=0;//ostateczny wybór podczas zapisywania zmian. 0-brak,1-default,2-random,3-xml,4-properties,5-json
    int ZOut;
    int MOut;
    int LOut;
    int sOut;
    @FXML
    protected void onDefaultButtonClick() {
        c=1;
    }
    @FXML
    public void onRandomButtonClick(){
        c=2;
    }
    @FXML
    private Text ZasobyOut;
    @FXML
    private Slider ZasobySlider;
    private List<HBox> zasobyLista = new ArrayList<>();
    @FXML private HBox Z1; @FXML private HBox Z2; @FXML private HBox Z3; @FXML private HBox Z4; @FXML private HBox Z5; @FXML private HBox Z6;@FXML private HBox Z7; @FXML private HBox Z8;
    @FXML private ChoiceBox<String> Z1CB; @FXML private ChoiceBox<String> Z2CB; @FXML private ChoiceBox<String> Z3CB; @FXML private ChoiceBox<String> Z4CB; @FXML private ChoiceBox<String> Z5CB; @FXML private ChoiceBox<String> Z6CB;@FXML private ChoiceBox<String> Z7CB; @FXML private ChoiceBox<String> Z8CB;
    @FXML
    private Text MNZOut;
    @FXML
    private Slider MNZSlider;
    private List<HBox> MNZLista = new ArrayList<>();
    private List<String> MNZZLista = new ArrayList<>();
    @FXML private HBox M1; @FXML private HBox M2; @FXML private HBox M3; @FXML private HBox M4; @FXML private HBox M5; @FXML private HBox M6;@FXML private HBox M7; @FXML private HBox M8;
    @FXML private ComboBox M1CB;
    @FXML
    private Text LPOut;
    @FXML
    private Slider LPSlider;
    private List<HBox> LPLista = new ArrayList<>();
    @FXML private HBox L1; @FXML private HBox L2; @FXML private HBox L3; @FXML private HBox L4; @FXML private HBox L5; @FXML private HBox L6;@FXML private HBox L7; @FXML private HBox L8;
    @FXML
    private Text SOut;
    @FXML
    private Slider SSlider;
    private List<HBox> SLista = new ArrayList<>();
    @FXML private HBox S1; @FXML private HBox S2; @FXML private HBox S3; @FXML private HBox S4; @FXML private HBox S5; @FXML private HBox S6;@FXML private HBox S7; @FXML private HBox S8; @FXML private HBox S9; @FXML private HBox S10; @FXML private HBox S11;
    @FXML
    public void onXMLButtonClick(){
        c=3;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Pliki XML", "*.xml")
        );
        Fxml = fileChooser.showOpenDialog(HelloController.settingsStage);
    }
    @FXML
    public void onPropertiesButtonClick(){
        c=4;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Pliki Properties", "*.properties")
        );
        Fproperties = fileChooser.showOpenDialog(HelloController.settingsStage);
    }
    @FXML
    public void onJSONButtonClick(){
        c=5;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Pliki JSON", "*.json")
        );
        Fjson = fileChooser.showOpenDialog(HelloController.settingsStage);
    }
    @FXML
    public void onSaveButtonClick(){
        // Zamknięcie wszystkich executorów z listy
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
                Losuje.usun(HelloApplication.iloscZasobow,HelloApplication.iloscLiniiProdukcyjnych,HelloApplication.iloscMagazynowNaZasoby,HelloApplication.listaZasobow,HelloApplication.listaMagazynowNaZasoby,HelloApplication.listaLiniiProdukcyjnych,HelloApplication.root);
                /*
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
                */
                NodeList Dane = doc.getElementsByTagName("Dane");
                for (int i = 0; i < 1; i++) {
                    Node node = Dane.item(i);
                    int iZ=0;
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element Dane1 = (Element) node;
                        iZ = Integer.parseInt(Dane1.getElementsByTagName("IleZasobow").item(0).getTextContent());
                    }
                    HelloApplication.iloscZasobow.set(iZ);
                    HelloApplication.listaZasobow = new AtomicReferenceArray<>(HelloApplication.iloscZasobow.get());
                }
                for (int i = 0; i < HelloApplication.iloscZasobow.get(); i++) {
                    Node node = Dane.item(0);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element Dane1 = (Element) node;
                        int zasob = Integer.parseInt(Dane1.getElementsByTagName("Zasob").item(i).getTextContent());
                        HelloApplication.listaZasobow.set(i,Losuje.NazwyZasobow[zasob]);
                        int IleMNZ = Integer.parseInt(Dane1.getElementsByTagName("IleMNZ").item(0).getTextContent());HelloApplication.iloscMagazynowNaZasoby.set(IleMNZ);
                        int IleLP = Integer.parseInt(Dane1.getElementsByTagName("IleLP").item(0).getTextContent());HelloApplication.iloscLiniiProdukcyjnych.set(IleLP);
                        int IleMW = Integer.parseInt(Dane1.getElementsByTagName("IleMW").item(0).getTextContent());HelloApplication.iloscMagazynowWyjsciowych.set(IleMW);
                        int IleS = Integer.parseInt(Dane1.getElementsByTagName("IleS").item(0).getTextContent());HelloApplication.iloscSamochodow.set(IleS);
                    }
                }
                System.out.print("Ilość magazynów na zasoby= "+HelloApplication.iloscMagazynowNaZasoby+"\n");
                NodeList listaMNZ = doc.getElementsByTagName("MNZ");
                for (int i = 0; i < listaMNZ.getLength(); i++) {
                    Node node = listaMNZ.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element MNZ = (Element) node;
                        int id = Integer.parseInt(MNZ.getElementsByTagName("id").item(0).getTextContent());
                        int zasob = Integer.parseInt(MNZ.getElementsByTagName("zasob").item(0).getTextContent());
                        int pk = Integer.parseInt(MNZ.getElementsByTagName("pk").item(0).getTextContent());
                        int il = Integer.parseInt(MNZ.getElementsByTagName("il").item(0).getTextContent());
                        int poj = Integer.parseInt(MNZ.getElementsByTagName("poj").item(0).getTextContent());
                        System.out.println("XML:MNZ id:"+id+", zasob:"+zasob + ", pk:"+pk + ", il:"+il + ", poj:"+poj);
                        Losuje.zmienRozmiarTablicy(HelloApplication.listaMagazynowNaZasoby, HelloApplication.iloscMagazynowNaZasoby.get());
                        HelloApplication.listaMagazynowNaZasoby.get().set(id-1, new MagazynNaZasoby(id, Losuje.NazwyZasobow[zasob], pk, il, poj, HelloApplication.root.get()));
                    }
                }
                NodeList listaLP = doc.getElementsByTagName("LP");
                for (int i = 0; i < listaLP.getLength(); i++) {
                    Node node = listaLP.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element LP = (Element) node;
                        int id = Integer.parseInt(LP.getElementsByTagName("id").item(0).getTextContent());
                        int zasob = Integer.parseInt(LP.getElementsByTagName("zasob").item(0).getTextContent());
                        boolean pk = Boolean.parseBoolean(LP.getElementsByTagName("pk").item(0).getTextContent());
                        System.out.println("XML:LP id:"+id+", zasob:"+zasob + ", pk:"+pk);
                        Losuje.zmienRozmiarTablicy(HelloApplication.listaLiniiProdukcyjnych, HelloApplication.iloscLiniiProdukcyjnych.get());
                        HelloApplication.listaLiniiProdukcyjnych.get().set(id - 1, new LiniaProdukcyjna(id, Losuje.NazwyZasobow[zasob], pk, HelloApplication.root.get()));
                    }
                }
                NodeList listaMW = doc.getElementsByTagName("MW");
                for (int i = 0; i < listaMW.getLength(); i++) {
                    Node node = listaMW.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element MW = (Element) node;
                        int id = Integer.parseInt(MW.getElementsByTagName("id").item(0).getTextContent());
                        int il = Integer.parseInt(MW.getElementsByTagName("il").item(0).getTextContent());
                        int pk = Integer.parseInt(MW.getElementsByTagName("pk").item(0).getTextContent());
                        int poj = Integer.parseInt(MW.getElementsByTagName("poj").item(0).getTextContent());
                        System.out.println("XML:MW id:"+id+", il:"+il + ", pk:"+pk+ ", poj:"+poj);
                        HelloApplication.MW1=new MagazynWyjsciowy(id,il,pk,poj,HelloApplication.root.get());
                        Losuje.zmienRozmiarTablicy(HelloApplication.listaMagazynowWyjsciowych, HelloApplication.iloscMagazynowWyjsciowych.get());
                        HelloApplication.listaMagazynowWyjsciowych.get().set(0, HelloApplication.MW1);
                    }
                }
                NodeList listaS = doc.getElementsByTagName("S");
                for (int i = 0; i < listaS.getLength(); i++) {
                    Node node = listaS.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element S = (Element) node;
                        int id = Integer.parseInt(S.getElementsByTagName("id").item(0).getTextContent());
                        boolean prac = Boolean.parseBoolean(S.getElementsByTagName("prac").item(0).getTextContent());
                        int o = Integer.parseInt(S.getElementsByTagName("o").item(0).getTextContent());
                        int x; if(o==1)x=75;else if(o==2)x=155;else if(o==3)x=770;else if(o==4)x=860;else x=0;
                        int y; if(o==1||o==2)y=HelloApplication.listaMagazynowNaZasoby.get().get(0).y;else if(o==3||o==4)y=HelloApplication.listaMagazynowWyjsciowych.get().get(0).y;else y=0;
                        int il = Integer.parseInt(S.getElementsByTagName("il").item(0).getTextContent());
                        boolean pl = Boolean.parseBoolean(S.getElementsByTagName("pl").item(0).getTextContent());
                        System.out.println("JSON:S id:"+id+", prac:"+prac + ", o:"+o+ ", il:"+il+" pl:"+pl);
                        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get());
                        HelloApplication.listaSamochodow.get().set(id-1,new Samochod(id,prac,o,il,null,HelloApplication.root.get(),x,y,pl));
                        if(o==1){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowA,1);HelloApplication.listaSamochodowA.get().set(0,(1));}
                        if(o==2){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowB,1);HelloApplication.listaSamochodowB.get().set(0,(2));}
                        if(o==3){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowC,1);HelloApplication.listaSamochodowC.get().set(0,(3));}
                        if(o==4){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowD,1);HelloApplication.listaSamochodowD.get().set(0,(4));}
                    }
                }
            }
        }
        if(c==4){//properties
            if (Fproperties != null) {
                try {
                    Properties props = new Properties();
                    FileInputStream fis = new FileInputStream(Fproperties);
                    props.load(fis);
                    /*for (int i = 1; i <= 2; i++) {
                        String imie = props.getProperty("osoba" + i + ".imie");
                        String wiek = props.getProperty("osoba" + i + ".wiek");
                        System.out.println(imie + " ma " + wiek + " lat.");
                    }*/
                    Losuje.usun(HelloApplication.iloscZasobow,HelloApplication.iloscLiniiProdukcyjnych,HelloApplication.iloscMagazynowNaZasoby,HelloApplication.listaZasobow,HelloApplication.listaMagazynowNaZasoby,HelloApplication.listaLiniiProdukcyjnych,HelloApplication.root);
                    int iZ=0;
                    for (int i = 0; i <= 1; i++){
                        iZ=Integer.parseInt(props.getProperty("dane.IleZasobow"));
                    }
                    HelloApplication.iloscZasobow.set(iZ);
                    HelloApplication.listaZasobow = new AtomicReferenceArray<>(HelloApplication.iloscZasobow.get());
                    int z=0;
                    for (int i = 0; i < iZ; i++) {
                        int zasob = Integer.parseInt(props.getProperty("dane.Zasob"));
                        HelloApplication.listaZasobow.set(i,Losuje.NazwyZasobow[zasob]);
                        int IleMNZ = Integer.parseInt(props.getProperty("dane.IleMNZ"));HelloApplication.iloscMagazynowNaZasoby.set(IleMNZ);
                        int IleLP = Integer.parseInt(props.getProperty("dane.IleLP"));HelloApplication.iloscLiniiProdukcyjnych.set(IleLP);
                        int IleMW = Integer.parseInt(props.getProperty("dane.IleMW"));HelloApplication.iloscMagazynowWyjsciowych.set(IleMW);
                        int IleS = Integer.parseInt(props.getProperty("dane.IleS"));HelloApplication.iloscSamochodow.set(IleS);
                    }
                    System.out.print("Properties:Ilość magazynów na zasoby= "+HelloApplication.iloscMagazynowNaZasoby+"\n");
                    for (int i = 1; i <= HelloApplication.iloscMagazynowNaZasoby.get(); i++) {
                        int id = Integer.parseInt(props.getProperty("MNZ"+i+".id"));
                        int zasob = Integer.parseInt(props.getProperty("MNZ"+i+".zasob"));
                        int pk = Integer.parseInt(props.getProperty("MNZ"+i+".pk"));
                        int il = Integer.parseInt(props.getProperty("MNZ"+i+".il"));
                        int poj = Integer.parseInt(props.getProperty("MNZ"+i+".poj"));
                        System.out.println("Properties:MNZ id:"+id+", zasob:"+zasob + ", pk:"+pk + ", il:"+il + ", poj:"+poj);
                        Losuje.zmienRozmiarTablicy(HelloApplication.listaMagazynowNaZasoby, HelloApplication.iloscMagazynowNaZasoby.get());
                        HelloApplication.listaMagazynowNaZasoby.get().set(id-1, new MagazynNaZasoby(id, Losuje.NazwyZasobow[zasob], pk, il, poj, HelloApplication.root.get()));
                    }
                    for (int i = 1; i <= HelloApplication.iloscLiniiProdukcyjnych.get(); i++) {
                        int id = Integer.parseInt(props.getProperty("LP"+i+".id"));
                        int zasob = Integer.parseInt(props.getProperty("LP"+i+".zasob"));
                        boolean pk = Boolean.parseBoolean(props.getProperty("LP"+i+".pk"));
                        System.out.println("Properties:LP id:"+id+", zasob:"+zasob + ", pk:"+pk);
                        Losuje.zmienRozmiarTablicy(HelloApplication.listaLiniiProdukcyjnych, HelloApplication.iloscLiniiProdukcyjnych.get());
                        HelloApplication.listaLiniiProdukcyjnych.get().set(id - 1, new LiniaProdukcyjna(id, Losuje.NazwyZasobow[zasob], pk, HelloApplication.root.get()));
                    }
                    for (int i = 1; i <= HelloApplication.iloscMagazynowWyjsciowych.get(); i++) {
                        int id = Integer.parseInt(props.getProperty("MW"+i+".id"));
                        int il = Integer.parseInt(props.getProperty("MW"+i+".il"));
                        int pk = Integer.parseInt(props.getProperty("MW"+i+".pk"));
                        int poj = Integer.parseInt(props.getProperty("MW"+i+".poj"));
                        System.out.println("Properties:MW id:"+id+", il:"+il + ", pk:"+pk+ ", poj:"+poj);
                        HelloApplication.MW1=new MagazynWyjsciowy(id,il,pk,poj,HelloApplication.root.get());
                        Losuje.zmienRozmiarTablicy(HelloApplication.listaMagazynowWyjsciowych, HelloApplication.iloscMagazynowWyjsciowych.get());
                        HelloApplication.listaMagazynowWyjsciowych.get().set(0, HelloApplication.MW1);
                    }
                    for (int i = 1; i <= HelloApplication.iloscSamochodow.get(); i++) {
                        int id = Integer.parseInt(props.getProperty("S"+i+".id"));
                        boolean prac = Boolean.parseBoolean(props.getProperty("S"+i+".prac"));
                        int o = Integer.parseInt(props.getProperty("S"+i+".o"));
                        int x; if(o==1)x=75;else if(o==2)x=155;else if(o==3)x=770;else if(o==4)x=860;else x=0;
                        int y; if(o==1||o==2)y=HelloApplication.listaMagazynowNaZasoby.get().get(0).y;else if(o==3||o==4)y=HelloApplication.listaMagazynowWyjsciowych.get().get(0).y;else y=0;
                        int il = Integer.parseInt(props.getProperty("S"+i+".il"));
                        boolean pl = Boolean.parseBoolean(props.getProperty("S"+i+".pl"));
                        System.out.println("Properties:S id:"+id+", prac:"+prac + ", o:"+o+ ", il:"+il+" pl:"+pl);
                        Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get());
                        HelloApplication.listaSamochodow.get().set(id-1,new Samochod(id,prac,o,il,null,HelloApplication.root.get(),x,y,pl));
                        if(o==1){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowA,1);HelloApplication.listaSamochodowA.get().set(0,(1));}
                        if(o==2){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowB,1);HelloApplication.listaSamochodowB.get().set(0,(2));}
                        if(o==3){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowC,1);HelloApplication.listaSamochodowC.get().set(0,(3));}
                        if(o==4){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowD,1);HelloApplication.listaSamochodowD.get().set(0,(4));}
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
                Losuje.usun(HelloApplication.iloscZasobow,HelloApplication.iloscLiniiProdukcyjnych,HelloApplication.iloscMagazynowNaZasoby,HelloApplication.listaZasobow,HelloApplication.listaMagazynowNaZasoby,HelloApplication.listaLiniiProdukcyjnych,HelloApplication.root);
                int iZ=0;
                for (JsonNode Dane : root1.get("Dane")) {
                    iZ = Dane.get("IleZasobow").asInt();
                }
                HelloApplication.iloscZasobow.set(iZ);
                HelloApplication.listaZasobow = new AtomicReferenceArray<>(HelloApplication.iloscZasobow.get());
                /*
                for(int i=0;i<HelloApplication.iloscZasobow.get();i++) {
                    HelloApplication.listaZasobow.set(i,Losuje.NazwyZasobow[i]);
                }*/
                int z=0;
                for (JsonNode Z : root1.get("Dane")) {
                    int zasob = Z.get("Zasob").asInt();//6,3,...
                    System.out.println("JSON:Dane: zasob:"+zasob);
                    HelloApplication.listaZasobow.set(z,Losuje.NazwyZasobow[zasob]);
                    int IleMNZ = Z.get("IleMNZ").asInt();HelloApplication.iloscMagazynowNaZasoby.set(IleMNZ);
                    int IleLP = Z.get("IleLP").asInt();HelloApplication.iloscLiniiProdukcyjnych.set(IleLP);
                    int IleMW = Z.get("IleMW").asInt();HelloApplication.iloscMagazynowWyjsciowych.set(IleMW);
                    int IleS = Z.get("IleS").asInt();HelloApplication.iloscSamochodow.set(IleS);
                }
                System.out.print("Ilość magazynów na zasoby= "+HelloApplication.iloscMagazynowNaZasoby+"\n");
                for (JsonNode MNZ : root1.get("MNZ")) {
                    int id = MNZ.get("id").asInt();//1,2,3,...
                    int zasob = MNZ.get("zasob").asInt();//0,1,2,..9,10
                    int pk = MNZ.get("pk").asInt();
                    int il = MNZ.get("il").asInt();
                    int poj = MNZ.get("poj").asInt();
                    System.out.println("JSON:MNZ id:"+id+", zasob:"+zasob + ", pk:"+pk + ", il:"+il + ", poj:"+poj);
                    Losuje.zmienRozmiarTablicy(HelloApplication.listaMagazynowNaZasoby, HelloApplication.iloscMagazynowNaZasoby.get());
                    HelloApplication.listaMagazynowNaZasoby.get().set(id-1, new MagazynNaZasoby(id, Losuje.NazwyZasobow[zasob], pk, il, poj, HelloApplication.root.get()));
                }
                for (JsonNode LP : root1.get("LP")) {
                    int id = LP.get("id").asInt();
                    int zasob = LP.get("zasob").asInt();
                    boolean pk = LP.get("pk").asBoolean();
                    System.out.println("JSON:LP id:" + id + ", zasob:" + zasob + ", pk:" + pk);
                    Losuje.zmienRozmiarTablicy(HelloApplication.listaLiniiProdukcyjnych, HelloApplication.iloscLiniiProdukcyjnych.get());
                    HelloApplication.listaLiniiProdukcyjnych.get().set(id - 1, new LiniaProdukcyjna(id, Losuje.NazwyZasobow[zasob], pk, HelloApplication.root.get()));
                    }
                for (JsonNode MW : root1.get("MW")) {
                    int id = MW.get("id").asInt();
                    int il = MW.get("il").asInt();
                    int pk = MW.get("pk").asInt();
                    int poj = MW.get("poj").asInt();
                    System.out.println("JSON:MW id:"+id+", il:"+il + ", pk:"+pk+ ", poj:"+poj);
                    HelloApplication.MW1=new MagazynWyjsciowy(id,il,pk,poj,HelloApplication.root.get());
                    Losuje.zmienRozmiarTablicy(HelloApplication.listaMagazynowWyjsciowych, HelloApplication.iloscMagazynowWyjsciowych.get());
                    HelloApplication.listaMagazynowWyjsciowych.get().set(0, HelloApplication.MW1);
                }
                for (JsonNode S : root1.get("S")) {
                    int id = S.get("id").asInt();
                    boolean prac = S.get("prac").asBoolean();
                    int o = S.get("o").asInt();//1,2,3,4
                    int x; if(o==1)x=75;else if(o==2)x=155;else if(o==3)x=770;else if(o==4)x=860;else x=0;
                    int y; if(o==1||o==2)y=HelloApplication.listaMagazynowNaZasoby.get().get(0).y;else if(o==3||o==4)y=HelloApplication.listaMagazynowWyjsciowych.get().get(0).y;else y=0;
                    int il = S.get("il").asInt();
                    boolean pl = S.get("pl").asBoolean();
                    System.out.println("JSON:S id:"+id+", prac:"+prac + ", o:"+o+ ", il:"+il+" pl:"+pl);
                    Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodow, HelloApplication.iloscSamochodow.get());
                    HelloApplication.listaSamochodow.get().set(id-1,new Samochod(id,prac,o,il,null,HelloApplication.root.get(),x,y,pl));
                    if(o==1){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowA,1);HelloApplication.listaSamochodowA.get().set(0,(1));}
                    if(o==2){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowB,1);HelloApplication.listaSamochodowB.get().set(0,(2));}
                    if(o==3){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowC,1);HelloApplication.listaSamochodowC.get().set(0,(3));}
                    if(o==4){Losuje.zmienRozmiarTablicy(HelloApplication.listaSamochodowD,1);HelloApplication.listaSamochodowD.get().set(0,(4));}
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
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        zasobyLista.add(Z1);zasobyLista.add(Z2);zasobyLista.add(Z3);zasobyLista.add(Z4);
        zasobyLista.add(Z5);zasobyLista.add(Z6);zasobyLista.add(Z7);zasobyLista.add(Z8);
        MNZLista.add(M1);MNZLista.add(M2);MNZLista.add(M3);MNZLista.add(M4);
        MNZLista.add(M5);MNZLista.add(M6);MNZLista.add(M7);MNZLista.add(M8);
        LPLista.add(L1);LPLista.add(L2);LPLista.add(L3);LPLista.add(L4);
        LPLista.add(L5);LPLista.add(L6);LPLista.add(L7);LPLista.add(L8);
        SLista.add(S1);SLista.add(S2);SLista.add(S3);SLista.add(S4);SLista.add(S5);SLista.add(S6);
        SLista.add(S7);SLista.add(S8);SLista.add(S9);SLista.add(S10);SLista.add(S11);
        Z1CB.getItems().addAll(Losuje.NazwyZasobow);Z2CB.getItems().addAll(Losuje.NazwyZasobow);Z3CB.getItems().addAll(Losuje.NazwyZasobow);Z4CB.getItems().addAll(Losuje.NazwyZasobow);
        Z5CB.getItems().addAll(Losuje.NazwyZasobow);Z6CB.getItems().addAll(Losuje.NazwyZasobow);Z7CB.getItems().addAll(Losuje.NazwyZasobow);Z8CB.getItems().addAll(Losuje.NazwyZasobow);

        ZasobySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                ZOut=(int)ZasobySlider.getValue();
                ZasobyOut.setText(Integer.toString(ZOut));
                for(int i=0;i<8;i++)
                {
                    zasobyLista.get(i).setOpacity(1);
                    zasobyLista.get(i).setDisable(false);
                }
                for(int i=8;i>ZOut;i--)
                {
                    zasobyLista.get(i-1).setOpacity(0.5);
                    zasobyLista.get(i-1).setDisable(true);
                }
            }
        });
        MNZSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                MOut=(int)MNZSlider.getValue();
                MNZOut.setText(Integer.toString(MOut));
                for(int i=0;i<8;i++)
                {
                    MNZLista.get(i).setOpacity(1);
                    MNZLista.get(i).setDisable(false);
                }
                for(int i=8;i>MOut;i--)
                {
                    MNZLista.get(i-1).setOpacity(0.5);
                    MNZLista.get(i-1).setDisable(true);
                }
            }
        });
        LPSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                LOut=(int)LPSlider.getValue();
                LPOut.setText(Integer.toString(LOut));
                for(int i=0;i<8;i++)
                {
                    LPLista.get(i).setOpacity(1);
                    LPLista.get(i).setDisable(false);
                }
                for(int i=8;i>LOut;i--)
                {
                    LPLista.get(i-1).setOpacity(0.5);
                    LPLista.get(i-1).setDisable(true);
                }
            }
        });
        SSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                sOut=(int)SSlider.getValue();
                SOut.setText(Integer.toString(sOut));
                for(int i=0;i<11;i++)
                {
                    SLista.get(i).setOpacity(1);
                    SLista.get(i).setDisable(false);
                }
                for(int i=11;i>sOut;i--)
                {
                    SLista.get(i-1).setOpacity(0.5);
                    SLista.get(i-1).setDisable(true);
                }
            }
        });

        Z1CB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(Z1CB.getValue()!=null){
                    M1CB.getItems().add(Z1CB.getValue());
                }
            }
        });
    }
}