package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name="category")
public class Category {
    @Id
    @SequenceGenerator(name="category_id_seq", sequenceName="category_id_seq",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="category_id_seq")
    private Long id;
    private String title;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER, mappedBy="categories")
    private List<User> users = new ArrayList<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}