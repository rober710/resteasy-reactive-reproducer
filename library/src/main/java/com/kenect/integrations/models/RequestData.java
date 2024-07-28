package com.kenect.integrations.models;

import java.time.Instant;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Jacksonized
@Value
public class RequestData {
    long firstId;
    long lastId;
    Map<Long, Instant> idToUpdatedAt;
    String key;
    Instant updateRecordsFrom;
}
