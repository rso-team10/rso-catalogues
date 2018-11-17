package si.fri.rso.team10;

import si.fri.rso.team10.dto.AlbumDTO;
import si.fri.rso.team10.dto.ArtistDTO;
import si.fri.rso.team10.dto.TrackDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("artists")
public class ArtistResource {

    @Inject
    private ArtistService artistService;

    @Inject
    private AlbumService albumService;

    @Inject
    private TrackService trackService;

    @GET
    public Response getArtists() {
        var artists = artistService.getArtists();
        return Response.ok(artists).build();
    }

    @GET
    @Path("{artistId}")
    public Response getArtist(@PathParam("artistId") String artistId) {
        try {
            var artist = artistService.getArtist(Long.parseLong(artistId));
            return Response.ok(new ArtistDTO(artist)).build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{artistId}/albums")
    public Response getAlbumsByArtist(@PathParam("artistId") String artistId) {
        try {
            var albums = albumService.getAlbumByArtistId(Long.parseLong(artistId)).stream().map(AlbumDTO::new).collect(Collectors.toList());
            return Response.ok(albums).build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{artistId}/tracks")
    public Response getTracksByArtist(@PathParam("artistId") String artistId) {
        try {
            var tracks = trackService.getTracksByArtist(Long.parseLong(artistId)).stream().map(TrackDTO::new).collect(Collectors.toList());
            return Response.ok(tracks).build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    public Response addArtist(Artist artist) {
        if (artistService.addEntity(artist)) {
            return Response.ok(new ArtistDTO(artist)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{artistId}")
    public Response deleteArtist(@PathParam("artistId") String artistId) {
        try {
            var artist = artistService.getArtist(Long.parseLong(artistId));
            if (artist != null && artistService.deleteEntity(artist)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
