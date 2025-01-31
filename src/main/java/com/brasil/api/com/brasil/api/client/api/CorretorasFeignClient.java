package com.brasil.api.com.brasil.api.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "brasilapi", url = "https://brasilapi.com.br", fallback = CorretorasFeignClientFallback.class)
public interface CorretorasFeignClient {
    @GetMapping("/api/cvm/corretoras/v1")
    List<Object> getCorretoras();
}
