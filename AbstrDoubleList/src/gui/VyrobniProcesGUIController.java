package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ResourceBundle;

import abstrlifo.IAbstrLifo;
import enums.EPozice;
import enums.EProces;
import enums.EReorg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import procesy.Proces;
import procesy.ProcesManualni;
import procesy.ProcesRoboticky;
import procesy.VyrobniProces;
import procesy.VyrobniProcesException;

public class VyrobniProcesGUIController {

    private VyrobniProces vyrobniProces;
    private IAbstrLifo<Proces> kandidati;

    private int maxIdManual;
    private int maxIdRobot;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnExport;

    @FXML
    private Button btnGeneruj;

    @FXML
    private Button btnImport;

    @FXML
    private Button btnOdeber;

    @FXML
    private Button btnReorg;

    @FXML
    private Button btnVloz;

    @FXML
    private Button btnVyber;

    @FXML
    private Button btnVymaz;

    @FXML
    private Button btnVytipuj;

    @FXML
    private Button btnZobraz;

    @FXML
    private ComboBox<EPozice> cbOdeber;

    @FXML
    private ComboBox<EReorg> cbReorg;

    @FXML
    private ComboBox<EProces> cbTypProcesu;

    @FXML
    private ComboBox<EPozice> cbVloz;

    @FXML
    private ComboBox<EPozice> cbVyber;

    @FXML
    private ComboBox<EReorg> cbVytipuj;

    @FXML
    private TextField fieldCasProcesu;

    @FXML
    private TextField fieldKriterium;

    @FXML
    private TextField fieldPocetOsob;

    @FXML
    private ListView<String> listProcesy;

    @FXML
    private Slider sldGeneruj;

    @FXML
    void exportuj(ActionEvent event) {

    }

    @FXML
    void generuj(ActionEvent event) {
        try {
            int pocet = (int) sldGeneruj.getValue();
            this.vyrobniProces.generatorDat(pocet);

            zobrazitDataVListView();
        } catch (IllegalArgumentException e) {
            //sem se uzivatel nedostane, GUI neumoznuje vyber neceho jineho nez cisla v rozmezi 1-50
        }
    }

    @FXML
    void importuj(ActionEvent event) {

    }

    @FXML
    void odeberProces(ActionEvent event) {
        try {
            switch(this.cbOdeber.getSelectionModel().getSelectedItem()) {
                case AKTUALNI -> this.vyrobniProces.odeberProces(EPozice.AKTUALNI);
                case NASLEDNIK -> this.vyrobniProces.odeberProces(EPozice.NASLEDNIK);
                case POSLEDNI -> this.vyrobniProces.odeberProces(EPozice.POSLEDNI);
                case PREDCHUDCE -> this.vyrobniProces.odeberProces(EPozice.PREDCHUDCE);
                case PRVNI -> this.vyrobniProces.odeberProces(EPozice.PRVNI);
            }

            zobrazitDataVListView();
        } catch (VyrobniProcesException e) {
            errorDialog(AlertType.ERROR, "Chyba", e.getMessage());
        }
    }

    @FXML
    void reorganizujProcesy(ActionEvent event) {
        try {
            switch (this.cbReorg.getSelectionModel().getSelectedItem()) {
                case AGREGACE -> vyrobniProces.reorganizace(EReorg.AGREGACE, this.kandidati);
                case DEKOMPOZICE -> vyrobniProces.reorganizace(EReorg.DEKOMPOZICE, this.kandidati);
            }

            zobrazitDataVListView();
        } catch (VyrobniProcesException e) {
            errorDialog(AlertType.ERROR, "Chyba", e.getMessage());
        }
    }

    @FXML
    void vlozProces(ActionEvent event) {
        try {
            if (this.fieldPocetOsob.getText().isEmpty() || this.fieldCasProcesu.getText().isEmpty()) {
                throw new NumberFormatException();
            }

            Iterator<Proces> it = this.vyrobniProces.iterator();
            ArrayList<String> al = new ArrayList<>();
            
            boolean obsahujeRoboticky = false;
            boolean obsahujeManualni = false;

            while (it.hasNext()) {
                String id = it.next().getId();

                if (!obsahujeRoboticky) obsahujeRoboticky = id.substring(0, 1) == "R";
                if (!obsahujeManualni) obsahujeManualni = id.substring(0, 1) == "O";

                al.add(id);
            }

            if (obsahujeManualni) {
                this.maxIdManual = al.stream()
                    .filter(p -> p.substring(0, 1) == "O")
                    .map(p -> Integer.parseInt(p.substring(1)))
                    .sorted(Comparator.reverseOrder())
                    .findFirst()
                    .get();
            }

            if (obsahujeRoboticky) {
                this.maxIdRobot = al.stream()
                    .filter(p -> p.substring(0, 1) == "R")
                    .map(p -> Integer.parseInt(p.substring(1)))
                    .sorted(Comparator.reverseOrder())
                    .findFirst()
                    .get();
            }

            int pocetOsob = Integer.parseInt(this.fieldPocetOsob.getText());
            int casProcesu = Integer.parseInt(this.fieldPocetOsob.getText());

            Proces proces = null;

            switch (this.cbTypProcesu.getSelectionModel().getSelectedItem()) {
                case MANUALNI -> {
                    ++this.maxIdManual;
                    proces = new ProcesManualni("O" + this.maxIdManual, pocetOsob, casProcesu);
                }
                case ROBOTICKY -> {
                    ++this.maxIdRobot;
                    proces = new ProcesRoboticky("R" + this.maxIdRobot, casProcesu);
                }
            }

            switch (this.cbVloz.getSelectionModel().getSelectedItem()) {
                case NASLEDNIK -> vyrobniProces.vlozProces(proces, EPozice.NASLEDNIK);
                case POSLEDNI -> vyrobniProces.vlozProces(proces, EPozice.POSLEDNI);
                case PREDCHUDCE -> vyrobniProces.vlozProces(proces, EPozice.PREDCHUDCE);
                case PRVNI -> vyrobniProces.vlozProces(proces, EPozice.PRVNI);
                case AKTUALNI -> {} //sem se uzivatel nedostane, nema moznost aktualni v GUI na vyber
            }

            zobrazitDataVListView();
        } catch (NumberFormatException e) {
            errorDialog(AlertType.ERROR, "Chyba", "Vstupni data ve spatnem formatu, zadejte pouze cisla");
        } catch (VyrobniProcesException e) {
            errorDialog(AlertType.ERROR, "Chyba", e.getMessage());
        }
    }

