package utils;

import javafx.collections.ObservableList;
import models.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static utils.TimeUtils.changeTimeFormat;

public class RecordFinder {
    private static final Logger logger = LogManager.getLogger(RecordFinder.class);
    private static List<Record> list;

    public RecordFinder(List<Record> list) {
        this.list = list;
    }

    public static ObservableList<Record> findBy(String parameter, String input) {
        switch (parameter) {
//            case "title" -> {
//                return findRecordsByTitle(input);
//            }
            case "style" -> {
                return (ObservableList<Record>) findRecordsByStyle(input);
            }
            case "duration" -> {
                return (ObservableList<Record>) findRecordsByDuration(changeTimeFormat(input));
            }
            default -> {
                return (ObservableList<Record>) findRecordsByTitle(input);
            }
        }
    }

    public static List<Record> findRecordsByTitle(String title) {
        try {
            return list.stream()
                    .filter(record -> record.getTitle().equals(title))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to find records by title like {}", title);
            return null;
        }
    }

    public static List<Record> findRecordsByStyle(String style) {
        try {
            return list.stream().
                    filter(record -> record.getStyle().equals(style))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to find records by style like {}", style);
            return null;
        }
    }

    public static List<Record> findRecordsByDuration(LocalTime start) {
        try {
            return list.stream()
                    .filter(record -> record.getDuration().isAfter(start) && record.getDuration().isBefore(start))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to find records by duration");
            return null;
        }
    }
}
