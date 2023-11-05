public class SaleRecord {
    private String date;
    private String salesPerson;
    private String customerName;
    private String carMake;
    private String carModel;

    private int carYear;
    private double salePrice;
    private double comissionRate;
    private double comissionEarned;

    public SaleRecord(String date, String salePerson, String customerName, String carMake, String carModel, int carYear,
            double salePrice, double comissionRate, double comissionEarned) {
        this.date = date;
        this.salesPerson = salePerson;
        this.customerName = customerName;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carYear = carYear;
        this.salePrice = salePrice;
        this.comissionRate = comissionRate;
        this.comissionEarned = comissionEarned;

    }

    public String getSalePerson() {
        return salesPerson;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public String getDate() {
        return date;
    }

    public int carYear() {
        return carYear;
    }

    public double getCommissionRate() {
        return comissionRate;
    }

    public String getModel() {
        return carModel;
    }

    public String getMake() {
        return carMake;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String toString() {
        return salesPerson + ", " + customerName + ", " + carMake + ", " + carModel + ", " + carYear + ", " + salePrice
                + ", " + comissionRate + ", " + comissionEarned;
    }

}