    @FXML
    void vyberProces(ActionEvent event) {
        try {
            switch(this.cbVyber.getSelectionModel().getSelectedItem()) {
                case NASLEDNIK -> this.vyrobniProces.zpristupniProces(EPozice.NASLEDNIK);
                case POSLEDNI -> this.vyrobniProces.zpristupniProces(EPozice.POSLEDNI);
                case PREDCHUDCE -> this.vyrobniProces.zpristupniProces(EPozice.PREDCHUDCE);
                case PRVNI -> this.vyrobniProces.zpristupniProces(EPozice.PRVNI);
                case AKTUALNI -> {} //sem se uzivatel nedostane, nema moznost aktualni v GUI na vyber
            }

            zobrazitDataVListView();
        } catch (VyrobniProcesException e) {
            errorDialog(AlertType.ERROR, "Chyba", e.getMessage());
        }
    }

    @FXML
    void vymazProcesy(ActionEvent event) {
        this.vyrobniProces.zrus();
        zobrazitDataVListView();
    }

    @FXML
    void vytipujKandidaty(ActionEvent event) {
        try {
            if (this.fieldKriterium.getText().isEmpty()) {
                throw new NumberFormatException();
            }

            switch (this.cbVytipuj.getSelectionModel().getSelectedItem()) {
                case AGREGACE -> this.kandidati = vyrobniProces.vytipujKandidatiReorg(Integer.parseInt(this.fieldKriterium.getText()), EReorg.AGREGACE);
                case DEKOMPOZICE -> this.kandidati = vyrobniProces.vytipujKandidatiReorg(Integer.parseInt(this.fieldKriterium.getText()), EReorg.DEKOMPOZICE);
            }

            zobrazitDataVListView();
        } catch (NumberFormatException e) {
            errorDialog(AlertType.ERROR, "Chyba", "Vstupni data ve spatnem formatu, zadejte pouze cisla");
        } catch (VyrobniProcesException e) {
            errorDialog(AlertType.ERROR, "Chyba", e.getMessage());
        }
    }

    @FXML
    void zobrazKandidaty(ActionEvent event) {

    }

    private void zobrazitDataVListView() {
        listProcesy.getItems().clear();

        Iterator<Proces> iterator = this.vyrobniProces.iterator();
        while (iterator.hasNext()) {
            Proces p = iterator.next();

            String strProces = "";
            if (p instanceof ProcesManualni pm) strProces = pm.toString();
            else if (p instanceof ProcesRoboticky pr) strProces = pr.toString();

            try {
                if (this.vyrobniProces.zpristupniProces(EPozice.AKTUALNI) == p) {
                    strProces = "=>   " + strProces;
                } else {
                    strProces = "    " + strProces;
                }
            } catch (VyrobniProcesException e) {
                errorDialog(AlertType.ERROR, "Chyba", e.getMessage());
            }

            listProcesy.getItems().add(strProces);
        }
    }

    private void errorDialog(AlertType typ, String title, String contentText) {
        Alert alert = new Alert(typ);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    @FXML
    void initialize() {
        this.vyrobniProces = new VyrobniProces();
        this.kandidati = null;
        this.maxIdManual = 100;
        this.maxIdRobot = 100;

        this.cbReorg.getItems().setAll(EReorg.values());
        this.cbTypProcesu.getItems().setAll(EProces.values());
        this.cbVytipuj.getItems().setAll(EReorg.values());
        this.cbOdeber.getItems().setAll(EPozice.values());
        this.cbVyber.getItems().setAll(EPozice.PRVNI, EPozice.POSLEDNI, EPozice.NASLEDNIK, EPozice.PREDCHUDCE);
        this.cbVloz.getItems().setAll(EPozice.PRVNI, EPozice.POSLEDNI, EPozice.NASLEDNIK, EPozice.PREDCHUDCE);

        this.cbReorg.getSelectionModel().selectFirst();
        this.cbTypProcesu.getSelectionModel().selectFirst();
        this.cbVytipuj.getSelectionModel().selectFirst();
        this.cbOdeber.getSelectionModel().selectFirst();
        this.cbVyber.getSelectionModel().selectFirst();
        this.cbVloz.getSelectionModel().selectFirst();
    }

}
