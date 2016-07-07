/*
 * Copyright © 2016 Toru Takahahshi. All rights reserved.
 */
package calendar;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.time.LocalDate;
import java.util.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * カレンダーを表示するJavaFXアプリケーションクラス。
 * 
 */
public class Calendar extends Application {
    private Stage stage;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        parseParameters();
        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node calendar = datePickerSkin.getPopupContent();
        
        StackPane root = new StackPane();
        root.getChildren().add(calendar);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Calendar.css").toExternalForm());
        
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * コマンドラインオプション（名前付き値）を解析し、表示位置・大きさの指定があれば反映する。
     */
    private void parseParameters() {
        Map<String, String> params = getParameters().getNamed();
        Platform.runLater(() -> {
            stage.setX(Double.valueOf(params.getOrDefault("x", "0.0")));
            stage.setY(Double.valueOf(params.getOrDefault("y", "0.0")));
            stage.setWidth(Double.valueOf(params.getOrDefault("width", "144")));
            stage.setHeight(Double.valueOf(params.getOrDefault("height", "144")));
        });
    }
    
    /**
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        System.out.println("Calendar program start");
        launch(args);
    }
    
}
