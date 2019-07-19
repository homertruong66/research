package com.rms.rms.common.dto;

import java.io.Serializable;
import java.util.List;

public class ProductViewDto implements Serializable {

    private static final long serialVersionUID = -3584749430907066837L;

    private ChannelDto channel;
    private List<CommissionDto> commissions;
    private ProductDto product;

    public ChannelDto getChannel() {
        return channel;
    }

    public void setChannel(ChannelDto channel) {
        this.channel = channel;
    }

    public List<CommissionDto> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<CommissionDto> commissions) {
        this.commissions = commissions;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductViewDto{" +
                "channel=" + channel +
                ", commissions=" + commissions +
                ", product=" + product +
                '}';
    }
}