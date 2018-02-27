package com.cpos.classes.sales;

public class SalesBill {
    private String salesBillNo;
    private String totalPrice;
    private String totalNum;
    private String storeCode;
    private String empCode;
    private String empName;
    private String gmtCreat;

    public String getSalesBillNo() {
        return salesBillNo;
    }

    public void setSalesBillNo(String salesBillNo) {
        this.salesBillNo = salesBillNo;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getGmtCreat() {
        return gmtCreat;
    }

    public void setGmtCreat(String gmtCreat) {
        this.gmtCreat = gmtCreat;
    }
}
