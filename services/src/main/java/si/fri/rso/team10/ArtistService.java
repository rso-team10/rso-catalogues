package si.fri.rso.team10;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequestScoped
public class ArtistService {

    @PersistenceContext
    private EntityManager em;

    public List<Artist> getArtists() {
        return em.createQuery("select artist from Artist artist", Artist.class).getResultList();
    }

    public Artist getArtist(Long id) {
        try {
            return em.createQuery("select artist from Artist artist where artist.id = :id", Artist.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        }
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

    public boolean deleteArtist(Artist artist) {
        try {
            beginTransaction();
            em.remove(artist);
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
