package utils;

import javafx.collections.ObservableList;
import models.Record;

import java.time.LocalTime;

import static utils.TimeUtils.changeTimeFormat;

public class CollectionService {
    private ObservableList<Record> records;

    public CollectionService(ObservableList<Record> records) {
        this.records = records;
    }

    public String collectionDuration() {
        LocalTime result = LocalTime.of(0, 0, 0);
        for (Record r : records) {
            result = add(result, r.getDuration());
        }
        return changeTimeFormat(result);
    }

    private LocalTime add(LocalTime a, LocalTime b) {
        return a.plusHours(b.getHour())
                .plusMinutes(b.getMinute())
                .plusSeconds(b.getSecond());
    }

}
