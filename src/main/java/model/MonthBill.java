package model;

import db.DBConnection;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class MonthBill {
    private static Connection conn = DBConnection.getConnection();
    public static double kgPrice = 160;

    public MonthBill(String id, int year, int month) throws SQLException {
        this.id = id;
        this.year = year;
        this.month = month;
        setFertilizerSub();
    }

    public static double getKgPrice() {
        return kgPrice;
    }

    public static void setKgPrice(double kgPrice) {
        MonthBill.kgPrice = kgPrice;
    }

    private String id;
    private int year;
    private int month;


    //SUMS
    private int[] kgs = new int[31];
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getKgAmount() {
        int tempSum = 0;
        for (int i = 0; i < 31; i++) {
            tempSum += kgs[i];
        }
        return tempSum;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
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
        for (int i = 0; i < 31; i++) {
            grossTeaSum += kgs[i] * kgPrice;
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

    public void setFertilizerSub() throws SQLException {
        //the customer wont pay the fertilizer debt at the same month he takes the debt, therefore we have to take the sum of fertilizer debt from the last months
        //if the customer took the debt in the last month then he would pay one part this month and the second part next month.
        for (int i = 1; i < 3; i++) {
            String dateString = "" + year + "-" + month;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

            // Parse the string to a YearMonth object
            YearMonth originalYearMonth = YearMonth.parse(dateString, formatter);

            // Get the YearMonth before the original date
            YearMonth yearMonthBefore = originalYearMonth.minusMonths(i);


            String sql = "SELECT fertilizer FROM records  WHERE YEAR(date)=" + yearMonthBefore.getYear() + " AND MONTH(date)=" + yearMonthBefore.getMonthValue() + " AND id='" + id + "'";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            System.out.println(sql);
            if (result.next()) {

                this.fertilizerSub += result.getDouble("fertilizer") / 2;
            }
        }
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

    public double getWholeSum() {

        System.out.println(grossTeaSum + "sdadasd");
        WholeSum = getCarrySum() + getGrossTeaSum() + getOtherSum();

        return WholeSum;

    }

    public double getWholeSub() {
        double wholeSub = transportSub + teaSub + containerSub + fertilizerSub + otherSub + advanceSub;

        return wholeSub;
    }

    public double getBalance() {
        return getWholeSum() - getWholeSub();
    }


    public static void printBill(MonthBill bill) throws SQLException, IOException, InvalidFormatException {
        Customer customer = new Customer(bill.getId());
        FileInputStream fis = new FileInputStream(new File("src/main/resources/invoice/invoice.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);


        XSSFCell phoneCell = sheet.getRow(0).getCell(1);
        XSSFCell idCell = sheet.getRow(1).getCell(1);
        XSSFCell nameCell = sheet.getRow(2).getCell(1);
        XSSFCell kgCell = sheet.getRow(4).getCell(1);
        XSSFCell monthCell = sheet.getRow(2).getCell(8);
        XSSFCell carrySumCell = sheet.getRow(6).getCell(2);
        XSSFCell grossTeaSumCell = sheet.getRow(7).getCell(2);
        XSSFCell otherSumCell = sheet.getRow(8).getCell(2);
        XSSFCell wholeSumCell = sheet.getRow(9).getCell(2);
        XSSFCell transportSubCell = sheet.getRow(11).getCell(2);
        XSSFCell teaSubCell = sheet.getRow(12).getCell(2);
        XSSFCell containerSubCell = sheet.getRow(13).getCell(2);
        XSSFCell advanceSubCell = sheet.getRow(14).getCell(2);
        XSSFCell fertilizerSubCell = sheet.getRow(15).getCell(2);
        XSSFCell otherSubCell = sheet.getRow(16).getCell(2);
        XSSFCell wholeSubCell = sheet.getRow(17).getCell(2);
        XSSFCell balanceCell = sheet.getRow(18).getCell(2);

        XSSFCell billIndexCell = sheet.getRow(4).getCell(5);

        // phoneCell.setCellType(CellType.STRING);
        phoneCell.setCellValue(customer.getPhone());
        idCell.setCellValue(customer.getId());
        nameCell.setCellValue(customer.getName());
        kgCell.setCellValue(bill.getKgAmount());
        monthCell.setCellValue(new DateFormatSymbols().getMonths()[bill.getMonth() - 1]);
        carrySumCell.setCellValue(bill.getCarrySum());
        grossTeaSumCell.setCellValue(bill.getGrossTeaSum());
        otherSumCell.setCellValue(bill.getOtherSum());
        wholeSumCell.setCellValue(bill.getWholeSum());
        transportSubCell.setCellValue(bill.getTransportSub());
        teaSubCell.setCellValue(bill.getTeaSub());
        containerSubCell.setCellValue(bill.getContainerSub());
        advanceSubCell.setCellValue(bill.getAdvanceSub());
        fertilizerSubCell.setCellValue(bill.getFertilizerSub());
        otherSubCell.setCellValue(bill.getOtherSub());
        wholeSubCell.setCellValue(bill.getWholeSub());
        balanceCell.setCellValue(bill.getBalance());
        billIndexCell.setCellValue("බිල් අංකය : " + bill.getYear() + bill.getMonth() + bill.getId());

        int i = 0;
        int row = 7;
        int col = 4;
        XSSFCell tableKgCell;
        while (i < 31) {
            row = 7;
            for (; (row <= 15 && i < 31); i++, row++) {
                tableKgCell = sheet.getRow(row).getCell(col);
                tableKgCell.setCellValue(bill.getKgs()[i]);
            }
            col += 2;

            System.out.println("this is the table date now inserting"+i);
        }


        FileOutputStream fileOut = new FileOutputStream("src/main/resources/invoice/" + bill.getYear() + bill.getMonth() + bill.getId() + ".xlsx");
        workbook.write(fileOut);

        System.out.println("Id column in Excel is updated successfully");
        fileOut.close();

        // Closing the workbook
        workbook.close();


    }
}