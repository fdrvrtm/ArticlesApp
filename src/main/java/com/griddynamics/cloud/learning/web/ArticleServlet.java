package com.griddynamics.cloud.learning.web;

import com.griddynamics.cloud.learning.dao.domain.Article;
import com.griddynamics.cloud.learning.dao.domain.PaginatedResult;
import com.griddynamics.cloud.learning.service.ArticleService;
import com.griddynamics.cloud.learning.web.dto.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/articles")
public class ArticleServlet extends HttpServlet {

    private static final String EMPTY_STRING = "";
    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_ROWS_PER_PAGE = "20";

    private ArticleService articleService = new ArticleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArticleFilterRequest filter = getArticleRequestFilter(req);

            PaginatedResult<Article> articles = articleService.getArticles(filter);
            resp.getWriter().print(articles);
        } catch (IllegalArgumentException e) {
            resp.sendError(400, "Check request parameters for validity");
        }
    }

    private String getOrDefault(HttpServletRequest req, String param, String defaultValue) {
        return Objects.toString(req.getParameter(param), defaultValue);
    }

    private ArticleFilterRequest getArticleRequestFilter(HttpServletRequest req) {

        String caption = getOrDefault(req, "caption", EMPTY_STRING);
        String tag = getOrDefault(req, "tag", EMPTY_STRING);
        String type = getOrDefault(req, "type", PurchaseType.ALL.toString());

        PurchaseType purchaseType = PurchaseType.valueOf(type.toUpperCase());
        PaginatedRequestParams params = getPaginatedRequestParams(req);

        return new ArticleFilterRequest(params, purchaseType, caption, tag);
    }

    private PaginatedRequestParams getPaginatedRequestParams(HttpServletRequest req) {

        String page = getOrDefault(req, "page", DEFAULT_PAGE_NUMBER);
        String rows = getOrDefault(req, "rows", DEFAULT_ROWS_PER_PAGE);
        String sortColumn = getOrDefault(req, "sortColumn", SortColumn.DATE.toString());
        String direction = getOrDefault(req, "sortDirection", SortDirection.DESC.toString());

        Integer pageNumber = Integer.parseInt(page);
        Integer limitPerPage = Integer.parseInt(rows);
        SortColumn orderBy = SortColumn.valueOf(sortColumn.toUpperCase());
        SortDirection sortDirection = SortDirection.valueOf(direction.toUpperCase());

        return new PaginatedRequestParams(orderBy, sortDirection, pageNumber, limitPerPage);
    }
}