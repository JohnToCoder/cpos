package com.cpos.classes.sales;

public class SalesDtl {
    private String salesBillNo;
    private String sku;
    private String size;
    private String proName;
    private String color;
    private String style;
    private String listPrice;
    private String curPrice;
    private String epc;
    private String gmtCreat;

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getGmtCreat() {
        return gmtCreat;
    }

    public void setGmtCreat(String gmtCreat) {
        this.gmtCreat = gmtCreat;
    }

    public String getSalesBillNo() {
        return salesBillNo;
    }

    public void setSalesBillNo(String salesBillNo) {
        this.salesBillNo = salesBillNo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(String curPrice) {
        this.curPrice = curPrice;
    }

}
