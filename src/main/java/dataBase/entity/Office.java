package dataBase.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "OFFICES")
public class Office implements java.io.Serializable {

    private BigDecimal office;
    private Salesrep salesrep;
    private String city;
    private String region;
    private BigDecimal target;
    private BigDecimal sales;
    private Set<Salesrep> salesreps = new HashSet<>();

    public Office() {
    }

    public Office(BigDecimal office, String city, String region, BigDecimal sales) {
        this.office = office;
        this.city = city;
        this.region = region;
        this.sales = sales;
    }

    public Office(BigDecimal office, Salesrep salesrep, String city, String region, BigDecimal target,
                  BigDecimal sales, Set<Salesrep> salesreps) {
        this.office = office;
        this.salesrep = salesrep;
        this.city = city;
        this.region = region;
        this.target = target;
        this.sales = sales;
        this.salesreps = salesreps;
    }

    @Id
    @Column(name = "OFFICE", unique = true, nullable = false, precision = 22, scale = 0)
    public BigDecimal getOffice() {
        return this.office;
    }

    public void setOffice(BigDecimal office) {
        this.office = office;
    }

    @Column(name = "CITY", nullable = false, length = 40)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "REGION", nullable = false, length = 30)
    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Column(name = "TARGET", precision = 22, scale = 0)
    public BigDecimal getTarget() {
        return this.target;
    }

    public void setTarget(BigDecimal target) {
        this.target = target;
    }

    @Column(name = "SALES", nullable = false, precision = 22, scale = 0)
    public BigDecimal getSales() {
        return this.sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "office")
    public Set<Salesrep> getSalesreps() {
        return this.salesreps;
    }

    public void setSalesreps(Set<Salesrep> salesreps) {
        this.salesreps = salesreps;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("office").append("='").append(getOffice()).append("' ");
        buffer.append("city").append("='").append(getCity()).append("' ");
        buffer.append("region").append("='").append(getRegion()).append("' ");
        buffer.append("target").append("='").append(getTarget()).append("' ");
        buffer.append("sales").append("='").append(getSales()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }

    /*
    StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("office").append("='").append(getOffice()).append("' ");
        buffer.append("city").append("='").append(getCity()).append("' ");
        buffer.append("region").append("='").append(getRegion()).append("' ");
        buffer.append("mgr").append("='").append(getMgr()).append("' ");
        buffer.append("target").append("='").append(getTarget()).append("' ");
        buffer.append("sales").append("='").append(getSales()).append("' ");
        buffer.append("]");

        return buffer.toString();
     */
}