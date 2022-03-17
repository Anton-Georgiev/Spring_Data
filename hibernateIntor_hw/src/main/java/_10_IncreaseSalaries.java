import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _10_IncreaseSalaries {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createQuery("UPDATE Employee e SET e.salary = e.salary * 1.12" +
                "WHERE e.department.id =  1 OR e.department.id = 2 OR e.department.id = 4 OR e.department.id = 11").executeUpdate();


        entityManager.getTransaction().commit();
    }
}
