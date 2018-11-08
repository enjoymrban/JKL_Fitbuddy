package models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "fitUser")
public class User {

    @Id
    @SequenceGenerator(name = "fitUser_id_seq", sequenceName = "fitUser_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "fitUser_id_seq")
    private long id;
    private String description;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String avatarUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Favorite_Categories",
            joinColumns = @JoinColumn(name = "fitUser_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Buddies",
            joinColumns = @JoinColumn(name = "fitUser_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "buddy_id", referencedColumnName = "id"))
    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonSerialize(using = CustomListSerializer.class)
    @JsonDeserialize(using = CustomListDeserializer.class)
    //private List<User> buddies = new ArrayList<>();
    private List<User> buddies;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER, mappedBy="buddies")
    private List<User> following = new ArrayList<>();

    /*// siehe jpa many to many relashionship causing infinite recursion auf stack overflow
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "Buddies",
            joinColumns = @JoinColumn(name = "fitUser_id"),
            inverseJoinColumns = @JoinColumn(name = "buddy_id")
    )
    @JsonManagedReference
    private List<User> buddies = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "buddies")
    @JsonBackReference
    private List<User> following = new ArrayList<>();*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<User> getBuddies() {
        return buddies;
    }

    public void setBuddies(List<User> buddies) {
        this.buddies = buddies;
    }

    public List<User> getFollowing() {
        return this.following;
    }

    public void setFollowing(List<User> following) { this.following = following; }
}
