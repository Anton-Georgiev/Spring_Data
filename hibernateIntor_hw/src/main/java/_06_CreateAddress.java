import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class _06_CreateAddress {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Scanner scr = new Scanner(System.in);
        Address newAddress = new Address();
        newAddress.setText("Vitoshka 15");
        entityManager.persist(newAddress);

        String lastName = scr.nextLine();

        Employee employee = entityManager.createQuery("SELECT e FROM Employee e WHERE e.lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName).getSingleResult();

        employee.setAddress(newAddress);
        entityManager.persist(employee);

        entityManager.getTransaction().commit();
    }
}
