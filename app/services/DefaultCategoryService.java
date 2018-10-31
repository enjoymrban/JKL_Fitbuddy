package services;

import models.Category;
import models.CategoryRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;


public class DefaultCategoryService implements CategoryService {
    private CategoryRepository categoryRepository;

    @Inject
    public DefaultCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CompletionStage<Stream<Category>> get() {
        return categoryRepository.list();
    }

    @Override
    public CompletionStage<Category> get(Long id) {
        return categoryRepository.find(id);
    }

    @Override
    public CompletionStage<Boolean> delete(Long id) {
        return categoryRepository.remove(id);
    }

    @Override
    public CompletionStage<Category> update(Category updatedCategory) {
        return categoryRepository.update(updatedCategory);
    }

    @Override
    public CompletionStage<Category> add(Category category) {
        return categoryRepository.add(category);
    }

}
