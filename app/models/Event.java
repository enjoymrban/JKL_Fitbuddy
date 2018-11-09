package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="event")
public class Event {

    @Id
    @SequenceGenerator(name="event_id_seq", sequenceName="event_id_seq",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="event_id_seq")
    private long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    private String description;
    private String date;
    private int nrOfPlayers;
    private long coordinateX;
    private long coordinateY;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Interested",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fitUser_id", referencedColumnName = "id"))
    private List<User> interested = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Participants",
            joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fitUser_id", referencedColumnName = "id"))
    private List<User> participants = new ArrayList<>();

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }

    public User getCreator() { return creator; }

    public void setCreator(User creator) { this.creator = creator; }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNrOfPlayers() {
        return nrOfPlayers;
    }

    public void setNrOfPlayers(int nrOfPlayers) {
        this.nrOfPlayers = nrOfPlayers;
    }

    public long getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(long coordinateX) {
        this.coordinateX = coordinateX;
    }

    public long getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(long coordinateY) {
        this.coordinateY = coordinateY;
    }

    public List<User> getInterested() {
        return this.interested;
    }

    public void setInterested(List<User> interested) { this.interested = interested; }

    public List<User> getParticipants() {
        return this.participants;
    }

    public void setParticipants(List<User> participants) { this.participants = participants; }
}
