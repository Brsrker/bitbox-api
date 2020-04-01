package com.jarvi.bitboxapi.util;

import java.util.Date;

public class Period {

    private Date start;
    private Date end;

    public Period(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public boolean in(Date date) {
        return date.after(start) && date.before(end);
    }

}
