package com.brasil.api.com.brasil.api.model;

import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(description = "Resposta paginada para a listagem de usuários")
public class PagedResponse<T> {

    private long totalItems;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private List<T> content;

    public PagedResponse( long totalItems, int totalPages, int currentPage, int pageSize,List<T> content) {

        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.content = content;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}




