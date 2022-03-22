package BillsPaymentSystem.Entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("bank_account")
public class BankAccountBilling extends BillingDetail{

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "swift_code")
    private long swiftCode;


    public BankAccountBilling(){}

    public BankAccountBilling(User owner, String bankName, long swiftCode) {
        super(owner);
        this.bankName = bankName;
        this.swiftCode = swiftCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public long getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(long swiftCode) {
        this.swiftCode = swiftCode;
    }
}
