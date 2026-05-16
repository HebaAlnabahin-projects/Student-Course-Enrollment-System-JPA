package DAO;

import Config.JPAUtil;
import Models.Enrollment;
import Models.EnrollmentId;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class EnrollmentDAO {

    public List<Enrollment> getAllEnrollments() throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Enrollment> query = em.createQuery("SELECT e FROM Enrollment e", Enrollment.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void addEnrollment(Enrollment enrollment) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(enrollment);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void updateEnrollment(Enrollment enrollment) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(enrollment);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteEnrollment(int studentId, int courseId) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            EnrollmentId id = new EnrollmentId(studentId, courseId);
            Enrollment enrollment = em.find(Enrollment.class, id);

            if (enrollment != null) {
                em.remove(enrollment);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}