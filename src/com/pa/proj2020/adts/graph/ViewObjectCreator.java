package com.pa.proj2020.adts.graph;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public final class ViewObjectCreator {
    private static final ViewObjectCreator instance = new ViewObjectCreator();

    /**
     * Método que retorna a instancia de um ViewObjectCreator
     *
     * @return a instancia de um ViewObjectCreator
     */
    public static ViewObjectCreator getInstance() {
        return instance;
    }


    public ImageView createImageView(Image img) {
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(850.0);
        imageView.setImage(img);

        return imageView;
    }


    public Text createText(String fontName, int size, String innerText, Color color) {
        if (fontName == null || size <= 0 || innerText == null || color == null) {
            return new Text("");
        }

        Font font = createFont(fontName, size);
        Text text = new Text(innerText);

        text.setFill(color);
        text.setFont(font);

        return (text);
    }


    public VBox createVBox(Insets insets, int spacing, Pos alignment) {
        if (insets == null || spacing <= 0) {
            return null;
        }

        VBox vbox = new VBox();
        vbox.setPadding(insets);
        vbox.setSpacing(spacing);

        if (alignment != null) {
            vbox.setAlignment(alignment);
        }

        return vbox;
    }


    public HBox createHBox(Insets insets, int spacing, Pos alignment) {
        if (insets == null || spacing <= 0) {
            return null;
        }

        HBox hbox = new HBox();
        hbox.setPadding(insets);
        hbox.setSpacing(spacing);

        if (alignment != null) {
            hbox.setAlignment(alignment);
        }

        return hbox;
    }


    public TextField createTextField(String innerText, int maxWidth, int minWidth) {
        TextField textField = new TextField();

        if (innerText == null || maxWidth <= 0 || minWidth < 0) {
            return textField;
        }

        textField.setText(innerText);
        textField.setMaxWidth(maxWidth);
        textField.setMinWidth(minWidth);

        return textField;
    }


    public Font createFont(String fontName, int size) {
        if (fontName == null || size <= 0) {
            return null;
        }

        return Font.font(fontName, size);
    }


    public Label createLabel(String innerText, Color color, String fontName, int size) {
        if (innerText == null || color == null || fontName == null) {
            return null;
        }

        Label label = new Label();
        Font font = createFont(fontName, size);

        label.setText(innerText);
        label.setTextFill(color);
        label.setFont(font);

        return label;
    }


    public ComboBox<String> createComboBoxString(String innerText, double v, double v1) {
        ComboBox<String> comboBox = new ComboBox<>();
        if (innerText == null || v <= 0 || v1 <= 0) {
            return comboBox;
        }

        comboBox.setPromptText(innerText);
        comboBox.setPrefSize(v, v1);

        return comboBox;
    }


    public ListView<String> createListViewString(double v, double v1, String value) {
        ListView<String> list = new ListView<>();

        if (v <= 0 || v1 <= 0) {
            return list;
        }

        list.setMaxSize(v, v1);

        if (value != null) {
            list.getItems().add(value);
        }

        return list;
    }

}
