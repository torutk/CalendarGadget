/*
 * Copyright (C) 2016 Toru Takahahshi. All Rights Reserved.
 */
package calendarfx;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * カレンダー表示プログラム。
 */
public class CalendarFx extends Application {
    private static final double MAX_WIDTH = 1024d;
    private static final double MIN_WIDTH = 64d;
    private static final double MAX_HEIGHT = 1024d;
    private static final double MIN_HEIGHT = 64d;
    
    private Stage stage;
    private ContextMenu popup = new ContextMenu();
    private ResourceBundle bundle;
    
    @Override
    public void start(Stage primaryStage) {
        parseParameters();
        bundle = ResourceBundle.getBundle(getClass().getName());
        
        DatePickerSkin skin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node calendar = skin.getPopupContent();
        
        StackPane root = new StackPane();
        root.getChildren().add(calendar);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("Calendar.css").toExternalForm()
        );
        // ポップアップメニュー        
        MenuItem exitItem = new MenuItem(bundle.getString("menu_exit"));
        exitItem.setOnAction(e -> Platform.exit());
        MenuItem zoomInItem = new MenuItem(bundle.getString("menu_zoomIn"));
        zoomInItem.setOnAction(e -> zoom(1.1));
        MenuItem zoomOutItem = new MenuItem(bundle.getString("menu_zoomOut"));
        zoomOutItem.setOnAction(e -> zoom(0.9));
        popup.getItems().addAll(zoomInItem, zoomOutItem, exitItem);
        // コンテキストメニュー操作（OS依存）をしたときに、ポップアップメニュー表示
        // Windows OSでは、マウスの右クリック、touchパネルの長押しで発生
        root.setOnContextMenuRequested(e -> {
            popup.show(primaryStage, e.getScreenX(), e.getScreenY());
        });
        
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;
    }
    
    private void zoom(double factor) {
        double nextWidth = stage.getWidth() * factor;
        double width = Math.max(Math.min(nextWidth, MAX_WIDTH), MIN_WIDTH);
        double nextHeight = stage.getHeight() * factor;
        double height = Math.max(Math.min(nextHeight, MAX_HEIGHT), MIN_HEIGHT);
        stage.setWidth(width);
        stage.setHeight(height);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void parseParameters() {
        Map<String, String> params = getParameters().getNamed();
        Platform.runLater(() -> stage.setX(Double.valueOf(params.getOrDefault("x", "0.0"))));
        Platform.runLater(() -> stage.setY(Double.valueOf(params.getOrDefault("y", "0.0"))));
        Platform.runLater(() -> stage.setWidth(Double.valueOf(params.getOrDefault("width", "144"))));
        Platform.runLater(() -> stage.setHeight(Double.valueOf(params.getOrDefault("height", "144"))));
    }
}
