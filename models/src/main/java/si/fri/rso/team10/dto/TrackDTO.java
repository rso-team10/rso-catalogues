package si.fri.rso.team10.dto;

import si.fri.rso.team10.Track;

import java.util.Date;

public class TrackDTO extends BasicDTO {

    private BasicDTO artist;
    private BasicDTO album;
    private String name;
    private String genre;
    private Date releaseDate;
    private boolean active;

    public TrackDTO(Track track) {
        super(track);

        this.artist = new BasicDTO(track.getArtist());
        this.album = new BasicDTO(track.getAlbum());
        this.name = track.getName();
        this.genre = track.getGenre();
        this.releaseDate = track.getReleaseDate();
        this.active = track.getActive();
    }

    public BasicDTO getArtist() {
        return artist;
    }

    public void setArtist(BasicDTO artist) {
        this.artist = artist;
    }

    public BasicDTO getAlbum() {
        return album;
    }

    public void setAlbum(BasicDTO album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
