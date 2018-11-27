package si.fri.rso.team10;

import si.fri.rso.team10.configuration.ConfigurationProperties;
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
@Path("tracks")
public class TrackResource {

    @Inject
    private TrackService trackService;

    @Inject
    private ConfigurationProperties configProps;

    @GET
    public Response getTracks() {
        var tracks = trackService.getTracks().stream().map(TrackDTO::new).collect(Collectors.toList());
        return Response.ok(tracks).build();
    }

    @GET
    @Path("/most")
    public Response getMostPopularTrack() {
        var track = trackService.getMostPopularTrack();
        return Response.ok(new TrackDTO(track)).build();
    }

    @GET
    @Path("{trackId}")
    public Response getTrack(@PathParam("trackId") String id) {
        try {
            var track = trackService.getTrack(Long.parseLong(id));
            return Response.ok(new TrackDTO(track)).build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    public Response addTrack(Track track) {
        if (trackService.addEntity(track)) {
            return Response.ok(new TrackDTO(track)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{trackId}")
    public Response deleteTrack(@PathParam("trackId") String trackId) {
        try {
            var track = trackService.getTrack(Long.parseLong(trackId));
            if (track != null && trackService.deleteEntity(track)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("config")
    public Response getConfigValue() {
        return Response.ok("{\"value\": \"" + configProps.getStringProperty() + "\"}").build();
    }

    @POST
    @Path("/activate/{trackId}")
    public Response activateTrack(@PathParam("trackId") String trackId) {
        try {
            var track = trackService.getTrack(Long.parseLong(trackId));
            if (track != null) {
                track.setActive(true);
                trackService.mergeEntity(track);
                return Response.ok(new TrackDTO(track)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
