package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

class Calculator {

    private static final int MAX_LENGTH = 15;

    private TextField line;
    private Button[] buttons;

    private boolean commaUsed = false;
    private boolean minusUsed = false;

    private double value = 0;
    private int operation = 0;
    private boolean waitAction = false;

    Calculator(Rectangle size) throws Exception {
        init(size);
    }

    private void init(Rectangle size) throws Exception {
        Stage stage = new Stage();
        VBox root = createStage();
        stage.setTitle("CalculatorFX");
        stage.setScene(new Scene(root, size.getWidth(), size.getHeight()));
        stage.setResizable(false);
        stage.show();
    }

    private VBox createStage() {
        VBox stage = new VBox();

        line = new TextField();
        line.setPrefHeight(200);
        line.setFont(Font.font(50));
        line.setText("0");
        line.setDisable(true);
        line.setAlignment(Pos.CENTER_RIGHT);

        buttons = new Button[] {
                new Button("7"),            //0
                new Button("8"),            //1
                new Button("9"),            //2
                new Button("4"),            //3
                new Button("5"),            //4
                new Button("6"),            //5
                new Button("1"),            //6
                new Button("2"),            //7
                new Button("3"),            //8
                new Button("0"),            //9
                new Button(","),            //10
                new Button("/"),            //11
                new Button("*"),            //12
                new Button("+"),            //13
                new Button("-"),            //14
                new Button("+/-"),          //15
                new Button("="),            //16
                new Button("sin"),          //17
                new Button("cos"),          //18
                new Button("tan"),          //19
                new Button("cat"),          //20
                new Button("√x"),          //21
                new Button("x²"),           //22
                new Button("x³"),           //23
                new Button("eˣ"),           //24
                new Button("AC"),           //25
                new Button("<-")            //26
        };

        for (Button button : buttons) {
            button.setPrefSize(100, 100);
            button.setFont(Font.font(30));
        }
        buttons[9].setPrefSize(210, 100);
        addListeners();

        GridPane numbers = new GridPane();
        numbers.add(buttons[0], 0, 0);
        numbers.add(buttons[1], 1, 0);
        numbers.add(buttons[2], 2, 0);
        numbers.add(buttons[3], 0, 1);
        numbers.add(buttons[4], 1, 1);
        numbers.add(buttons[5], 2, 1);
        numbers.add(buttons[6], 0, 2);
        numbers.add(buttons[7], 1, 2);
        numbers.add(buttons[8], 2, 2);
        numbers.add(buttons[9], 0, 3, 2, 1);
        numbers.add(buttons[10], 2, 3);

        GridPane operations = new GridPane();
        operations.add(buttons[21], 0, 0);
        operations.add(buttons[22], 1, 0);
        operations.add(buttons[23], 2, 0);
        operations.add(buttons[24], 3, 0);
        operations.add(buttons[17], 0, 1);
        operations.add(buttons[18], 1, 1);
        operations.add(buttons[19], 2, 1);
        operations.add(buttons[20], 3, 1);
        operations.add(buttons[11], 0, 2);
        operations.add(buttons[12], 1, 2);
        operations.add(buttons[13], 2, 2);
        operations.add(buttons[14], 3, 2);
        operations.add(buttons[25], 0, 3);
        operations.add(buttons[26], 1, 3);
        operations.add(buttons[15], 2, 3);
        operations.add(buttons[16], 3, 3);

        numbers.setAlignment(Pos.CENTER);
        numbers.setHgap(10);
        numbers.setVgap(10);

        operations.setAlignment(Pos.CENTER);
        operations.setHgap(10);
        operations.setVgap(10);

        HBox box = new HBox(numbers, operations);
        box.setSpacing(10);

        stage.getChildren().add(line);
        stage.getChildren().add(box);
        stage.setAlignment(Pos.CENTER);
        stage.setPadding(new Insets(20));
        stage.setSpacing(20);
        VBox.setVgrow(box, Priority.ALWAYS);

        return stage;
    }

