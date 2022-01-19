package sample.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.utils.FunctionType;
import sample.ga.GeneticAlgorithm;
import sample.utils.Logger;
import sample.ga.SelectionType;

public class Controller {

    @FXML
    private Button startButton;
    @FXML
    private Button openResultsButton;
    @FXML
    private ComboBox<FunctionType> functionComboBox;
    @FXML
    private ComboBox<SelectionType> selectionTypeComboBox;
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
    private static FunctionType functionType;
    private static SelectionType selectionType;
    private static int populationSize = 10;
    private static int generationNumber = 100;
    private static double crossoverRate = GeneticAlgorithm.DEFAULT_CROSSOVER_RATE;
    private static double mutationRate = GeneticAlgorithm.DEFAULT_MUTATION_RATE;
    private static final double PRECISION = 100.0;

    public void initialize() {
        setButtonListeners();
        initComboBoxes();
        setSlidersListeners();
    }

    private void setButtonListeners() {
        startButton.setOnAction(event1 -> {
            String beginText = functionType.description +
                    "\n---------------------------------------------------------\n";
            resultsArea.setText(beginText);
            openResultsButton.setDisable(true);
            ga = new GeneticAlgorithm(functionType, selectionType, populationSize,
                    generationNumber, crossoverRate, mutationRate);
            new Thread(() -> {
                String results = ga.runAlgorithm();
                resultsArea.appendText(results);
                openResultsButton.setDisable(false);
                openResultsButton.setOnAction(event2 -> {
                    Logger logger = ga.getLogger();
                    logger.getFileHandler().openFile();
                });
            }).start();
//            new Thread(() -> new GeneticAlgorithm(FunctionType.RASTRIGIN, SelectionType.ROULETTE_WHEEL, 10,
//                    generationNumber, crossoverRate, mutationRate).doResearch()).start();
//            new Thread(() -> new GeneticAlgorithm(FunctionType.RASTRIGIN, SelectionType.ROULETTE_WHEEL, 25,
//                    generationNumber, crossoverRate, mutationRate).doResearch()).start();
//            new Thread(() -> new GeneticAlgorithm(FunctionType.RASTRIGIN, SelectionType.ROULETTE_WHEEL, 50,
//                    generationNumber, crossoverRate, mutationRate).doResearch()).start();
//            new Thread(() -> new GeneticAlgorithm(FunctionType.RASTRIGIN, SelectionType.RANK, 10,
//                    generationNumber, crossoverRate, mutationRate).doResearch()).start();
//            new Thread(() -> new GeneticAlgorithm(FunctionType.RASTRIGIN, SelectionType.RANK, 25,
//                    generationNumber, crossoverRate, mutationRate).doResearch()).start();
//            new Thread(() -> new GeneticAlgorithm(FunctionType.RASTRIGIN, SelectionType.RANK, 50,
//                    generationNumber, crossoverRate, mutationRate).doResearch()).start();
//            new Thread(() -> new GeneticAlgorithm(FunctionType.RASTRIGIN, SelectionType.TOURNAMENT, 10,
//                    generationNumber, crossoverRate, mutationRate).doResearch()).start();
//            new Thread(() -> new GeneticAlgorithm(FunctionType.RASTRIGIN, SelectionType.TOURNAMENT, 25,
//                    generationNumber, crossoverRate, mutationRate).doResearch()).start();
//            new Thread(() -> new GeneticAlgorithm(FunctionType.RASTRIGIN, SelectionType.TOURNAMENT, 50,
//                    generationNumber, crossoverRate, mutationRate).doResearch()).start();
        });
    }

    private void initComboBoxes() {
        FunctionType[] functionTypes = FunctionType.types();
        functionType = functionTypes[0];
        functionComboBox.setValue(functionType);
        functionComboBox.setItems(FXCollections.observableArrayList(functionTypes));
        functionComboBox.valueProperty().addListener(
                (observable, oldValue, actualValue) -> functionType = actualValue);

        SelectionType[] selectionTypes = SelectionType.values();
        selectionType = selectionTypes[0];
        selectionTypeComboBox.setValue(selectionType);
        selectionTypeComboBox.setItems(FXCollections.observableArrayList(selectionTypes));
        selectionTypeComboBox.valueProperty().addListener(
                (observable, oldValue, actualValue) -> selectionType = actualValue);
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
