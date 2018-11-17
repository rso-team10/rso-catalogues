package si.fri.rso.team10.dto;

import si.fri.rso.team10.Artist;
import si.fri.rso.team10.ArtistType;
import si.fri.rso.team10.Gender;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistDTO extends BasicDTO {

    private String alias;
    private Date birthDate;
    private Date deathDate;
    private String name;
    private ArtistType artistType;
    private Gender gender;

    private List<String> members;
    private List<BasicDTO> tracks;

    public ArtistDTO(Artist artist) {
        super(artist);

        this.alias = artist.getAlias();
        this.birthDate = artist.getBirthDate();
        this.deathDate = artist.getDeathDate();
        this.name = artist.getName();
        this.artistType = artist.getArtistType();
        this.gender = artist.getGender();
        this.members = artist.getMembers();

        this.tracks = artist.getTracks().stream().map(BasicDTO::new).collect(Collectors.toList());
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArtistType getArtistType() {
        return artistType;
    }

    public void setArtistType(ArtistType artistType) {
        this.artistType = artistType;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<BasicDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<BasicDTO> tracks) {
        this.tracks = tracks;
    }
}
