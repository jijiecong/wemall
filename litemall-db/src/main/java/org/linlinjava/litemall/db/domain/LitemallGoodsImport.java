package org.linlinjava.litemall.db.domain;

import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;

/**
 * @author jijiecong
 * @version 1.0
 * @date 2023/5/28 14:09
 * @description TODO
 */
public class LitemallGoodsImport {

    @ExcelProperty(value = "商品名称", index = 0)
    private String name;

    @ExcelProperty(value = "商品编码", index = 1)
    private String goodsSn;

    @ExcelProperty(value = "商品分类", index = 2)
    private String categoryName;

    @ExcelProperty(value = "商品划线价", index = 3)
    private BigDecimal price;

    @ExcelProperty(value = "商品售价", index = 4)
    private BigDecimal counterPrice;

    @ExcelProperty(value = "商品库存", index = 5)
    private Integer stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCounterPrice() {
        return counterPrice;
    }

    public void setCounterPrice(BigDecimal counterPrice) {
        this.counterPrice = counterPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
