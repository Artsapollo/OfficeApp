package springData.dto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class OfficeRequest {
    @Min(value = 100, message = "1")
    @Max(value = 200, message = "1")
    @NotNull(message = "6")
    private BigDecimal office;

    @Size(min = 4, max = 10, message = "2")
    @NotNull(message = "6")
    private String city;

    @Size(min = 4, max = 10, message = "3")
    @NotNull(message = "6")
    private String region;

    @Valid
    @NotNull(message = "6")
    private OfficeDetails officeDetails;

    public BigDecimal getOffice() {
        return office;
    }

    public void setOffice(BigDecimal office) {
        this.office = office;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public OfficeDetails getOfficeDetails() {
        return officeDetails;
    }

    public void setOfficeDetails(OfficeDetails officeDetails) {
        this.officeDetails = officeDetails;
    }

    @Override
    public String toString() {
        return "OfficeRequest{" +
                "office=" + office +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", officeDetails=" + officeDetails +
                '}';
    }
}