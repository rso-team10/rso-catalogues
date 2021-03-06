package si.fri.rso.team10;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import si.fri.rso.team10.dto.TrackCount;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequestScoped
public class TrackService extends AbstractService<Track> {

//    @Inject
//    @DiscoverService(value = "rso-stats", version = "1.0.x", environment = "dev")
//    private WebTarget statsService;

    @PersistenceContext
    private EntityManager em;

    public List<Track> getTracks() {
        return em.createQuery("select track from Track track", Track.class).getResultList();
    }

    public Track getTrack(Long id) {
        try {
            return em.createQuery("select track from Track track where track.id = :id", Track.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Track> getTracksByArtist(Long artistId) {
        return em.createQuery("select track from Track track where track.artist.id = :id", Track.class).setParameter("id", artistId).getResultList();
    }

    public List<Track> getTracksByAlbum(Long albumId) {
        return em.createQuery("select track from Track track where track.album.id = :id", Track.class).setParameter("id", albumId).getResultList();
    }

    public Track getMostPopularTrack() {
        var httpClient = HttpClient.newBuilder().build();
//        var httpRequest = HttpRequest.newBuilder(statsService.getUriBuilder().path("/v1/listen/most").build()).build();
        var httpRequest = HttpRequest.newBuilder(URI.create("http://stats:8082/v1/listen/most")).build();
        try {
            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            var trackCount = new ObjectMapper().readValue(httpResponse.body(), TrackCount.class);
            var track = getTrack(trackCount.getTrackId());

            return track;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @CircuitBreaker(requestVolumeThreshold = 2)
    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "getTrackFallBack")
    public Track getFirstTrack() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return em.find(Track.class, 1);
    }

    public Track getTrackFallBack() {
        return new Track();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
