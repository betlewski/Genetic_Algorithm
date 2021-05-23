package sample.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.ga.FunctionType;
import sample.ga.GeneticAlgorithm;

public class Controller {

    @FXML
    private Button startButton;
    @FXML
    private Button openResultsButton;
    @FXML
    private ComboBox<FunctionType> functionComboBox;
    @FXML
    private Label populationLabel;
    @FXML
    private Slider populationSlider;
    @FXML
    private Label generationLabel;
    @FXML
    private Slider generationSlider;
    @FXML
    private Label crossoverLabel;
    @FXML
    private Slider crossoverSlider;
    @FXML
    private Label mutationLabel;
    @FXML
    private Slider mutationSlider;
    @FXML
    private TextArea resultsArea;

    private GeneticAlgorithm ga = null;
    private static FunctionType functionType = FunctionType.ACKLEYS;
    private static int populationSize = 10;
    private static int generationNumber = 10;
    private static double crossoverRate = GeneticAlgorithm.DEFAULT_CROSSOVER_RATE;
    private static double mutationRate = GeneticAlgorithm.DEFAULT_MUTATION_RATE;
    private static final double PRECISION = 100.0;

    public void initialize() {
        setButtonListeners();
        initFunctionComboBox();
        setSlidersListeners();
    }

    private void setButtonListeners() {
        startButton.setOnAction(event -> {
            resultsArea.setText(functionType.getDescription() + "\n");
            ga = new GeneticAlgorithm(functionType, populationSize, generationNumber, crossoverRate, mutationRate);
            String results = ga.runAlgorithm();
            resultsArea.appendText(results);
            openResultsButton.setDisable(false);
        });
    }

    private void initFunctionComboBox() {
        FunctionType[] functionTypes = FunctionType.values();
        functionComboBox.setItems(FXCollections.observableArrayList(functionTypes));
        functionComboBox.setValue(FunctionType.ACKLEYS);
        functionComboBox.valueProperty().addListener(
                (observable, oldValue, actualValue) -> functionType = actualValue);
    }

    private void setSlidersListeners() {
        populationSlider.valueProperty().addListener((observable, oldValue, actualValue) -> {
            int value = actualValue.intValue();
            populationSize = value;
            populationLabel.setText(String.valueOf(value));
        });
        generationSlider.valueProperty().addListener((observable, oldValue, actualValue) -> {
            int value = actualValue.intValue();
            generationNumber = value;
            generationLabel.setText(String.valueOf(value));
        });
        crossoverSlider.valueProperty().addListener((observable, oldValue, actualValue) -> {
            double value = Math.round(actualValue.doubleValue() * PRECISION) / PRECISION;
            crossoverRate = value;
            crossoverLabel.setText(String.valueOf(value));
        });
        mutationSlider.valueProperty().addListener((observable, oldValue, actualValue) -> {
            double value = Math.round(actualValue.doubleValue() * PRECISION) / PRECISION;
            mutationRate = value;
            mutationLabel.setText(String.valueOf(value));
        });
    }

}
