package si.fri.rso.team10;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RequestScoped
public class ArtistService extends AbstractService<Artist> {

    @PersistenceContext
    private EntityManager em;

    @CircuitBreaker
    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "getArtistsFallback")
    public List<Artist> getArtists() {
        try {
            TimeUnit.SECONDS.sleep(5); // just for demo fault tolerance
            return em.createQuery("select artist from Artist artist", Artist.class).getResultList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Artist getArtist(Long id) {
        try {
            return em.createQuery("select artist from Artist artist where artist.id = :id", Artist.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Artist> getArtistsFallback() {
        Logger.getLogger(Artist.class.getName()).info("Fallback");
        return Collections.emptyList();
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
