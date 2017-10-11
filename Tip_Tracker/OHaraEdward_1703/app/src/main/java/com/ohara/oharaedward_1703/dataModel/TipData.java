package com.ohara.oharaedward_1703.dataModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//Edward O'Hara
//MDV469 - 1703
//TipData
public class TipData implements Serializable {

    //stored properties
    public String selectedDate;
    public Long totalSales;
    public Long tipOutPercent;
    public Long cashTips;
    public Long creditCardTips;
    public Long hoursWorked;
    public String note;

    public double totalTips;
    public double totalPerHr;

    public String tipKey;


    //firebase default constructor
    public TipData(){
    }

    //constructor
    public  TipData(String dateSelected, Long totalSales, Long tipOutPercent, Long cashTips, Long creditCardTips, Long hoursWorked) {
        this.selectedDate = dateSelected;
        this.totalSales = totalSales;
        this.tipOutPercent = tipOutPercent;
        this.cashTips = cashTips;
        this.creditCardTips = creditCardTips;
        this.hoursWorked = hoursWorked;
    }

    //firebase object mapping for edit screen
    public Map<String, Object> toMap(){

        HashMap<String, Object> result = new HashMap<>();

        result.put("totalSales", this.totalSales);
        result.put("tipOutPercent", this.tipOutPercent);
        result.put("cashTips", this.cashTips);
        result.put("creditCardTips", this.creditCardTips);
        result.put("hoursWorked", this.hoursWorked);
        result.put("dateSelected", this.selectedDate);
        result.put("note", this.note);

        //return result
        return result;
    }

    @Override
    public String toString() {
        return selectedDate;
    }
}
