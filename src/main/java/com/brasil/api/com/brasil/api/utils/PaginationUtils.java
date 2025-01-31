package com.brasil.api.com.brasil.api.utils;

import com.brasil.api.com.brasil.api.model.PagedResponse;

import java.util.Collections;
import java.util.List;

public class PaginationUtils {

    private PaginationUtils() {}

    public static <T> PagedResponse<T> paginate(List<T> items, int page, int size) {
        if (items == null || items.isEmpty()) {
            return new PagedResponse<>(0, 0, page, size, Collections.emptyList());
        }

        int totalItems = items.size();
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalItems);

        if (startIndex >= totalItems) {
            return new PagedResponse<>(totalItems, 0, page, size, Collections.emptyList());
        }

        List<T> paginatedItems = items.subList(startIndex, endIndex);

        int totalPages = totalItems / size;

        return new PagedResponse<>(totalItems, totalPages, page, size, paginatedItems);
    }
}

