package com.salmon.test.framework.helpers.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Developer on 12/08/2016.
 */
public class StringHelper {

    public static String returnEmptyStringIfNull(Object realValue){

        if(realValue == null){
            return null;
        }else{
            return realValue.toString();
        }
    }

    /**
     * Get the formatted date from the date string in the format of HH:MM
     * @param date
     * @return
     * @throws Exception
     */
    public static String getHourMinuteFromDate(String date) throws Exception{


        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date formattedDate = format.parse(date);
        Timestamp ts = new Timestamp(formattedDate.getTime());

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts.getTime());
        String result = StringUtils.leftPad(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)), 2, '0') +
                ":" +
                StringUtils.leftPad(String.valueOf(cal.get(Calendar.MINUTE)), 2, '0');
        return result;
    }

    public static long getTime(String date) throws Exception{


        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date formattedDate = format.parse(date);
        Timestamp ts = new Timestamp(formattedDate.getTime());

        return ts.getTime();
    }

    /**
     * Format the time to include 0 if hours or minures are less then 10
     * @param hour
     * @param minute
     * @return Strinf with the formatted time
     * @throws Exception
     */
    public static String getFormattedTime(String hour, String minute) throws Exception{

        String formattedTime = "";
        if(hour!=null && hour.length() == 1){
            hour = "0"+hour;
        }
        if(minute!=null && minute.length() == 1){
            minute = "0"+minute;
        }
        if(hour != null && minute != null){
            formattedTime = hour + ":" + minute;
        }else {
            formattedTime = null;
        }

        return formattedTime;
    }

}
