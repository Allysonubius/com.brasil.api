package com.brasil.api.com.brasil.api.client.api;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CorretorasFeignClientFallback implements CorretorasFeignClient {
    @Override
    public List<Object> getCorretoras() {
        return Collections.emptyList();
    }
}
