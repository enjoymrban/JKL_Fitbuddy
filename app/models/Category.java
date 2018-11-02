package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name="category")
public class Category {
    @Id
    @SequenceGenerator(name="category_id_seq", sequenceName="category_id_seq",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="category_id_seq")
    private Long id;
    private String title;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch=FetchType.EAGER)
    @JoinTable(name="Favorite_Categories",
            joinColumns = @JoinColumn(name="category_id"),
            inverseJoinColumns = @JoinColumn(name="fitUser_id")
    )
    @JsonBackReference // siehe jackson bidirectional relationships and infinite recursion
    private Set<User> users = new HashSet<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<User> getUsers() { return users; }

    public void setUsers(Set<User> users) { this.users = users; }

    // eingefügt gemäss vladmihalcea.com artikel zu jpa und hibernate... bitte testen
    public void addUser(User user) {
        users.add(user);
        user.getCategories().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getCategories().remove(this);
    }
}