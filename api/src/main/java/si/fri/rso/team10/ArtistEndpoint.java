package si.fri.rso.team10;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("artists")
public class ArtistEndpoint {

    @Inject
    private CatalogueService catalogueService;

    @GET
    public Response getArtists() {
        List<Artist> artists = catalogueService.getArtists();

        return Response.ok(artists).build();
    }
}
