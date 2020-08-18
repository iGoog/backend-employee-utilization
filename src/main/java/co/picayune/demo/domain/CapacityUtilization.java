package co.picayune.demo.domain;

public class CapacityUtilization {
    private Employee employee;
    private boolean aggregate;
    private Long usage;
    private Long availability;
    private float utilization;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isAggregate() {
        return aggregate;
    }

    public void setAggregate(boolean aggregate) {
        this.aggregate = aggregate;
    }

    public Long getUsage() {
        return usage;
    }

    public void setUsage(Long usage) {
        this.usage = usage;
    }

    public Long getAvailability() {
        return availability;
    }

    public void setAvailability(Long availability) {
        this.availability = availability;
    }

    public float getUtilization() {
        return utilization;
    }

    public void setUtilization(float utilization) {
        this.utilization = utilization;
    }
}
