import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class _04_SalaryOver50000 {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        List<String> resList = entityManager.createQuery("SELECT e.firstName FROM Employee e WHERE e.salary > 50000", String.class).getResultList();
        resList.forEach(System.out::println);


        entityManager.getTransaction().commit();
    }
}