    private void addListeners() {
        buttons[0].setOnAction(value -> checkConditionsForNumbers("7"));
        buttons[1].setOnAction(value -> checkConditionsForNumbers("8"));
        buttons[2].setOnAction(value -> checkConditionsForNumbers("9"));
        buttons[3].setOnAction(value -> checkConditionsForNumbers("4"));
        buttons[4].setOnAction(value -> checkConditionsForNumbers("5"));
        buttons[5].setOnAction(value -> checkConditionsForNumbers("6"));
        buttons[6].setOnAction(value -> checkConditionsForNumbers("1"));
        buttons[7].setOnAction(value -> checkConditionsForNumbers("2"));
        buttons[8].setOnAction(value -> checkConditionsForNumbers("3"));
        buttons[9].setOnAction(value -> {
            if ((commaUsed && minusUsed && line.getText().length() < MAX_LENGTH + 2) ||
                    (commaUsed && !minusUsed && line.getText().length() < MAX_LENGTH + 1) ||
                    (!commaUsed && minusUsed && line.getText().length() < MAX_LENGTH + 1) ||
                    (!commaUsed && !minusUsed && line.getText().length() < MAX_LENGTH)) {
                if (Double.parseDouble(line.getText()) != 0) {
                    if (!waitAction) line.setText(line.getText() + "0");
                    else {
                        line.setText("0");
                        waitAction = false;
                        minusUsed = false;
                        commaUsed = false;
                    }
                } else {
                    if (waitAction) {
                        line.setText("0");
                        waitAction = false;
                        minusUsed = false;
                        commaUsed = false;
                    } else {
                        if (commaUsed) line.setText(line.getText() + "0");
                    }
                }
            } else if (waitAction) {
                line.setText("0");
                waitAction = false;
                minusUsed = false;
                commaUsed = false;
            }
        });
        buttons[10].setOnAction(value -> {
            if (!commaUsed) {
                line.setText(line.getText() + ".");
                commaUsed = true;
            }
        });
        buttons[11].setOnAction(value -> addAction(1));
        buttons[12].setOnAction(value -> addAction(2));
        buttons[13].setOnAction(value -> addAction(3));
        buttons[14].setOnAction(value -> addAction(4));
        buttons[15].setOnAction(value -> {
            if (Double.parseDouble(line.getText()) != 0) {
                if (!minusUsed) {
                    line.setText("-" + line.getText());
                    minusUsed = true;
                } else {
                    line.setText(line.getText(1, line.getText().length()));
                    minusUsed = false;
                }
                if (waitAction) waitAction = false;
            }
        });
        buttons[16].setOnAction(value -> {
            switch (operation) {
                case 1: this.value /= Double.parseDouble(line.getText()); break;
                case 2: this.value *= Double.parseDouble(line.getText()); break;
                case 3: this.value += Double.parseDouble(line.getText()); break;
                case 4: this.value -= Double.parseDouble(line.getText()); break;
            }
            addChanges(this.value);
            operation = 0;
        });
        buttons[17].setOnAction(value -> {
            double a = Double.parseDouble(line.getText());
            a = Math.toRadians(a);
            a = Math.sin(a);
            addChanges(a);
        });
        buttons[18].setOnAction(value -> {
            double a = Double.parseDouble(line.getText());
            a = Math.toRadians(a);
            a = Math.cos(a);
            addChanges(a);
        });
        buttons[19].setOnAction(value -> {
            double a = Double.parseDouble(line.getText());
            a = Math.toRadians(a);
            a = Math.tan(a);
            addChanges(a);
        });
        buttons[20].setOnAction(value -> {
            double a = Double.parseDouble(line.getText());
            a = Math.toRadians(a);
            a = 1d / Math.tan(a);
            addChanges(a);
        });
        buttons[21].setOnAction(value -> {
            double a = Double.parseDouble(line.getText());
            a = Math.sqrt(a);
            addChanges(a);
        });
        buttons[22].setOnAction(value -> {
            double a = Double.parseDouble(line.getText());
            a = Math.pow(a, 2);
            addChanges(a);
        });
        buttons[23].setOnAction(value -> {
            double a = Double.parseDouble(line.getText());
            a = Math.pow(a, 3);
            addChanges(a);
        });
        buttons[24].setOnAction(value -> {
            double a = Double.parseDouble(line.getText());
            a = Math.exp(a);
            addChanges(a);
        });
        buttons[25].setOnAction(value -> {
            line.setText("0");
            commaUsed = false;
            minusUsed = false;
            operation = 0;
            this.value = 0;
            waitAction = false;
        });
        buttons[26].setOnAction(value -> {
            if (line.getText().length() >= 2) {
                if (line.getText().charAt(line.getText().length() - 1) == '.')
                    commaUsed = false;
                if (line.getText().length() == 2 && minusUsed) {
                    line.setText("0");
                    minusUsed = false;
                } else line.setText(line.getText(0, line.getText().length() - 1));
            } else if (line.getText().length() == 1) {
                line.setText("0");
                minusUsed = false;
            }
        });
    }

    private void checkConditionsForNumbers(String number) {
        if ((commaUsed && minusUsed && line.getText().length() < MAX_LENGTH + 2) ||
                (commaUsed && !minusUsed && line.getText().length() < MAX_LENGTH + 1) ||
                (!commaUsed && minusUsed && line.getText().length() < MAX_LENGTH + 1) ||
                (!commaUsed && !minusUsed && line.getText().length() < MAX_LENGTH)) {
            if (line.getText().length() == 1 && line.getText().charAt(0) == '0')
                line.setText(number);
            else {
                if (waitAction) {
                    line.setText(number);
                    waitAction = false;
                    minusUsed = false;
                    commaUsed = false;
                } else line.setText(line.getText() + number);
            }
        } else if (waitAction) {
            line.setText(number);
            waitAction = false;
            minusUsed = false;
            commaUsed = false;
        }
    }

    private void addAction(int operation) {
        switch (this.operation) {
            case 0: value = Double.parseDouble(line.getText()); break;
            case 1: value /= Double.parseDouble(line.getText()); break;
            case 2: value *= Double.parseDouble(line.getText()); break;
            case 3: value += Double.parseDouble(line.getText()); break;
            case 4: value -= Double.parseDouble(line.getText()); break;
        }
        line.setText(value % 1 != 0 ? (value + "") : ((int) value) + "");
        waitAction = true;
        minusUsed = value < 0;
        commaUsed = !(value % 1 == 0);
        this.operation = operation;
    }

    private void addChanges(double value) {
        line.setText(value % 1 != 0 ? (value + "") : ((int) value) + "");
        waitAction = true;
        minusUsed = value < 0;
        commaUsed = !(value % 1 == 0);
    }

//    private void printStatesOfVariables() {
//        System.out.println();
//        System.out.println("Comma: " + commaUsed);
//        System.out.println("Minus: " + minusUsed);
//        System.out.println("Action: " + operation);
//        System.out.println("Wait operation: " + waitAction);
//    }
}
