package org.example.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.Record;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectionServiceTest {

    @Test
    void testCollectionDuration_withMultipleRecords() {
        ObservableList<Record> records = FXCollections.observableArrayList(
                new Record("Rock", "Title1", LocalTime.of(0, 2, 30)),
                new Record("Jazz", "Title2", LocalTime.of(0, 1, 15)),
                new Record("Pop", "Title3", LocalTime.of(0, 3, 45))
        );

        CollectionService service = new CollectionService(records);
        String totalDuration = service.collectionDuration();

        assertEquals("00:07:30", totalDuration);
    }

    @Test
    void testCollectionDuration_withEmptyList() {
        CollectionService service = new CollectionService(FXCollections.observableArrayList());
        assertEquals("00:00:00", service.collectionDuration());
    }
}
