/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loblaw.provisioning;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Michael Watkins
 */
public class Computron {

    String[] phoneToStrip = {"-", ".", "(", ")"};//to remove valid non-numbers
    String[] postToStrip = {"-", " "};//to remove valid non-alphanums

    //tech info
    String name = "";//needs checked
    String techPhone = "";//needs checked
    String company = "";//needs checked
    String purchaseOrder = "";//can be anything
    //store info
    String storePhone = "";//needs checked
    String storeStreet = "";//can be almost anything
    String storeCity = "";//needs checked
    String storeProvince = "";//needs checked
    String storePostal = "";//needs checked
    //tags
    String bannerTag = "";//from a dropdown - no checking needed
    String configurationTag = "";//from a dropdown - no checking needed
    String connectionTag = "";//from a dropdown - no checking needed
    String iblocationTag = "";//from a dropdown - no checking needed
    String interactiveTag = "";//from a dropdown - no checking needed
    String languageTag = "";//from a dropdown - no checking needed
    String lOBTag = "";//needs checked
    String storeIDTag = "";//needs checked
    String locTypeTag = "";//from a dropdown - no checking needed
    String manufacturerTag = "";//from a dropdown - no checking needed
    String modelTag = "";//needs checked
    String orientationTag = "";//from a dropdown - no checking needed
    String ipAddressTag = "";//needs checked
    String serialTag = "";//needs checked                               TO DO
    String hostname = "";

    public Computron() {
        //silence is golden.
        //...

    }

    public ArrayList<String> setTechInfo(String tname, String tphone, String tcompany, String tpurchaseOrder) {
        ArrayList<String> errors = new ArrayList();
        ////////////////handle name
        boolean goodName = testName(tname);
        if (!goodName) {
            errors.add("Non-alpha, non-space character detected in name.");
        }
        name = tname;
        /////////////////end of name
        ////////handle phone
        String tempPhone = stripPhone(tphone);
        boolean goodPhone = testPhone(tempPhone);
        if (!goodPhone) {
            techPhone = tphone;
            errors.add("Phone number is not valid");
        } else {
            techPhone = formatPhone(tempPhone);
        }
        ////////////////end of phone
        ///////////////handle company
        boolean goodCompany = testName(tcompany);
        if (!goodCompany) {
            errors.add("Non-alpha, non-space character detected in Company.");
        }
        company = tcompany;
        //////////////end of company

        purchaseOrder = tpurchaseOrder;//we accept all non-evil values for purchase order.

        return errors;
    }

    public ArrayList<String> setStoreInfo(String sphone, String sstreet, String scity, String sprovince, String spostal) {
        ArrayList<String> errors = new ArrayList();

        ////////handle phone
        String tempPhone = stripPhone(sphone);
        boolean goodPhone = testPhone(tempPhone);
        if (!goodPhone) {
            storePhone = sphone;
            errors.add("Phone number is not valid");
        } else {
            storePhone = formatPhone(tempPhone);
        }
        ////////////////end of phone
        ///////////////handle city
        boolean goodCity = testName(scity);
        if (!goodCity) {
            errors.add("Non-alpha, non-space character detected in City.");
        }
        storeCity = scity;
        ////////////////end of city

        storeProvince = sprovince;//province comes from dropdown box, so I
        //dont have to test, amiriiiiiiight?

        storeStreet = sstreet;//we accept all non-evil values
        ////////handle postal
        String tempPost = stripPostal(spostal);
        String postError = testPostal(tempPost);
        if (postError.compareToIgnoreCase("~") != 0) {

            storePostal = sphone;
            errors.add(postError);
        } else {
            storePostal = formatPostal(tempPost);
        }
        ////////////////end of postal

        return errors;
    }

    public ArrayList<String> setTags(String banner, String config, String connectionType, String iBLoc, String interactive,
            String language, String lOB, String storeID, String locType,
            String manufacturer, String model, String orientation, String ip) {
        //jesus thats a lot of parameters 8O
        bannerTag = banner;
        configurationTag = config;
        connectionTag = connectionType;//from a dropdown - no checking needed
        iblocationTag = iBLoc;//from a dropdown - no checking needed
        interactiveTag = interactive;//from a dropdown - no checking needed
        languageTag = language;//from a dropdown - no checking needed
        lOBTag = lOB;//from a dropdown - no checking needed
        locTypeTag = locType;//from a dropdown - no checking needed
        manufacturerTag = manufacturer;//from a dropdown - no checking needed
        orientationTag = orientation;//from a dropdown - no checking needed

        ipAddressTag = ip;//needs checked
        storeIDTag = storeID;//needs checked
        modelTag = model;//needs checked

        ArrayList<String> errors = new ArrayList();
        errors = testIP(ip);
        errors.addAll(testStoreIDTag(storeIDTag));
        errors.addAll(testModelTag(model));

        return errors;
    }
    
