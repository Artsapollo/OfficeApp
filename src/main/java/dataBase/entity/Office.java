package dataBase.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "OFFICES", schema = "MA_STUDENT")
public class Office implements java.io.Serializable {

    @Id
    @Column(name = "OFFICE", unique = true, nullable = false, precision = 22, scale = 0)
    private BigDecimal office;

    @Column(name = "CITY", nullable = false, length = 40)
    private String city;

    @Column(name = "REGION", nullable = false, length = 30)
    private String region;

    @Column(name = "TARGET", precision = 22, scale = 0)
    private BigDecimal target;

    @Column(name = "SALES", nullable = false, precision = 22, scale = 0)
    private BigDecimal sales;

//    @OneToMany(fetch = FetchType.LAZY)
//    private Set<Salesrep> salesreps = new HashSet<>();

    public Office() {
    }

    public Office(BigDecimal office, String city, String region, java.math.BigDecimal sales) {
        this.office = office;
        this.city = city;
        this.region = region;
        this.sales = sales;
    }

    public Office(BigDecimal office, String city, String region, java.math.BigDecimal target,
                  java.math.BigDecimal sales, Set<Salesrep> salesreps) {
        this.office = office;
        this.city = city;
        this.region = region;
        this.target = target;
        this.sales = sales;
//        this.salesreps = salesreps;
    }


    public BigDecimal getOffice() {
        return office;
    }

    public void setOffice(BigDecimal office) {
        this.office = office;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public java.math.BigDecimal getTarget() {
        return this.target;
    }

    public void setTarget(java.math.BigDecimal target) {
        this.target = target;
    }

    public java.math.BigDecimal getSales() {
        return this.sales;
    }

    public void setSales(java.math.BigDecimal sales) {
        this.sales = sales;
    }

//    public Set<Salesrep> getSalesreps() {
//        return this.salesreps;
//    }
//
//    public void setSalesreps(Set<Salesrep> salesreps) {
//        this.salesreps = salesreps;
//    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office office1 = (Office) o;
        return Objects.equals(office, office1.office) &&
                Objects.equals(city, office1.city) &&
                Objects.equals(region, office1.region) &&
                Objects.equals(target, office1.target) &&
                Objects.equals(sales, office1.sales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(office, city, region, target, sales);
    }
}