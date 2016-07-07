/*
 * Copyright © 2016 Toru Takahahshi. All rights reserved.
 */
package calendar;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.time.LocalDate;
import javafx.application.Application;
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
    
    @Override
    public void start(Stage primaryStage) {
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
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        System.out.println("Calendar program start");
        launch(args);
    }
    
}
