/**
 * String Pattern Search Algorithm - JavaFX GUI Version
 * =====================================================
 * Interactive graphical interface for learning pattern matching
 * 
 * @author Learning Tool
 * @version 1.0
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class PatternSearchGUI extends Application {
    
    // UI Components
    private TextField textField;
    private TextField patternField;
    private TextArea outputArea;
    private Canvas visualCanvas;
    private Slider speedSlider;
    private CheckBox caseSensitiveCheck;
    private CheckBox highlightMatchCheck;
    private Button searchButton;
    private Button stopButton;
    private Label statusLabel;
    
    // State variables
    private volatile boolean isSearching = false;
    private Task<Void> searchTask;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("🔍 Pattern Search Algorithm - Interactive Learning Tool");
        
        // Main container
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f0;");
        
        // Title Section
        VBox titleBox = createTitleSection();
        root.setTop(titleBox);
        
        // Main Content
        VBox mainContent = new VBox(15);
        mainContent.setPadding(new Insets(20));
        
        // Input Section
        VBox inputSection = createInputSection();
        
        // Visualization Section
        VBox vizSection = createVisualizationSection();
        
        // Output Section
        VBox outputSection = createOutputSection();
        
        mainContent.getChildren().addAll(inputSection, vizSection, outputSection);
        
        // Wrap in ScrollPane for better responsiveness
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #f0f0f0;");
        root.setCenter(scrollPane);
        
        // Status Bar
        HBox statusBar = createStatusBar();
        root.setBottom(statusBar);
        
        // Create scene
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Handle window close
        primaryStage.setOnCloseRequest(e -> {
            isSearching = false;
            if (searchTask != null) {
                searchTask.cancel();
            }
        });
    }
    
    private VBox createTitleSection() {
        VBox titleBox = new VBox(5);
        titleBox.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15;");
        titleBox.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("🔍 String Pattern Search Algorithm");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.WHITE);
        
        Label subtitleLabel = new Label("Learn how computers search for patterns in text");
        subtitleLabel.setFont(Font.font("Arial", 16));
        subtitleLabel.setTextFill(Color.web("#ecf0f1"));
        
        titleBox.getChildren().addAll(titleLabel, subtitleLabel);
        return titleBox;
    }
    
    private VBox createInputSection() {
        VBox inputBox = new VBox(10);
        inputBox.setStyle("-fx-background-color: white; -fx-padding: 15; " +
                         "-fx-border-color: #bdc3c7; -fx-border-radius: 5; " +
                         "-fx-background-radius: 5;");
        
        Label inputLabel = new Label("📝 Input");
        inputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Text input
        Label textLabel = new Label("Text to search in:");
        textLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        textField = new TextField("hello world");
        textField.setFont(Font.font("Courier New", 16));
        textField.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 2;");
        
        // Pattern input
        Label patternLabel = new Label("Pattern to find:");
        patternLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        patternField = new TextField("wor");
        patternField.setFont(Font.font("Courier New", 16));
        patternField.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 2;");
        
        // Options
        HBox optionsBox = new HBox(15);
        caseSensitiveCheck = new CheckBox("Case Sensitive");
        caseSensitiveCheck.setSelected(true);
        highlightMatchCheck = new CheckBox("Highlight Matches");
        highlightMatchCheck.setSelected(true);
        
        // Speed control
        HBox speedBox = new HBox(10);
        speedBox.setAlignment(Pos.CENTER_LEFT);
        Label speedLabel = new Label("Animation Speed:");
        speedSlider = new Slider(0.1, 2.0, 0.5);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(0.5);
        speedSlider.setPrefWidth(200);
        speedBox.getChildren().addAll(speedLabel, speedSlider);
        
        optionsBox.getChildren().addAll(caseSensitiveCheck, highlightMatchCheck, speedBox);
        
        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        searchButton = new Button("🔍 Start Search");
        searchButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        searchButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; " +
                            "-fx-padding: 12 24; -fx-cursor: hand;");
        searchButton.setOnAction(e -> startSearch());
        
        stopButton = new Button("⏹ Stop");
        stopButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        stopButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                          "-fx-padding: 12 24; -fx-cursor: hand;");
        stopButton.setDisable(true);
        stopButton.setOnAction(e -> stopSearch());
        
        Button clearButton = new Button("🗑 Clear");
        clearButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        clearButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; " +
                           "-fx-padding: 12 24; -fx-cursor: hand;");
        clearButton.setOnAction(e -> clearOutput());
        
        Button examplesButton = new Button("📚 Examples");
        examplesButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        examplesButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                              "-fx-padding: 10 20; -fx-cursor: hand;");
        examplesButton.setOnAction(e -> showExamples());
        
        buttonBox.getChildren().addAll(searchButton, stopButton, clearButton, examplesButton);
        
        inputBox.getChildren().addAll(
            inputLabel,
            textLabel, textField,
            patternLabel, patternField,
            optionsBox,
            buttonBox
        );
        
        return inputBox;
    }
    
    private VBox createVisualizationSection() {
        VBox vizBox = new VBox(10);
        vizBox.setStyle("-fx-background-color: white; -fx-padding: 15; " +
                       "-fx-border-color: #bdc3c7; -fx-border-radius: 5; " +
                       "-fx-background-radius: 5;");
        
        Label vizLabel = new Label("👁 Visual Representation");
        vizLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        visualCanvas = new Canvas(950, 170);
        visualCanvas.setStyle("-fx-border-color: #bdc3c7;");
        
        vizBox.getChildren().addAll(vizLabel, visualCanvas);
        return vizBox;
    }
    
    private VBox createOutputSection() {
        VBox outputBox = new VBox(10);
        outputBox.setStyle("-fx-background-color: white; -fx-padding: 15; " +
                          "-fx-border-color: #bdc3c7; -fx-border-radius: 5; " +
                          "-fx-background-radius: 5;");
        
        Label outputLabel = new Label("📊 Search Process & Results");
        outputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setFont(Font.font("Courier New", 13));
        outputArea.setPrefRowCount(15);
        outputArea.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 2;");
        
        outputBox.getChildren().addAll(outputLabel, outputArea);
        VBox.setVgrow(outputArea, Priority.ALWAYS);
        
        return outputBox;
    }
    
    private HBox createStatusBar() {
        HBox statusBar = new HBox();
        statusBar.setStyle("-fx-background-color: #34495e; -fx-padding: 10;");
        
        statusLabel = new Label("Ready to search");
        statusLabel.setTextFill(Color.WHITE);
        statusLabel.setFont(Font.font("Arial", 12));
        
        statusBar.getChildren().add(statusLabel);
        return statusBar;
    }
    
    private void visualizeStep(String text, String pattern, int position, 
                               int comparingIndex, Boolean isMatch) {
        GraphicsContext gc = visualCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, visualCanvas.getWidth(), visualCanvas.getHeight());
        
        if (text.isEmpty() || pattern.isEmpty()) {
            return;
        }
        
        double charWidth = 45;
        double charHeight = 45;
        double startX = 50;
        double startY = 70;
        
        // Draw text characters
        for (int i = 0; i < text.length(); i++) {
            double x = startX + i * charWidth;
            
            // Determine color
            Color boxColor;
            if (position <= i && i < position + pattern.length()) {
                if (comparingIndex >= 0 && i == position + comparingIndex) {
                    if (isMatch != null) {
                        boxColor = isMatch ? Color.web("#27ae60") : Color.web("#e74c3c");
                    } else {
                        boxColor = Color.web("#f39c12");
                    }
                } else {
                    boxColor = Color.web("#3498db");
                }
            } else {
                boxColor = Color.web("#95a5a6");
            }
            
            // Draw character box
            gc.setFill(boxColor);
            gc.fillRect(x, startY, charWidth - 2, charHeight);
            gc.setStroke(Color.web("#2c3e50"));
            gc.setLineWidth(2);
            gc.strokeRect(x, startY, charWidth - 2, charHeight);
            
            // Draw character
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
            String charStr = text.charAt(i) == ' ' ? "␣" : String.valueOf(text.charAt(i));
            gc.fillText(charStr, x + charWidth / 2 - 10, startY + charHeight / 2 + 7);
            
            // Draw index
            gc.setFill(Color.web("#7f8c8d"));
            gc.setFont(Font.font("Arial", 12));
            gc.fillText(String.valueOf(i), x + charWidth / 2 - 5, startY + charHeight + 15);
        }
        
        // Draw pattern indicator
        if (position >= 0 && position < text.length()) {
            double windowX = startX + position * charWidth;
            double windowY = startY - 25;
            
            String patternText = "Pattern: '" + pattern + "'";
            
            // Background for pattern label
            gc.setFill(Color.WHITE);
            gc.setStroke(Color.web("#e74c3c"));
            gc.setLineWidth(1);
            gc.strokeRect(windowX - 2, windowY - 15, patternText.length() * 7 + 4, 18);
            gc.fillRect(windowX - 2, windowY - 15, patternText.length() * 7 + 4, 18);
            
            // Pattern text
            gc.setFill(Color.web("#e74c3c"));
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            gc.fillText(patternText, windowX, windowY);
            
            // Draw arrow
            double arrowX = windowX + (pattern.length() * charWidth) / 2 - 1;
            gc.setStroke(Color.web("#e74c3c"));
            gc.setLineWidth(2);
            gc.strokeLine(arrowX, windowY + 3, arrowX, startY - 3);
            
            // Arrow head
            gc.strokeLine(arrowX, startY - 3, arrowX - 5, startY - 8);
            gc.strokeLine(arrowX, startY - 3, arrowX + 5, startY - 8);
        }
    }
    
    private void writeOutput(String text) {
        Platform.runLater(() -> {
            outputArea.appendText(text);
            outputArea.setScrollTop(Double.MAX_VALUE);
        });
    }
    
    private void updateStatus(String text) {
        Platform.runLater(() -> statusLabel.setText(text));
    }
    
    private void clearOutput() {
        outputArea.clear();
        GraphicsContext gc = visualCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, visualCanvas.getWidth(), visualCanvas.getHeight());
        updateStatus("Cleared");
    }
    
    private void startSearch() {
        String text = textField.getText();
        String pattern = patternField.getText();
        
        if (text.isEmpty() && pattern.isEmpty()) {
            showAlert("Input Required", "Please enter both text and pattern!");
            return;
        }
        
        clearOutput();
        updateStatus("Searching...");
        
        searchButton.setDisable(true);
        stopButton.setDisable(false);
        isSearching = true;
        
        // Create background task
        searchTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                findPatternAnimated(text, pattern);
                return null;
            }
        };
        
        searchTask.setOnSucceeded(e -> {
            searchButton.setDisable(false);
            stopButton.setDisable(true);
        });
        
        searchTask.setOnFailed(e -> {
            searchButton.setDisable(false);
            stopButton.setDisable(true);
            showAlert("Error", "An error occurred during search");
        });
        
        searchTask.setOnCancelled(e -> {
            searchButton.setDisable(false);
            stopButton.setDisable(true);
        });
        
        Thread thread = new Thread(searchTask);
        thread.setDaemon(true);
        thread.start();
    }
    
    private void stopSearch() {
        isSearching = false;
        if (searchTask != null) {
            searchTask.cancel();
        }
        updateStatus("Search stopped");
    }
    
    private void findPatternAnimated(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        
        // Handle edge cases
        if (pattern.isEmpty()) {
            writeOutput("⚠️ Pattern is empty!\n");
            return;
        }
        
        if (text.isEmpty()) {
            writeOutput("⚠️ Text is empty!\n");
            return;
        }
        
        if (pattern.length() > text.length()) {
            writeOutput(String.format("⚠️ Pattern (length %d) is longer than text (length %d)\n",
                pattern.length(), text.length()));
            return;
        }
        
        // Apply case sensitivity
        String searchText = text;
        String searchPattern = pattern;
        if (!caseSensitiveCheck.isSelected()) {
            searchText = text.toLowerCase();
            searchPattern = pattern.toLowerCase();
            writeOutput("🔤 Case-insensitive mode enabled\n");
        }
        
        // Make final copies for use in lambda expressions
        final String finalSearchText = searchText;
        final String finalSearchPattern = searchPattern;
        
        writeOutput("\n============================================================\n");
        writeOutput(String.format("🔍 Searching for '%s' in '%s'\n", pattern, text));
        writeOutput(String.format("📏 Text length: %d, Pattern length: %d\n",
            text.length(), pattern.length()));
        writeOutput("============================================================\n\n");
        
        int searchLimit = finalSearchText.length() - finalSearchPattern.length() + 1;
        
        // Main search loop
        for (int textPosition = 0; textPosition < searchLimit && isSearching; textPosition++) {
            writeOutput(String.format("\n📍 Position %d:\n", textPosition));
            
            final int pos = textPosition;
            Platform.runLater(() -> visualizeStep(finalSearchText, finalSearchPattern, pos, -1, null));
            sleep(speedSlider.getValue() * 300);
            
            boolean isMatch = true;
            
            // Compare characters
            for (int patternPosition = 0; patternPosition < finalSearchPattern.length() && isSearching; patternPosition++) {
                int currentTextIndex = textPosition + patternPosition;
                char textChar = finalSearchText.charAt(currentTextIndex);
                char patternChar = finalSearchPattern.charAt(patternPosition);
                
                boolean matchesChar = textChar == patternChar;
                
                final int pp = patternPosition;
                final boolean match = matchesChar;
                Platform.runLater(() -> visualizeStep(finalSearchText, finalSearchPattern, pos, pp, match));
                
                String symbol = matchesChar ? "✓" : "✗";
                writeOutput(String.format("   Comparing: text[%d]='%c' ↔ pattern[%d]='%c' %s\n",
                    currentTextIndex, textChar, patternPosition, patternChar, symbol));
                
                sleep(speedSlider.getValue() * 500);
                
                if (!matchesChar) {
                    isMatch = false;
                    writeOutput(String.format("   ❌ No match at position %d\n", textPosition));
                    break;
                }
            }
            
            if (isMatch && isSearching) {
                matches.add(textPosition);
                writeOutput(String.format("   ✅ MATCH FOUND at position %d!\n", textPosition));
                final int finalPos = textPosition;
                Platform.runLater(() -> visualizeStep(finalSearchText, finalSearchPattern, 
                    finalPos, finalSearchPattern.length() - 1, true));
                sleep(speedSlider.getValue() * 1000);
            }
        }
        
        // Show results
        writeOutput("\n============================================================\n");
        if (!matches.isEmpty() && isSearching) {
            writeOutput(String.format("🎉 Found %d match(es) at position(s): %s\n",
                matches.size(), matches));
            updateStatus(String.format("Search complete! Found %d match(es)", matches.size()));
        } else if (isSearching) {
            writeOutput(String.format("❌ Pattern '%s' not found in '%s'\n", pattern, text));
            updateStatus("Search complete - No matches found");
        }
        writeOutput("============================================================\n");
        
        isSearching = false;
    }
    
    private void sleep(double milliseconds) {
        try {
            Thread.sleep((long) milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void showExamples() {
        Stage exampleStage = new Stage();
        exampleStage.setTitle("📚 Example Test Cases");
        
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: white;");
        
        Label titleLabel = new Label("📚 Click an example to load it");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        vbox.getChildren().add(titleLabel);
        
        String[][] examples = {
            {"Basic Search", "hello world", "wor"},
            {"Overlapping Matches", "banana", "ana"},
            {"Pattern Not Found", "hello", "world"},
            {"Repeated Characters", "aaaa", "aa"},
            {"Single Character", "abcabc", "a"},
            {"Multiple Words", "the quick brown fox", "quick"},
            {"Mississippi Test", "Mississippi", "issi"},
            {"Programming", "programming is fun", "gram"}
        };
        
        for (String[] example : examples) {
            Button btn = new Button(example[0] + "\nText: '" + example[1] + 
                                  "' | Pattern: '" + example[2] + "'");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 12; " +
                        "-fx-cursor: hand; -fx-alignment: center-left;");
            btn.setFont(Font.font("Arial", 13));
            
            String text = example[1];
            String pattern = example[2];
            btn.setOnAction(e -> {
                textField.setText(text);
                patternField.setText(pattern);
                exampleStage.close();
                updateStatus("Loaded example - Ready to search");
            });
            
            vbox.getChildren().add(btn);
        }
        
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        
        Scene scene = new Scene(scrollPane, 600, 500);
        exampleStage.setScene(scene);
        exampleStage.show();
    }
    
    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

