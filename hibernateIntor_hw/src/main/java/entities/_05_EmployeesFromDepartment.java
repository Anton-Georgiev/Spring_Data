package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class _05_EmployeesFromDepartment {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Employee> resultList = entityManager.createQuery("SELECT e FROM Employee e" +
                " WHERE e.department.id = 6" +
                "ORDER BY e.salary ASC, e.id ASC", Employee.class).getResultList();
        resultList.stream().forEach(e -> {
                    String format = String.format("%s %s from %s - $%f",e.getFirstName(),e.getLastName(),e.getDepartment().getName(),e.getSalary());
            System.out.println(format);
        });


        entityManager.getTransaction().commit();
    }
}
