package ande.prabhas.org;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ApplicationContextManager {
        private static ThreadLocal<ApplicationContext> globalAppContext = new ThreadLocal<ApplicationContext>() {
                    protected synchronized ApplicationContext initialValue() {
                            return new ApplicationContext();
                    }
            };
            

            public static void setString(String key, String value) {
                    if (globalAppContext.get().mapStrings != null)
                            globalAppContext.get().mapStrings.put(key, value);
            }

            public static String getString(String key) {
                    if (globalAppContext.get().mapStrings != null && globalAppContext.get().mapStrings.get(key) != null) {
                            return globalAppContext.get().mapStrings.get(key);
                    } else
                            return null;
            }
            
            public static void performAgeCalc() {
                Iterator<String> iter=globalAppContext.get().mapStrings.keySet().iterator();
                                        
                for(;iter.hasNext();) {
                    String key = iter.next();
                    int age = calcAge(globalAppContext.get().mapStrings.get(key));
                    globalAppContext.get().mapStrings.put(key, age+"");
                }
            }
            
            //Warning : Execution of reset is must when you are using the appcontext over XSLs.The stored objects will not be GCed and will hog the heap space if the instance load is more by the day
            public static void reset() {                    
                    globalAppContext.get().mapStrings.clear();
            }
            
            public static String printData() {
                StringBuffer sbf = new StringBuffer();
                Iterator<String> iter=globalAppContext.get().mapStrings.keySet().iterator();
                                        
                for(;iter.hasNext();) {
                    String key = iter.next();
                    sbf.append("<ns1:Key>").append(key).append("</ns1:Key>");
                    sbf.append("<ns1:Value>").append(globalAppContext.get().mapStrings.get(key)).append("</ns1:Value>");
                }
                return sbf.toString();
            }
            
            public static void main(String args[]) {
                globalAppContext.get().mapStrings.put("ID1", "Value1");
                globalAppContext.get().mapStrings.put("ID2", "Value2");
                globalAppContext.get().mapStrings.put("ID3", "Value3");
                
                System.out.println(printData());
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
                    
                }catch(ParseException e) {
                    e.printStackTrace();
                }
                
                return date;
            }
}
