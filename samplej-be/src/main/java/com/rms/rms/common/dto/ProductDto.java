package com.rms.rms.common.dto;

import com.rms.rms.common.BaseDto;

import java.io.Serializable;

/**
 * homertruong
 */

public class ProductDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 6610635370057321704L;

    private String code;
    private Double commission;
    private String description;
    private String image;
    private String name;
    private Double price;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "code=" + code +
                ", commission=" + commission +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}