package models;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Provide JPA operations running inside of a thread pool sized to the connection pool
 */
public class CategoryRepository {

    private final JPAApi jpaApi;

    @Inject
    public CategoryRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public CompletionStage<Category> add(Category category) {
        return supplyAsync(() -> wrap(em -> insert(em, category)));
    }

    public CompletionStage<Category> update(Category category) {
        return supplyAsync(() -> wrap(em -> update(em, category)));
    }

    public CompletionStage<Category> find(Long id) {
        return supplyAsync(() -> wrap(em -> find(em, id)));
    }

    public CompletionStage<Boolean> remove(Long id) {
        return supplyAsync(() -> wrap(em -> remove(em, id)));
    }

    public CompletionStage<Stream<Category>> list() {
        return supplyAsync(() -> wrap(em -> list(em)));
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Category insert(EntityManager em, Category category) {
        em.persist(category);
        return category;
    }

    private Category update(EntityManager em, Category category) {
        Category categoryToUpdate = em.find(Category.class, category.getId());
        categoryToUpdate.setTitle(category.getTitle());
        categoryToUpdate.setUsers(category.getUsers());
        return categoryToUpdate;
    }

    private Category find(EntityManager em, Long id) {
        return em.find(Category.class, id);
    }

    private Boolean remove(EntityManager em, Long id) {
        Category category = em.find(Category.class, id);
        if(null != category) {
            em.remove(category);
            return true;

        }
        return false;
    }

    private Stream<Category> list(EntityManager em) {
        List<Category> categories = em.createQuery("select cat from category cat", Category.class).getResultList();
        return categories.stream();
    }
}