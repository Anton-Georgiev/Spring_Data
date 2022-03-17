import entities.Town;

import javax.persistence.*;
import java.util.List;

public class _02_changeCasing {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        Query queryResult = entityManager.createQuery("SELECT t FROM Town t", Town.class);
        List<Town> resList = queryResult.getResultList();

        for (Town town : resList) {
            String name = town.getName();
            if(name.length() <= 5){
                 town.setName(name.toUpperCase());
                 entityManager.persist(town);
            }
        }



        entityManager.getTransaction().commit();
    }
}
