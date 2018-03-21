package com.cpos.classes.goods;

public class GoodsCheckDtl {
    private String scbCode;
    private String epc;
    private String sku;
    private String size;
    private String gmtCreat;

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

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public String getScbCode() {
        return scbCode;
    }

    public void setScbCode(String scbCode) {
        this.scbCode = scbCode;
    }

    public String getGmtCreat() {
        return gmtCreat;
    }

    public void setGmtCreat(String gmtCreat) {
        this.gmtCreat = gmtCreat;
    }
}
