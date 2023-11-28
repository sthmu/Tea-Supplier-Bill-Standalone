package model;

public class Record {
    private String custId;
    private String date;
    private double kg;
    private double teapacketCost;
    private double booksAndContainers;
    private double fertilizer;
    private double other;
    private double postage;
    private double arrears;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getKg() {
        return kg;
    }

    public void setKg(double kg) {
        this.kg = kg;
    }

    public double getTeapacketCost() {
        return teapacketCost;
    }

    public void setTeapacketCost(double teapacketCost) {
        this.teapacketCost = teapacketCost;
    }

    public double getBooksAndContainers() {
        return booksAndContainers;
    }

    public void setBooksAndContainers(double booksAndContainers) {
        this.booksAndContainers = booksAndContainers;
    }

    public double getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(double fertilizer) {
        this.fertilizer = fertilizer;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }

    public double getPostage() {
        return postage;
    }

    public void setPostage(double postage) {
        this.postage = postage;
    }

    public double getArrears() {
        return arrears;
    }

    public void setArrears(double arrears) {
        this.arrears = arrears;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public Record(String custId, String date, double kg, double teapacketCost, double booksAndContainers, double fertilizer, double other, double postage, double arrears, double debt) {
        this.custId = custId;
        this.date = date;
        this.kg = kg;
        this.teapacketCost = teapacketCost;
        this.booksAndContainers = booksAndContainers;
        this.fertilizer = fertilizer;
        this.other = other;
        this.postage = postage;
        this.arrears = arrears;
        this.debt = debt;
    }

    private double debt;

}
