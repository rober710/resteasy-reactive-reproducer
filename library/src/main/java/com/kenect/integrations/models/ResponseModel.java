package com.kenect.integrations.models;

import java.util.Collection;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Jacksonized
@Value
public class ResponseModel<T> {
    private final long firstId;
    private final long lastId;
    @Singular
    private final Collection<T> updates;
    @Singular
    private final Collection<Long> deletedIds;
}
