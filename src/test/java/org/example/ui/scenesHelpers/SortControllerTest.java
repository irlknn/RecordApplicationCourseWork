package org.example.ui.scenesHelpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.models.Record;
import org.example.repository.DBRecordCollectionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class SortControllerTest {

    private final int collectionId = 1;
    private DBRecordCollectionManager mockManager;
    private SortController sortController;

    @BeforeEach
    void setUp() {
        mockManager = mock(DBRecordCollectionManager.class);
        sortController = new SortController(mockManager, collectionId);
    }

    @Test
    void testSortBy_callsSortByParameterAndReturnsList() {
        ObservableList<Record> dummyList = FXCollections.observableArrayList();
        String param = "title";

        when(mockManager.sortByParameter(collectionId, param)).thenReturn(dummyList);

        ObservableList<Record> result = sortController.sortBy(param);

        verify(mockManager, times(1)).sortByParameter(collectionId, param);
        assertSame(dummyList, result);
    }

    @Test
    void testSortBy_withDifferentParameter() {
        ObservableList<Record> dummyList = FXCollections.observableArrayList();
        String param = "duration";

        when(mockManager.sortByParameter(collectionId, param)).thenReturn(dummyList);

        ObservableList<Record> result = sortController.sortBy(param);

        verify(mockManager, times(1)).sortByParameter(collectionId, param);
        assertSame(dummyList, result);
    }

    @Test
    void testSortBy_withNullParameter() {
        ObservableList<Record> dummyList = FXCollections.observableArrayList();

        when(mockManager.sortByParameter(collectionId, null)).thenReturn(dummyList);

        ObservableList<Record> result = sortController.sortBy(null);

        verify(mockManager, times(1)).sortByParameter(collectionId, null);
        assertSame(dummyList, result);
    }
}
