package si.fri.rso.team10.dto;

import si.fri.rso.team10.Album;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumDTO extends BasicDTO {

    private String name;
    private BasicDTO artist;
    private Date releaseDate;

    private List<BasicDTO> tracks;

    public AlbumDTO(Album album) {
        super(album);

        this.name = album.getName();
        this.artist = new BasicDTO(album.getArtist());
        this.releaseDate = album.getReleaseDate();

        this.tracks = album.getTracks().stream().map(BasicDTO::new).collect(Collectors.toList());
    }

}
