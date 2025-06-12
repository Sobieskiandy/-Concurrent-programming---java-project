/*
Programowanie Współbieżne - Projekt
Autor: Piotr Lada
Grupa: WCY23IY1S1
Nr zadania projektowego: 5
Tytuł zadania projektowego: Zakład produkcyjny
Zadanie nr: PW-5
Język implementacji: Java
Środowisko implementacyjne: Eclipse, Intelij IDEA, Netbeans
Termin wykonania: ostatnie zajęcia
Podstawowe wymagania:
a. liczba procesów sekwencyjnych powinna być dobrana z wyczuciem tak, aby zachować czytelność interfejsu i
jednocześnie umożliwić zobrazowanie reprezentatywnych przykładów, Done
b. kod źródłowy programu musi być tak skonstruowany, aby można było „swobodnie” modyfikować liczbę
procesów sekwencyjnych (za wyjątkiem zadań o ściśle określonej liczbie procesów),
c. graficzne zobrazowanie działania procesów współbieżnych, Robię
d. odczyt domyślnych danych wejściowych ze sformatowanego, tekstowego pliku danych (xml, properties, inne),
e. możliwość modyfikacji danych wejściowych poprzez GUI.
Sprawozdanie (w formie elektronicznej) powinno zawierać następujące elementy:
1) stronę tytułową,
2) niniejszą treść zadania,
3) syntetyczny opis problemu – w tym wszystkie przyjęte założenia,
4) wykaz własnych procesów sekwencyjnych,
5) wykaz współdzielonych zasobów,
6) wykaz wyróżnionych punktów synchronizacji,
7) wykaz obiektów synchronizacji,
8) listing programu.
Problem do rozwiązania:
Zakład produkcyjny.
Założenia:
Zakład produkcyjny produkuje na kilku liniach wyroby tego samego typu korzystając z dwóch rodzajów surowców.
Surowce różnego rodzaju przechowywane są w oddzielnych magazynach, które
zaopatrywane są przez jeden samochód w sposób wyłączny.
Surowce danego rodzaju dowożone są po spadku zapasu w magazynie poniżej ustalonego poziomu.
Produkcja wyrobów na linii zajmuje losowy czas. Wyroby gotowe trafiają z linii do magazynu wyjściowego,
z którego co losowy czas są zabierane w formie paczek (ustalona liczba
sztuk).
 */
package org.example.pw_projekt_gui;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import java.util.List;
import java.util.ArrayList;
/*
DONE
 - podstawa(obiekty, menu do testów)
 - dodałem zmienne typu Atomic
 - poprawne przenoszenie długości tablicy oraz elementów listyliniiprodukcyjnych
 - częściowe dodanie współbieżności między obiektami(Alg. Peterson,_,_, Alg. Dekker)
  - idk czemu w Linii Produkcyjnej by dobrze zdefiniować samochód w sferze3 muszę dodać +1
  współbieżność między obiektami
  - Wczytywanie z plików
   - GUI
TODO
 - klasa Losuje, linijka 80 i MW1, przy losowaniu dodaj metodę która zatrzyma poprzednie.
 - Rozwiązać problem z plikami .gif
 */
public class HelloApplication extends Application {
    //metoda dostarczająca do MNZ kiedy zostanie wywołana. Jest ona sekcją krytyczną symbolizująca dostęp do zasobu jakim jest samochód dostawczy który przewozi pewną zmienną ilość określonych zasobów w określonej ilości do określonego MNZ.
    static Semaphore semaMNZ = new Semaphore(1);
    static Semaphore semaLP = new Semaphore(1);static Semaphore semaLP1 = new Semaphore(1);
    static Semaphore semaMW = new Semaphore(1);
    public static AtomicInteger iloscMagazynowNaZasoby=new AtomicInteger(0);
    public static AtomicInteger iloscMagazynowWyjsciowych=new AtomicInteger(0);
    public static MagazynWyjsciowy MW1;
    public static AtomicReference<Pane> root = new AtomicReference<>();
    public static AtomicInteger iloscZasobow=new AtomicInteger(0);
    public static AtomicInteger iloscLiniiProdukcyjnych = new AtomicInteger(0);
    public static AtomicInteger iloscSamochodow = new AtomicInteger(0);
    public static AtomicReference<AtomicReferenceArray<Integer>> listaSamochodowA = new AtomicReference<>(new AtomicReferenceArray<>(0));
    public static AtomicReference<AtomicReferenceArray<Integer>> listaSamochodowB = new AtomicReference<>(new AtomicReferenceArray<>(0));
    public static AtomicReference<AtomicReferenceArray<Integer>> listaSamochodowC = new AtomicReference<>(new AtomicReferenceArray<>(0));
    public static AtomicReference<AtomicReferenceArray<Integer>> listaSamochodowD = new AtomicReference<>(new AtomicReferenceArray<>(0));
    public static AtomicReferenceArray<String> listaZasobow= new AtomicReferenceArray<String>(iloscZasobow.get());//String[] listaZasobow=null;
    public static AtomicReference<AtomicReferenceArray<Samochod>> listaSamochodow = new AtomicReference<>(new AtomicReferenceArray<>(iloscSamochodow.get()));
    public static AtomicReference<AtomicReferenceArray<MagazynNaZasoby>> listaMagazynowNaZasoby = new AtomicReference<>(new AtomicReferenceArray<>(iloscMagazynowNaZasoby.get()));//AtomicReferenceArray<MagazynNaZasoby> listaMagazynowNaZasoby=new AtomicReferenceArray<MagazynNaZasoby>(iloscMagazynowNaZasoby.get());//MagazynNaZasoby[] listaMagazynowNaZasoby=null;
    public static AtomicReference<AtomicReferenceArray<MagazynWyjsciowy>> listaMagazynowWyjsciowych = new AtomicReference<>(new AtomicReferenceArray<>(iloscMagazynowWyjsciowych.get()));
    public static AtomicReference<AtomicReferenceArray<LiniaProdukcyjna>> listaLiniiProdukcyjnych = new AtomicReference<>(new AtomicReferenceArray<>(iloscLiniiProdukcyjnych.get()));//Thread[] listaLiniiProdukcyjnych=null;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        HelloController controller = fxmlLoader.getController();
        Pane pejnZFXML = controller.Pejn;

        Scene scene = new Scene(root, 1080, 800);
        stage.setTitle("Zakład produkcyjny");
        stage.setScene(scene);
        stage.show();

        HelloApplication.root.set(pejnZFXML);
        new Losuje().domyslne0(HelloApplication.iloscZasobow, HelloApplication.iloscLiniiProdukcyjnych, HelloApplication.iloscMagazynowNaZasoby, HelloApplication.iloscMagazynowWyjsciowych,HelloApplication.listaZasobow, HelloApplication.listaMagazynowNaZasoby, HelloApplication.listaLiniiProdukcyjnych, HelloApplication.listaMagazynowWyjsciowych, HelloApplication.root);
    }
    public static void main(String[] args)
    {
        launch();
    }
}