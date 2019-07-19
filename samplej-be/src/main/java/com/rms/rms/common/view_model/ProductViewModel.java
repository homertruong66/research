package com.rms.rms.common.view_model;

import com.rms.rms.common.util.MyDateUtil;

import java.util.Date;

public class ProductViewModel {

    private Date fromDate;
    private Date toDate;

    public String validate() {
        String errors = "";

        if (fromDate != null && toDate != null && fromDate.after(toDate)) {
            errors += "from_date [" + MyDateUtil.getYYMMDDString(fromDate) + "] can't be after " +
                      "to_date [" + MyDateUtil.getYYMMDDString(toDate) + "]! && ";
        }

        return errors.equals("") ? null : errors;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "ProductViewModel{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
