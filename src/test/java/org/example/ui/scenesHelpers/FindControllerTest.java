package org.example.ui.scenesHelpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.repository.DBRecordCollectionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class FindControllerTest {

    private final int collectionId = 1;
    private DBRecordCollectionManager mockManager;
    private FindController findController;

    @BeforeEach
    void setup() {
        mockManager = mock(DBRecordCollectionManager.class);
        findController = new FindController(mockManager, collectionId);
    }

    @Test
    void testFindBy_titleOrStyle_callsFindByParameter() {
        ObservableList<Record> dummyList = FXCollections.observableArrayList();
        when(mockManager.findByParameter(collectionId, "title", "inputValue")).thenReturn(dummyList);
        when(mockManager.findByParameter(collectionId, "style", "inputValue")).thenReturn(dummyList);

        // parameter = "title"
        ObservableList<Record> result1 = findController.findBy("title", "inputValue");
        verify(mockManager).findByParameter(collectionId, "title", "inputValue");
        assertSame(dummyList, result1);

        // parameter = "style"
        ObservableList<Record> result2 = findController.findBy("style", "inputValue");
        verify(mockManager).findByParameter(collectionId, "style", "inputValue");
        assertSame(dummyList, result2);
    }

    @Test
    void testFindBy_duration_callsFindByDuration() {
        ObservableList<Record> dummyList = FXCollections.observableArrayList();
        when(mockManager.findByDuration(collectionId, "00:00:00", "inputValue")).thenReturn(dummyList);

        ObservableList<Record> result = findController.findBy("duration", "inputValue");

        verify(mockManager).findByDuration(collectionId, "00:00:00", "inputValue");
        assertSame(dummyList, result);
    }

    @Test
    void testFindBy_default_callsFindByParameterWithTitle() {
        ObservableList<Record> dummyList = FXCollections.observableArrayList();
        when(mockManager.findByParameter(collectionId, "title", "inputValue")).thenReturn(dummyList);

        ObservableList<Record> result = findController.findBy("unknownParam", "inputValue");

        verify(mockManager).findByParameter(collectionId, "title", "inputValue");
        assertSame(dummyList, result);
    }
}
