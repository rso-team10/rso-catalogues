package si.fri.rso.team10;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequestScoped
public class CatalogueService {

    @PersistenceContext
    private EntityManager em;

    public List<Artist> getArtists() {
        return em.createQuery("select artist from Artist artist").getResultList();
    }
}
