package si.fri.rso.team10;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequestScoped
public class AlbumService extends AbstractService<Album>{

    @PersistenceContext()
    private EntityManager em;

    public List<Album> getAlbums() {
        return em.createQuery("select album from Album album", Album.class).getResultList();
    }

    public Album getAlbum(Long id) {
        try {
            return em.createQuery("select album from Album album where album.id = :id", Album.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Album> getAlbumByArtistId(Long id) {
        return em.createQuery("select album from Album album where album.artist.id = :id", Album.class).setParameter("id", id).getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
