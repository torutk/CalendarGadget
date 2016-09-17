/*
 * Copyright © 2016 Toru Takahahshi. All rights reserved.
 */
package calendar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 * 外部ファイルに定義された日付リストを読み込み保持し、日付指定の問い合わせに答えるクラス。
 * 
 * 最初に、コンストラクタの引数で指定したパスのファイルから日付リストを読み込む。
 * ファイルが無い場合は、クラスパスから /holidays.conf を読み込む。
 * 日付リストの書式は、ISO8601の年月日形式（例: 2016-09-17）を1行に1個ずつ記述。
 * 行の先頭を#で始めるとその行は読み飛ばす。
 * 
 * @author toru
 */
public class Holidays {
    private static final Logger logger = Logger.getLogger(Holidays.class.getName());
    private static final String RESOURCE_NAME = "/holidays.conf";
    private List<LocalDate> holidays;
    
    public Holidays(String path) {
        Path filePath = Paths.get(path);
        if (Files.exists(filePath)) {
            readFromPath(filePath);
        } else {
            readFromStream();
        }
    }

    /**
     * 祝日設定を引数で指定したパスから読み込む。
     *
     * 最初に指定したファイルを読み込み、日付のリストを生成し返却する。
     *
     */
    private void readFromPath(Path path) {
        try {
            holidays = toDates(Files.lines(path));
            logger.config(() -> String.format("Holidays are configured by file '%s'", path));
        } catch (IOException ex) {
            logger.config(() -> String.format("Holidays configure file '%s' cannot read because %s", path, ex.getLocalizedMessage()));
        }
    }

    /**
     * リソースから祝日設定を読み込む。
     */
    private void readFromStream() {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(RESOURCE_NAME)))) {
            holidays = toDates(reader.lines());
            logger.config(() -> String.format("Holidays are configured by resource '%S'", RESOURCE_NAME));
        } catch (IOException ex) {
            logger.warning(() -> String.format("holidays cannot be configured, resource '%s' read error:", RESOURCE_NAME, ex.getLocalizedMessage()));
        }
    }

    boolean contains(LocalDate item) {
        return holidays.contains(item);
    }
       
    private List<LocalDate> toDates(Stream<String> stream) {
        return stream.filter(s -> !s.isEmpty())
                .filter(s -> !s.startsWith("#"))
                .map(s -> LocalDate.parse(s))
                .collect(toList());
    }
}
