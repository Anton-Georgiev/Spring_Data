import entities.Employee;
import entities.Town;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class _03_checkEmployeeExists {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        Scanner scr = new Scanner(System.in);
        String name = scr.nextLine();
        String lastName = scr.nextLine();

        long employeeCount = entityManager.createQuery("SELECT COUNT(e) FROM Employee e WHERE e.firstName = :name AND" +
                " e.lastName = :lastName", Long.class)
                .setParameter("name", name)
                .setParameter("lastName", lastName).getSingleResult();

        if(employeeCount > 0) {
            System.out.println("yes");
        } else System.out.println("no");

        entityManager.getTransaction().commit();
    }
}
