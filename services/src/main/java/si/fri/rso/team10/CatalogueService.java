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

    public boolean addArtist(Artist artist) {
        try {
            beginTransaction();
            em.persist(artist);
            commitTransaction();
            return true;
        } catch (Exception e) {
            rollbackTransaction();
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void beginTransaction() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTransaction() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTransaction() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
