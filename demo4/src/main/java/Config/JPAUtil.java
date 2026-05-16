package Config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf;

    private JPAUtil() {}

    private static EntityManagerFactory getEMF() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("week10PU");
            } catch (Exception e) {
                System.out.println("Error: Could not initialize JPA from persistence.xml");
                e.printStackTrace();
            }
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        EntityManagerFactory factory = getEMF();
        if (factory == null) {
            throw new IllegalStateException("Error: EntityManagerFactory is null. Check persistence.xml location.");
        }
        return factory.createEntityManager();
    }

    public static void closeEMF() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}