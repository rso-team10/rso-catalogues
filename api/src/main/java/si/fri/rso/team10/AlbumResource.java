package si.fri.rso.team10;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("albums")
public class AlbumResource {

    @Inject
    private AlbumService albumService;

    @GET
    public Response getAlbums() {
        List<Album> albums = albumService.getAlbums();
        return Response.ok(albums).build();
    }

    @GET
    @Path("{albumId}")
    public Response getAlbum(@PathParam("albumId") String id) {
        try {
            var album = albumService.getAlbum(Long.parseLong(id));
            return Response.ok(album).build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    public Response addAlbum(Album album) {
        if (albumService.addEntity(album)) {
            return Response.ok(album).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{albumId}")
    public Response deleteAlbum(@PathParam("albumId") String albumId) {
        try {
            var album = albumService.getAlbum(Long.parseLong(albumId));
            if (album != null && albumService.deleteEntity(album)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
