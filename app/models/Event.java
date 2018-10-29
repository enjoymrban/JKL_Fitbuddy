package models;

import javax.persistence.*;

@Entity(name="event")
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private String date;
    private int nrOfPlayers;
    private long coordinateX;
    private long coordinateY;


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

    public long getGetCoordinateY() {
        return coordinateY;
    }

    public void setGetCoordinateY(long coordinateY) {
        this.coordinateY = coordinateY;
    }

}
