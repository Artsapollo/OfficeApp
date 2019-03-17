package dataBase.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMERS")
public class Customer implements java.io.Serializable {

    private BigDecimal custNum;
    private Salesrep salesrep;
    private String company;
    private BigDecimal creditLimit;
    private Set<Order> orders = new HashSet<>();

    public Customer() {
    }

    public Customer(BigDecimal custNum) {
        this.custNum = custNum;
    }

    public Customer(BigDecimal custNum, Salesrep salesrep, String company, BigDecimal creditLimit,
                    Set<Order> orders) {
        this.custNum = custNum;
        this.salesrep = salesrep;
        this.company = company;
        this.creditLimit = creditLimit;
        this.orders = orders;
    }

    @Id
    @Column(name = "CUST_NUM", unique = true, nullable = false, precision = 22, scale = 0)
    public BigDecimal getCustNum() {
        return this.custNum;
    }

    public void setCustNum(BigDecimal custNum) {
        this.custNum = custNum;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUST_REP")
    public Salesrep getSalesrep() {
        return this.salesrep;
    }

    public void setSalesrep(Salesrep salesrep) {
        this.salesrep = salesrep;
    }

    @Column(name = "COMPANY", length = 40)
    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "CREDIT_LIMIT", precision = 22, scale = 0)
    public BigDecimal getCreditLimit() {
        return this.creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}