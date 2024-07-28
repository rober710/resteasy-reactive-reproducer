package com.kenect.integrations.models;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * Same as with the controller, this class allows services have a common set of functionality built-in.
 */
@RequiredArgsConstructor
public class LibraryService<T> {

    public ResponseModel<T> doSomething(RequestData requestData) {
        return ResponseModel.<T>builder()
                .firstId(1L)
                .lastId(100L)
                .updates(Collections.emptyList())
                .deletedIds(List.of(1L, 2L, 3L))
                .build();
    }
}
