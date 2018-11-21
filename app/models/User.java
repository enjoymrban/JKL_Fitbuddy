package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import securesocial.core.BasicProfile;


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
    private String providerId;
    private String authUserId;

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
    @JsonIgnore
    private List<User> buddies;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER, mappedBy="buddies")
    private List<User> following = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER, mappedBy="interested")
    private List<Event> interestingEvents = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER, mappedBy="participants")
    private List<Event> participatingEvent = new ArrayList<>();

    // wird nicht in Datenbank gespeichert
    @Transient
    @JsonIgnore
    private BasicProfile profile;

    public User() {
        // standard constructor
    }

    public User(BasicProfile profile) {
        this.providerId = profile.providerId();
        this.authUserId  = profile.userId();
        this.profile = profile;
        if(profile.firstName().isDefined()) { firstName = profile.firstName().get(); }
        if(profile.lastName().isDefined())
            lastName  = profile.lastName().get();
        if(profile.fullName().isDefined())
            fullName = profile.fullName().get();
        if(profile.email().isDefined())
            email = profile.email().get();
        if(profile.avatarUrl().isDefined())
            avatarUrl = profile.avatarUrl().get();
    }

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

    public List<Event> getInterestingEvents() {
        return this.interestingEvents;
    }

    public void setInterestingEvents(List<Event> interestingEvents) { this.interestingEvents = interestingEvents; }

    public List<Event> getParticipatingEvent() {
        return this.participatingEvent;
    }

    public void setParticipatingEvent(List<Event> participatingEvent) { this.participatingEvent = participatingEvent; }

    public String getProviderId() { return providerId; }

    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getAuthUserId() { return this.authUserId; }

    public void setAuthUserId(String authUserId) { this.authUserId = authUserId; }

    public BasicProfile getProfile() { return profile; }

    public void setProfile(BasicProfile profile) { this.profile = profile; }
}
