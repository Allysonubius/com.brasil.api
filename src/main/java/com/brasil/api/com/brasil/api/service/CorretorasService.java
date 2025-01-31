package com.brasil.api.com.brasil.api.service;

import com.brasil.api.com.brasil.api.client.api.CorretorasFeignClient;
import com.brasil.api.com.brasil.api.model.PagedResponse;
import com.brasil.api.com.brasil.api.utils.PaginationUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.List;

@Service
public class CorretorasService {
    private static final Logger logger = LoggerFactory.getLogger(CorretorasService.class);
    private final CorretorasFeignClient corretorasFeignClient;
    private List<Object> cachedCorretoras;
    private boolean isDataLoaded = false;

    public CorretorasService(CorretorasFeignClient corretorasFeignClient) {
        this.corretorasFeignClient = corretorasFeignClient;
    }

    public PagedResponse<Object> getAllCorretoras(int page, int size) {
        try {
            if (!isDataLoaded) {
                cachedCorretoras = this.corretorasFeignClient.getCorretoras();
                isDataLoaded = true;
            }

            return PaginationUtils.paginate(cachedCorretoras, page, size);
        } catch (Exception e) {
            logger.error("Erro ao buscar corretoras: " + e.getMessage());
            return new PagedResponse<>(0, 0, page, size, Collections.emptyList());
        }
    }
}






