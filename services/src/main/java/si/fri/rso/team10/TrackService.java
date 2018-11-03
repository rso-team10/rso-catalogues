package si.fri.rso.team10;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

public class TrackService extends AbstractService<Track> {

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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
