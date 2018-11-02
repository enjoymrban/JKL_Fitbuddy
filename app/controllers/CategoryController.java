package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Category;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;

import services.CategoryService;

import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;


public class CategoryController extends Controller {

    private final CategoryService categoryService;
    private final HttpExecutionContext ec;

    @Inject
    public CategoryController(CategoryService categoryService, HttpExecutionContext ec) {
        this.categoryService = categoryService;
        this.ec = ec;
    }

    public CompletionStage<Result> category() {
        return categoryService.get().thenApplyAsync(personStream -> {
            return ok(Json.toJson(personStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> getCategory(Long id) {
        return categoryService.get(id).thenApplyAsync(category -> {
            return ok(Json.toJson(category));
        }, ec.current());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> addCategory() {
        final JsonNode jsonRequest = request().body().asJson();
        final Category catToAdd = Json.fromJson(jsonRequest, Category.class);

        return categoryService.add(catToAdd).thenApplyAsync(category -> {
            return ok(Json.toJson(category));
        }, ec.current());

    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> updateCategory(Long id) {
        final JsonNode jsonRequest = request().body().asJson();
        final Category catToUpdate = Json.fromJson(jsonRequest, Category.class);
        catToUpdate.setId(id);
        return categoryService.update(catToUpdate).thenApplyAsync(box -> {
            return ok(Json.toJson(box));
        }, ec.current());
    }

    public CompletionStage<Result> deleteCategory(Long id) {
        return categoryService.delete(id).thenApplyAsync(removed -> {
            return removed ? ok() : internalServerError();
        }, ec.current());
    }
}
