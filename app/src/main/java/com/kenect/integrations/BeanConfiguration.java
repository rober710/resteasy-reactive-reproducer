package com.kenect.integrations;

import com.kenect.integrations.models.LibraryService;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;

public class BeanConfiguration {

    @ApplicationScoped
    public LibraryService<Map<String, Object>> serviceInstanceFromLib() {
        return new LibraryService<>();
    }
}
