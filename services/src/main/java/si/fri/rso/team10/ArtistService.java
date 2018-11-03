package si.fri.rso.team10;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequestScoped
public class ArtistService extends AbstractService<Artist>{

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

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
