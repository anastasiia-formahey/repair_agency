package com.formahei.service;

import javax.servlet.http.HttpServletRequest;

public class Pagination {
    public static final int RECORDS_PER_PAGE = 5;

    public static void setPagination(HttpServletRequest request, int currentPage, int rows, String orderBy) {
        int nOfPages = rows / RECORDS_PER_PAGE;

        if (nOfPages % RECORDS_PER_PAGE > 0 && rows % RECORDS_PER_PAGE != 0) {
            nOfPages += 1;
        }
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);
        request.setAttribute("orderBy", orderBy);
    }
    public static int getInt(String currentPageParam) {
        int current;
        try {
            current = Integer.parseInt(currentPageParam);
        } catch (NumberFormatException e) {
            current = -1;
        }
        return current;
    }
    public static int getCurrentPage(String currentPageParam) {
        return (currentPageParam == null || getInt(currentPageParam) <= 1) ? 1 : getInt(currentPageParam);
    }
}
