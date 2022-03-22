package BillsPaymentSystem.Entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "billing_type", discriminatorType = DiscriminatorType.STRING)

public class BillingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "billingDetails")
    private User owner;

    public BillingDetail(){}

    public BillingDetail(User owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long number) {
        this.id = number;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