    public void setHostname(String deviceNumber){
        hostname = getHostnamePrefix()+deviceNumber;
    }

    private String stripPhone(String phone) {
        String temp = phone;

        for (int j = 0; j < phoneToStrip.length; j++) {
            temp = temp.replace(phoneToStrip[j], "");
        }
        return temp;
    }

    private String formatPhone(String phone) {
        String temp = phone;
        temp = temp.substring(0, 3) + "-" + temp.substring(3, 6) + "-" + temp.substring(6);
        return temp;
    }

    private boolean testName(String name) {
        for (int i = 0; i < name.length(); i++) {
            int asciiValue = (int) name.charAt(i);
            if (asciiValue != 32) {//we want to permit spaces
                if (asciiValue < 65) {//lower bound for capital alphabet
                    return false;
                }
                if (asciiValue > 122) {//upper bound for lowercase
                    return false;
                }
                if (asciiValue > 90 && asciiValue < 97) {//gap between lowercase and uppercase
                    return false;
                }
            }
        }
        return true;
    }

    private boolean testPhone(String tempPhone) {
        if (tempPhone.length() != 10) {
            System.out.println("Phone Number wrong amount of digits");
            return false;
        }
        String t1 = tempPhone.substring(0, 5);
        String t2 = tempPhone.substring(5);

        try {
            int test = Integer.parseInt(t1);
            if (test < 0) {
                return false;
            }
            test = Integer.parseInt(t2);
            if (test < 0) {
                return false;
            }

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private String stripPostal(String spostal) {
        String temp = spostal;

        for (int j = 0; j < postToStrip.length; j++) {
            temp = temp.replace(postToStrip[j], "");
        }
        return temp;
    }

    private String testPostal(String tempPost) {
        if (tempPost.length() != 6) {
            return "Postal wrong amount of digits";
        }
        for (int i = 0; i < tempPost.length(); i++) {
            int asciiValue = (int) tempPost.charAt(i);
            if (i % 2 == 0) {//every other digit starting with first should be a letter
                if (asciiValue < 65 || asciiValue > 122 || (asciiValue > 90 && asciiValue < 97)) {//ascii values for chars
                    System.out.println(">9 <65");
                    return "Postal digit " + (i + 1) + " (" + tempPost.charAt(i) + ") " + " is not a valid letter";
                }

            } else {//every other character start with second should be number
                if (!(asciiValue >= 48 && asciiValue <= 57)) {//we want to permit 0-9 numbers
                    return "Postal digit " + (i + 1) + " (" + tempPost.charAt(i) + ") " + " is not a valid number";
                }
            }
        }

        return "~";
    }

    private String formatPostal(String tempPost) {
        tempPost = tempPost.substring(0, 3) + " " + tempPost.substring(3);
        System.out.println(tempPost);
        return tempPost;
    }

    private ArrayList<String> testIP(String ip) {
        ArrayList<String> errors = new ArrayList();
        //making sure there are exactly 3 periods, and that none of them are in a row:
        boolean prevWasPeriod = false;
        int numPeriods = 0;
        for (int i = 0; i < ip.length(); i++) {//look at IP one char at a time
            if (("" + ip.charAt(i)).compareToIgnoreCase(".") == 0) {//if its a .
                numPeriods++;//increment how many . we have seen
                if (prevWasPeriod) {//if previous char was also a .
                    errors.add("two \".\" in a row at slot " + (i + 1));//error!
                } else {//if previos char was not also a .
                    prevWasPeriod = true;//the current . now is considered the prev
                }
            } else {//if character was not a .
                prevWasPeriod = false;//current lack of . is now prev lack of
            }
        }
        if (numPeriods != 3) {//if we dont have exactly 3 periods, error!
            errors.add("Ip has " + numPeriods + " periods, it needs exactly 3");
        }
        if (errors.size() > 0) {//if ip format is bad
            return errors;//we want to break before attempting to split octets
        }

        //breaking down into octets
        String firstOctet = ip.substring(0, ip.indexOf("."));
        String rest = ip.substring(ip.indexOf(".") + 1);
        boolean goodFirst, goodSecond, goodThird, goodFourth = false;
        try {
            int first = Integer.parseInt(firstOctet);
            if(first<0||first>255){//unnecessary for now, but might need later
                errors.add(first + " is out of octet range (0-255)");
            } else if(first!=172){
                errors.add("Loblaw IPs should start with 172, yours starts with:" + first);
            }
        } catch (Exception e) {
            errors.add("first octet \"" + firstOctet + "\" is not a valid int");
        }

        String secondOctet = rest.substring(0, rest.indexOf("."));
        rest = rest.substring(rest.indexOf(".") + 1);
        try {
            int second = Integer.parseInt(secondOctet);
            if(second!=23){
                errors.add("Second octet of IP should be 23, you entered:" + second);
            } else if(second<0||second>255){
                errors.add("second octet \"" + secondOctet + "\" is not a valid int");
            }
        } catch (Exception e) {
            errors.add("second Octet \"" + secondOctet + "\" is not a valid int");
        }

        String thirdOctet = rest.substring(0, rest.indexOf("."));
        rest = rest.substring(rest.indexOf(".") + 1);
        try {
            int third = Integer.parseInt(thirdOctet);
            if(third<0||third>255){
                errors.add("third octet \"" + thirdOctet + "\" is not a valid int");
            }
        } catch (Exception e) {
            errors.add("third Octet \"" + thirdOctet + "\" is not a valid int");
        }

        String fourthOctet = rest;
        try {
            int fourth = Integer.parseInt(fourthOctet);
            if(fourth<0||fourth>255){
                errors.add("fourth octet \"" + fourthOctet + "\" is not a valid int");
            }
        } catch (Exception e) {
            errors.add("fourth Octet \"" + fourthOctet + "\" is not a valid int");
        }

        return errors;
    }


    private ArrayList<String> testStoreIDTag(String storeIDTag) {
        ArrayList<String> errors = new ArrayList();
 
        for (int i = 0; i < storeIDTag.length(); i++) {
            int asciiValue = (int) storeIDTag.charAt(i);
                    //is letter                                   is number                                 is -
                if (!((asciiValue >= 65 && asciiValue <= 122) || (asciiValue >= 48 && asciiValue <= 57)||asciiValue == 45||asciiValue==32)) {
                    errors.add("Store ID " + (i + 1) + " (" + storeIDTag.charAt(i) + ") " + " is not a valid letter, number,-, or space");
                }
            
        }

        return errors;
    }

    private ArrayList<String> testModelTag(String model) {
        ArrayList<String> errors = new ArrayList();
        //right now, we except pretty much anything as the model
        return errors;
    }

    public String getCSVString() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Calendar cal = Calendar.getInstance();
        String dateTag = dateFormat.format(cal.getTime());
        //oh god the horra
        String tagNames = "properties.system.hostname,resolve,"
                + "properties.system.ipAddress,properties.system.mac,"
                + "proxy,outproxy,properties.system.opstate,device.serial,"
                + "type.type,type.brand,type.series,type.model,"
                + "system.module.version,login,password,location.level1,"
                + "location.level2,location.level3,location.level4,"
                + "location.level5,location.zip,latitude,longitude,ocreceiver,"
                + "ocsender,properties.category,properties.system.sec.hostname,"
                + "properties.system.sec.ipAddress,properties.system.sec.mac,"
                + "Banner,Configuration,Connection Type,In-Building Location,"
                + "Interactive,Language,Line of Business,Location ID,"
                + "Location Type,MP_Manufacturer,MP_Manufacturer_Model,"
                + "NOC Monitor,Orientation,Provisioned_Date";
        String nl = "\n";
        String c = ",";
        //bohica
        String values = hostname + c + "false" + c + ipAddressTag + c
                + c + c + c + "Running" +c+ serialTag + c + "Digital Signage Network Player" + c
                + "Stratacache" + c + "Spectra 200" + c + "S-200-W7" + c + c + c + c
                + "North America" + c + "Canada" + c + storeProvince + c + storeCity + c
                + storeStreet + c + storePostal + c + "0.0" + c + "0.0" + c + "true" + c + "false" + c
                + c + c + c + c + bannerTag + c + configurationTag + c + connectionTag + c
                + iblocationTag + c + interactiveTag + c + languageTag + c + lOBTag + c
                + storeIDTag + c + locTypeTag + c + manufacturerTag + c + modelTag + c
                + "Yes" + c + orientationTag+c+dateTag;

        return tagNames + nl + values;
    }
    
    public String getHostnamePrefix(){
        String leadingZeros = "";
        int numZeros = 5-storeIDTag.length();
        for(int i=0;i<numZeros;i++){
            leadingZeros = leadingZeros+"0";
        }
        return leadingZeros+storeIDTag+"_"+"DS"+"_"+iblocationTag+"_";
    }
    public String getHostname(){
        return hostname;
    }
}
