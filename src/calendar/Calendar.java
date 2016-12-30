/*
 * Copyright © 2016 Toru Takahahshi. All rights reserved.
 */
package calendar;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * カレンダーを表示するJavaFXアプリケーションクラス。
 *
 */
public class Calendar extends Application {
    private static final Logger logger = Logger.getLogger(Calendar.class.getName());
    private Stage stage;
    private ScheduledExecutorService executor;
    private LocalDate today;
    private Holidays holidays;
    private Pane rootPane;
    private Node calendar;

    @Override
    public void start(Stage primaryStage) {
        // コマンドライン引数の解析
        stage = primaryStage;
        parseParameters();

        today = LocalDate.now();
        calendar = createDatePickerPopup(today);

        rootPane = new StackPane();
        rootPane.getChildren().add(calendar);

        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add(getClass().getResource("Calendar.css").toExternalForm());

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(this::crossoverDate, secondsTillTomorrow(LocalDateTime.now()), TimeUnit.SECONDS);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> executor.shutdownNow());
        primaryStage.show();
    }

    /**
     * 引数に指定した日付を「今日」としてカレンダー表示を生成して返却する。
     *
     * @param date 今日
     * @return カレンダー表示ノード
     */
    private Node createDatePickerPopup(LocalDate date) {
        logger.entering(this.getClass().getName(), "createDatePickerPopup");
        DatePicker datePicker = new DatePicker(date);
        // 日付セルのファクトリを定義し、曜日名（英名）をスタイルクラスに追加した日付を生成
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().add(DayOfWeek.from(item).getDisplayName(TextStyle.FULL, Locale.US).toLowerCase());
                if (holidays.contains(item)) {
                    getStyleClass().add("holiday");
                }
            }
        });
        Node calendar = new DatePickerSkin(datePicker).getPopupContent();
        return calendar;
    }

    /**
     * 指定した時間から日付が変わるまでの時間（秒）を計算する。
     */
    private long secondsTillTomorrow(LocalDateTime time) {
        LocalDateTime tomorrow = time.plusDays(1).with(LocalTime.MIDNIGHT);
        return Duration.between(time, tomorrow).getSeconds();
    }

    /**
     * 日付更新時にカレンダーを再作成する。
     * <p>
     * 現在の時刻を取得し、その日付が、フィールドtodayの日付より先であれば日付更新処理を実施し、
     * 次の日付更新処理ををスケジュールする。
     * <p>
     * そうでなければ、日付更新処理が何らかの事情で早めに実行されたとみなし、次の日付更新処理の
     * スケジュールのみ実施する。
     */
    private void crossoverDate() {
        LocalDateTime now = LocalDateTime.now();
        if (today.isBefore(now.toLocalDate())) {
            Platform.runLater(() -> {
                today = today.plusDays(1);
                rootPane.getChildren().remove(calendar);
                rootPane.getChildren().add(createDatePickerPopup(today));
            });
        }
        executor.schedule(this::crossoverDate, secondsTillTomorrow(now), TimeUnit.SECONDS);
    }

    /**
     * コマンドラインオプション（名前付き値）を解析する。
     * 
     * 表示位置・大きさの指定があれば反映する。
     * 祝日設定ファイルが指定されていれば読み込む。
     */
    private void parseParameters() {
        Map<String, String> params = getParameters().getNamed();
        Platform.runLater(() -> {
            stage.setX(Double.valueOf(params.getOrDefault("x", "0.0")));
            stage.setY(Double.valueOf(params.getOrDefault("y", "0.0")));
            stage.setWidth(Double.valueOf(params.getOrDefault("width", "144")));
            stage.setHeight(Double.valueOf(params.getOrDefault("height", "144")));
        });
        holidays = new Holidays(params.getOrDefault("holiday", "holidays.conf"));
        verboseLogging(params.getOrDefault("verbose", "v"));
    }

    private void verboseLogging(String vs) {
        Level verbose = vs.length() < 1 ? Level.INFO
                : vs.length() == 1 ? Level.CONFIG
                : vs.length() == 2 ? Level.FINE
                : vs.length() == 3 ? Level.FINER
                : Level.FINEST;
        Logger.getLogger("calendar").setLevel(verbose);
        Arrays.stream(Logger.getLogger("").getHandlers()).forEach(handler -> handler.setLevel(verbose));
    }
    
    /**
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        System.out.println("Calendar program start");
        launch(args);
    }

}
