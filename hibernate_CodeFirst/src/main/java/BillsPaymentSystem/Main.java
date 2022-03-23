package BillsPaymentSystem;

import BillsPaymentSystem.Entities.BankAccountBilling;
import BillsPaymentSystem.Entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("CodeFirstEx");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        User user = new User("Anton","Georgiev", "email@gmail.com", "1234");
        BankAccountBilling billing = new BankAccountBilling(user, "revo", 123415);
        user.setBillingDetails(billing);
        entityManager.persist(billing);
        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
