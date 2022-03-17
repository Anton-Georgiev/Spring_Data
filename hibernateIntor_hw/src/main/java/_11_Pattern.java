import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class _11_Pattern {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scr = new Scanner(System.in);
        String partOfName = scr.nextLine();

      //  String param = String.format(%);
        entityManager.createQuery("SELECT e FROM Employee e WHERE e.firstName LIKE :name", Employee.class)
        .setParameter("name",partOfName + "%").getResultList().stream().forEach(e -> System.out.println(e.getFirstName()));

        entityManager.getTransaction().commit();
    }
}
