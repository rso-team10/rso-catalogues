package si.fri.rso.team10;

import javax.persistence.EntityManager;

public abstract class AbstractService<T> {

    protected abstract EntityManager getEntityManager();

    public boolean addEntity(T entity) {
        try {
            beginTransaction();
            getEntityManager().persist(entity);
            commitTransaction();
            return true;
        } catch (Exception e) {
            rollbackTransaction();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean mergeEntity(T entity) {
        try {
            beginTransaction();
            getEntityManager().merge(entity);
            commitTransaction();
            return true;
        } catch (Exception e){
            rollbackTransaction();
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteEntity(T entity) {
        try {
            beginTransaction();
            getEntityManager().remove(entity);
            commitTransaction();
            return true;
        } catch (Exception e) {
            rollbackTransaction();
            System.out.println(e.getMessage());
            return false;
        }
    }

    protected void beginTransaction() {
        if (!getEntityManager().getTransaction().isActive())
            getEntityManager().getTransaction().begin();
    }

    protected void commitTransaction() {
        if (getEntityManager().getTransaction().isActive())
            getEntityManager().getTransaction().commit();
    }

    protected void rollbackTransaction() {
        if (getEntityManager().getTransaction().isActive())
            getEntityManager().getTransaction().rollback();
    }
}
