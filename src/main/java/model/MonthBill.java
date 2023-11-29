package model;

public class MonthBill {

    public static double kgPrice=160;

    public static double getKgPrice() {
        return kgPrice;
    }

    public static void setKgPrice(double kgPrice) {
        MonthBill.kgPrice = kgPrice;
    }
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String year;
    private String month;

    private double carrySum;
    private double grossTeaSum;
    private double otherSum;
    private double WholeSum;

    //SUBS
    private double transportSub;
    private double teaSub;
    private double containerSub;
    private double fertilizerSub;
    private double otherSub;
    private double advanceSub;


    //SUMS
    private int[] kgs=new int[31];
    public int getKgAmount(){
        int tempSum=0;
        for(int i=0;i<31;i++){
             tempSum+= kgs[i];
        }
        return tempSum;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int[] getKgs() {
        return kgs;
    }

    public double getCarrySum() {
        return carrySum;
    }

    public void setCarrySum(double carrySum) {
        this.carrySum = carrySum;
    }

    public double getGrossTeaSum() {
        for(int i=0;i<31;i++){
            grossTeaSum += kgs[i]*kgPrice;
        }
        return grossTeaSum;
    }

    public void setGrossTeaSum(double grossTeaSum) {
        this.grossTeaSum = grossTeaSum;
    }

    public double getOtherSum() {
        return otherSum;
    }

    public void setOtherSum(double otherSum) {
        this.otherSum = otherSum;
    }

    public double getWholeSum() {
        return WholeSum;
    }

    public void setWholeSum(double wholeSum) {
        WholeSum = wholeSum;
    }

    public double getTransportSub() {
        return transportSub;
    }

    public void setTransportSub(double transportSub) {
        this.transportSub = transportSub;
    }

    public double getTeaSub() {
        return teaSub;
    }

    public void setTeaSub(double teaSub) {
        this.teaSub = teaSub;
    }

    public double getContainerSub() {
        return containerSub;
    }

    public void setContainerSub(double containerSub) {
        this.containerSub = containerSub;
    }

    public double getFertilizerSub() {
        return fertilizerSub;
    }

    public void setFertilizerSub(double fertilizerSub) {
        this.fertilizerSub = fertilizerSub;
    }

    public double getOtherSub() {
        return otherSub;
    }

    public void setOtherSub(double otherSub) {
        this.otherSub = otherSub;
    }

    public double getAdvanceSub() {
        return advanceSub;
    }

    public void setAdvanceSub(double advanceSub) {
        this.advanceSub = advanceSub;
    }


    public double getWholeSub() {
        double wholeSub=transportSub +teaSub +containerSub +fertilizerSub +otherSub +advanceSub;

        return wholeSub;
    }


    public double getBalance() {
       return getWholeSum()-getWholeSub();
    }

}
