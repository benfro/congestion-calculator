package net.benfro.concalc.web;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DateList {
    private List<String> dates = new ArrayList<>();

    public void addDate(String date) {
        dates.add(date);
    }

}
