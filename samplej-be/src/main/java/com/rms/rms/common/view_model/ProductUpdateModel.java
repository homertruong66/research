package com.rms.rms.common.view_model;

import org.springframework.web.util.HtmlUtils;

public class ProductUpdateModel {

    private String code;
    private String description;
    private String image;
    private String name;
    private Double price;

    public void escapeHtml() {
        this.code = HtmlUtils.htmlEscape(this.code, "UTF-8");
        this.description = HtmlUtils.htmlEscape(this.description, "UTF-8");
        this.image = HtmlUtils.htmlEscape(this.image, "UTF-8");
        this.name = HtmlUtils.htmlEscape(this.name, "UTF-8");
    }

    public String validate() {
        return null;
    }

    //
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return "ProductUpdateModel{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
