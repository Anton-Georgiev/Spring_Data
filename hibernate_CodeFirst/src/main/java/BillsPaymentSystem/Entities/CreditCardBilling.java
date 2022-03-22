package BillsPaymentSystem.Entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("credit_card")
public class CreditCardBilling extends BillingDetail{

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "expiration_month")
    private LocalDate expirationMonth;

    @Column(name = "expiration_year")
    private LocalDate expirationYear;

    public CreditCardBilling() {
    }

    public CreditCardBilling(User owner) {
        super(owner);

    }

    public CreditCardBilling(String cardType, LocalDate expirationMonth, LocalDate expirationYear) {
        this.cardType = cardType;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }
}
