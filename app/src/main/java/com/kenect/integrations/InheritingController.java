package com.kenect.integrations;

import com.kenect.integrations.models.LibraryController;
import com.kenect.integrations.models.LibraryService;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import java.util.Map;
import lombok.AllArgsConstructor;

@Path("/my-service/problematic")
@ApplicationScoped
@AllArgsConstructor
@Authenticated
public class InheritingController extends LibraryController<Map<String, Object>> {

    @Inject
    public InheritingController(LibraryService<Map<String, Object>> testService) {
        super(testService);
    }
}
