package _01;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "_01_wizards_deposits")
public class WizardDeposits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 60, nullable = false)
    private String lastName;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private int age;

    @Column(name = "magic_wand_creator", length = 100)
    private String magicWandCreator;

    @Column(name = "magic_wand_size", nullable = false)
    private short magicWandSize;

    @Column(name = "deposit_group", length = 20, nullable = false)
    private String depositGroup;

    @Column(name = "deposit_start_date", nullable = false)
    private LocalDate depositStartDate;

    @Column(name = "deposit_amount", nullable = false)
    private float depositAmount;

    @Column(name = "deposit_interest", nullable = false)
    private float depositInterest;

    @Column(name = "deposit_charge", nullable = false)
    private float depositCharge;

    @Column(name = "deposit_expiration_date", nullable = false)
    private LocalDate depositExpirationDate;

    @Column(name = "is_deposit_expired", nullable = false)
    private boolean isExpired;

    public WizardDeposits(){}

}
