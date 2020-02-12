package org.chu.community.dto;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private List<Integer> pages;
    private Integer currentPage;
    private Integer totPage;

    public void setPagination(Integer totCount, int currentPage, int sizeOfOnePage) {
        int totalPage = totCount % sizeOfOnePage == 0 ? totCount / sizeOfOnePage : totCount / sizeOfOnePage + 1;
        this.totPage = totalPage;

        if (currentPage > totPage) currentPage = 1;
        this.currentPage = currentPage;

        pages = new LinkedList<>();
        for (int i = Math.max(currentPage - 3, 1); i <= Math.min(currentPage + 3, totalPage); i++)
            pages.add(i);
        // 是否展示上一页
        showPrevious = currentPage != 1;
        // 是否展示下一页
        showNext = currentPage != totalPage;
        // 是否展示第一页
        showFirstPage = !pages.contains(1);
        showEndPage = !pages.contains(totalPage);


    }
}
