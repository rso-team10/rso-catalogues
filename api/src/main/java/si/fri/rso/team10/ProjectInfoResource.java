package si.fri.rso.team10;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("project")
public class ProjectInfoResource {

    @GET
    @Path("info")
    public Response getProjectInfo() {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setClani(Arrays.asList("nk3007", "ak6689"));
        projectInfo.setOpis_projekta("Naš projekt je namenjen pretoku (streaming) glasbenih posnetkov.");
        projectInfo.setMikrostoritve(Arrays.asList("http://localhost:8081/v1/artists", "http://localhost:8081/v1/albums", "http://localhost:8081/v1/tracks", "http://localhost:8082/v1/listen", "http://localhost:8082/v1/listen/most"));
        projectInfo.setGithub(Arrays.asList("https://github.com/rso-team10/rso-catalogues","https://github.com/rso-team10/rso-stats", "https://github.com/rso-team10/rso-webapp"));
        projectInfo.setTravis(Arrays.asList("https://travis-ci.org/rso-team10/rso-stats", "https://travis-ci.org/rso-team10/rso-catalogues"));
        projectInfo.setDockerhub(Arrays.asList("https://hub.docker.com/r/kozeljko/stats/", "https://hub.docker.com/r/kozeljko/catalogues/"));

        return Response.ok(projectInfo).build();
    }
}
