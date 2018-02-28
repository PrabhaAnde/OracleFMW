package ande.prabhas.org;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

public class Utils {
    public Utils() {
        super();
        
        System.out.println("--- Utils Class Loaded ---");
    }
    
    
    public static int calcAge(String dob) {
        
        Date date = convStringToDate(dob);
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        
        int age=0;
        
        birthDate.setTime(date);
        
        if(birthDate.after(today))
            throw new IllegalArgumentException("DOB cant be in future");
        
//        age = today.get(Calendar.YEAR)-birthDate.get(Calendar.YEAR);
        
        long diffInMillis = today.getTimeInMillis()-birthDate.getTimeInMillis();
        long diffInYears = (diffInMillis/(24*60*60*1000))/365;
        
        age =(int)diffInYears;
        return age;
    }
    
    private static Date convStringToDate(String inDate) {
        
        System.out.println("BDate::"+inDate);
        SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        
        try{
            date = sd.parse(inDate);
            System.out.println(date);
        }catch(ParseException e) {
            e.printStackTrace();
        }
        
        return date;
    }
    
    public static void main(String args[]) {
        System.out.println(calcAge("01/01/2000"));
    }
}
