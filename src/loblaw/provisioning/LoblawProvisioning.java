/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loblaw.provisioning;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * @author Michael Watkins
 */
public class LoblawProvisioning {

    //for input checking:
    static String[] badChars = {"\"", "\\", ";", ":", "\'", "/", "`"};

    //combobox options:
    static String[] provinces = {"","AB", "BC", "MB", "NB", "NL", "NS",
        "NT", "NU", "ON", "PE", "QC", "SK", "YT"};
    static String[] bannerOptions
            = {"","Dominion", "Extra Foods", "Fortinos", "Loblaws", "Maxi", "Nofrills",
                "Provigo", "Save Easy", "Superstore", "Valu-mart", "YIG", "Zehrs Market"};
    static String[] configOptions
            = {"","Array 2x2", "Array 4x4", "Audio only",
                "Menu Board", "Single Screen"};
    static String[] wiredOptions = {"","Wired", "Wireless"};
    static String[] locationOptions
            = {"","ABM", "Aisle Display", "Colleague Staff room", "CFR (Conference room)",
                "End Cap", "Health and Beauty", "Hot Meals Ready", "Hub",
                "Island Aisle Display", "Kiosk", "Lane", "Optical", "Pet",
                "Pharmacy", "Rate Board"};
    static String[] interactiveOptions = {"","No", "Yes"};
    static String[] languageOptions = {"","English", "French"};
    static String[] locTypeOptions
            = {"","2 alpha", "Distribution Centre",
                "Gas Bar", "Office", "Store"};
    static String[] manufacturerOptions = {"","Lenovo", "Samsung", "Stratacache"};
    static String[] orientationOptions = {"","Portrait", "Landscape"};
    static String[] provinceOptions
            = {"","AB (Alberta)", "BC (British Columbia)", "MB (Manitoba)",
                "NB (New Brunswick)", "NL (Newfoundland and Labrador)",
                "NS (Nova Scotia)", "NT (Northwest Territories)", "NU (Nunavut)",
                "ON (Ontario)", "PE (Prince Edward Island)", "QC (Quebec)",
                "SK (Saskatchewan)", "YT (Yukon)"};

    ///default choices for tag dropdowns:
    static int tagBanner = 0;
    static int tagConfig = 0;
    static int tagConnectionType = 0;
    static int tagInBuildingLocation = 0;
    static int tagInteractive = 0;
    static int tagLanguage = 0;
    static int tagLocType = 0;
    static int tagManufacturer = 0;
    static int tagOrientation = 0;

    //tech infos
    static String tName = "";
    static String tPhone = "";
    static String tCompany = "BFG";
    static String poNum = "N/A";

    //store infos
    static String sPhone = "";
    static String sStreet = "";
    static String sCity = "";
    static String sProvince = "";
    static String sPostal = "";
    static int storeProvince = 0;
    
    
    //tag infos
    static String tagLineOfBusiness = "PCF";
    static String tagStoreID = "";
    static String tagMPModel = "TinyPC";
    static String tagIPAddress = "172.23.";

    static JTextField poNumBox;//this one is very special.

    //global containers:
    static JPanel canvas;
    static JFrame frame;
    //configuration settings:
    static int panelWidth = 500;
    static int panelHeight = 500;

    static Computron device;//device to create
    static String currentCanvas = "tech";//state manager

    static ArrayList<JComponent> entries;

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        //configureLibrary();
        //getResolution();
        frame = new JFrame("Loblaw Provisioning Tool");
        device = new Computron();
        buildCanvas();
    }

    private static void buildCanvas() {
        killCanvas();
        canvas = new JPanel();
        entries = new ArrayList();
        makePanel(currentCanvas);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(panelWidth, panelHeight);
        frame.setResizable(false);//lock window size
        frame.setContentPane(canvas);
        frame.setVisible(true);
        canvas.requestFocusInWindow();
    } 

    private static void makeTechPanel() {
        //set our window dimensions:
        panelWidth = 300;
        panelHeight = 150;

        canvas.setLayout(new GridLayout(5, 1));
        JPanel nameLine = new JPanel(new GridLayout(1, 2));
        JPanel techPhoneLine = new JPanel(new GridLayout(1, 2));
        JPanel techCompanyLine = new JPanel(new GridLayout(1, 2));
        JPanel purchaseOrderLine = new JPanel(new GridLayout(1, 2));
        JPanel buttonLine = new JPanel(new GridLayout(1, 2));

        JLabel namelbl = new JLabel("Tech Name:");
        JLabel phonelbl = new JLabel("Tech Phone:");
        JLabel techCompanylbl = new JLabel("Tech Company:");
        JLabel purhcaseOrderlbl = new JLabel("Tech Purchase Order #:");

        JTextField nameBox = new JTextField(tName);
        nameBox.setColumns(10);
        entries.add(nameBox);

        JTextField phoneBox = new JTextField(tPhone);
        phoneBox.setColumns(10);
        entries.add(phoneBox);

        JTextField companyBox = new JTextField(tCompany);
        // companyBox.setColumns(10);
        //companyBox.setForeground(defaultText);
        entries.add(companyBox);

        poNumBox = new JTextField(poNum);
        poNumBox.setColumns(10);
        //entries.add(poNumBox); //we don't actually validate this input
        //so there is no need to test it in entries
        //just use the poNum variable.

        JButton clearButton = new JButton("CLEAR");

        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                clearButtonPressed();
            }

        });

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                continueButtonPressed();
            }

        });
        //pack that shit up
        nameLine.add(namelbl);
        nameLine.add(nameBox);

        techPhoneLine.add(phonelbl);
        techPhoneLine.add(phoneBox);

        techCompanyLine.add(techCompanylbl);
        techCompanyLine.add(companyBox);

        purchaseOrderLine.add(purhcaseOrderlbl);
        purchaseOrderLine.add(poNumBox);

        buttonLine.add(clearButton);
        buttonLine.add(continueButton);
        //add it to our canvas:
        canvas.add(nameLine);
        canvas.add(techPhoneLine);
        canvas.add(techCompanyLine);
        canvas.add(purchaseOrderLine);
        canvas.add(buttonLine);

    }

    private static void makeStorePanel() {
        panelWidth = 380;
        panelHeight = 200;

        canvas.setLayout(new GridLayout(7, 1));
        JPanel titleLine = new JPanel(new GridLayout(1, 1));
        JPanel storePhoneLine = new JPanel(new GridLayout(1, 2));
        JPanel storeStreetLine = new JPanel(new GridLayout(1, 2));
        JPanel storeCityLine = new JPanel(new GridLayout(1, 2));
        JPanel storeProvinceLine = new JPanel(new GridLayout(1, 2));
        JPanel storePostalLine = new JPanel(new GridLayout(1, 2));
        JPanel buttonLine = new JPanel(new GridLayout(1, 2));

        JLabel storelbl = new JLabel("Store Information", SwingConstants.CENTER);
        JLabel phonelbl = new JLabel("Phone #:");
        JLabel streetlbl = new JLabel("Street Address:");
        JLabel citylbl = new JLabel("City:");
        JLabel provincelbl = new JLabel("State/Province:");
        JLabel postallbl = new JLabel("Postal Code:");

        JTextField phoneBox = new JTextField(sPhone);
        phoneBox.setColumns(10);
        entries.add(phoneBox);

        JTextField addressBox = new JTextField(sStreet);
        addressBox.setColumns(10);
        entries.add(addressBox);

        JTextField cityBox = new JTextField(sCity);
        entries.add(cityBox);

/////////////////
        JComboBox provinceBox = new JComboBox(provinceOptions);
        provinceBox.setSelectedIndex(storeProvince);
        entries.add(provinceBox);
/////////////////

        JTextField postalBox = new JTextField(sPostal);
        entries.add(postalBox);

        JButton clearButton = new JButton("CLEAR");

        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                clearButtonPressed();
            }

        });

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                continueButtonPressed();
            }

        });

        titleLine.add(storelbl);

        storePhoneLine.add(phonelbl);
        storePhoneLine.add(phoneBox);

        storeStreetLine.add(streetlbl);
        storeStreetLine.add(addressBox);

        storeCityLine.add(citylbl);
        storeCityLine.add(cityBox);

        storeProvinceLine.add(provincelbl);
        storeProvinceLine.add(provinceBox);

        storePostalLine.add(postallbl);
        storePostalLine.add(postalBox);

        buttonLine.add(clearButton);
        buttonLine.add(continueButton);

        canvas.add(titleLine);
        canvas.add(storePhoneLine);
        canvas.add(storeStreetLine);
        canvas.add(storeCityLine);
        canvas.add(storeProvinceLine);
        canvas.add(storePostalLine);
        canvas.add(buttonLine);
    }

    private static void makeTagsPanel() {
        canvas.setLayout(new GridLayout(14, 1));
        panelWidth = 300;
        panelHeight = 425;
//////////////////////////////////////////////////////////////////////////////
        //dropdowns:
        JPanel bannerLine = new JPanel(new GridLayout(1, 2));//Banner tag
        JLabel bannerlbl = new JLabel("Banner:");
        JComboBox bannerBox = new JComboBox(bannerOptions);
        bannerBox.setSelectedIndex(tagBanner);
        bannerLine.add(bannerlbl);
        bannerLine.add(bannerBox);
        entries.add(bannerBox);

//////////////////////////////////////////////////////////////////////////////
        JPanel configLine = new JPanel(new GridLayout(1, 2));//Configuration tag
        JLabel configlbl = new JLabel("Configuration:");
        JComboBox configBox = new JComboBox(configOptions);
        configBox.setSelectedIndex(tagConfig);
        configLine.add(configlbl);
        configLine.add(configBox);
        entries.add(configBox);

//////////////////////////////////////////////////////////////////////////////
        JPanel wiredLine = new JPanel(new GridLayout(1, 2));//Connection Type tag
        JLabel wiredlbl = new JLabel("Connection Type:");
        JComboBox wiredBox = new JComboBox(wiredOptions);
        wiredBox.setSelectedIndex(tagConnectionType);
        wiredLine.add(wiredlbl);
        wiredLine.add(wiredBox);
        entries.add(wiredBox);

//////////////////////////////////////////////////////////////////////////////       
        JPanel locationLine = new JPanel(new GridLayout(1, 2));//In-Building Location tag
        JLabel locationlbl = new JLabel("In-Building Location:");
        JComboBox locationBox = new JComboBox(locationOptions);
        locationBox.setSelectedIndex(tagInBuildingLocation);
        locationLine.add(locationlbl);
        locationLine.add(locationBox);
        entries.add(locationBox);

//////////////////////////////////////////////////////////////////////////////       
        JPanel interactiveLine = new JPanel(new GridLayout(1, 2));//Interactive tag
        JLabel interactivelbl = new JLabel("Interactive:");
        JComboBox interactiveBox = new JComboBox(interactiveOptions);
        interactiveBox.setSelectedIndex(tagInteractive);
        interactiveLine.add(interactivelbl);
        interactiveLine.add(interactiveBox);
        entries.add(interactiveBox);

//////////////////////////////////////////////////////////////////////////////       
        JPanel languageLine = new JPanel(new GridLayout(1, 2));//Language tag
        JLabel languagelbl = new JLabel("Language:");
        JComboBox languageBox = new JComboBox(languageOptions);
        languageBox.setSelectedIndex(tagLanguage);
        languageLine.add(languagelbl);
        languageLine.add(languageBox);
        entries.add(languageBox);

//////////////////////////////////////////////////////////////////////////////       
        //Strings:
        JPanel businessLine = new JPanel(new GridLayout(1, 2));//Line of Business tag
        JLabel businesslbl = new JLabel("Line of Business");
        JTextField businessBox = new JTextField(tagLineOfBusiness);
        businessLine.add(businesslbl);
        businessLine.add(businessBox);
        entries.add(businessBox);

//////////////////////////////////////////////////////////////////////////////        
        JPanel idLine = new JPanel(new GridLayout(1, 2));//Location ID tag
        JLabel idlbl = new JLabel("Store ID #:");
        JTextField idBox = new JTextField(tagStoreID);
        idLine.add(idlbl);
        idLine.add(idBox);
        entries.add(idBox);

//////////////////////////////////////////////////////////////////////////////       
        //dropdowns:
        JPanel locTypeLine = new JPanel(new GridLayout(1, 2));//Location Type tag
        JLabel locTypelbl = new JLabel("Location Type:");
        JComboBox locTypeBox = new JComboBox(locTypeOptions);
        locTypeBox.setSelectedIndex(tagLocType);
        locTypeLine.add(locTypelbl);
        locTypeLine.add(locTypeBox);
        entries.add(locTypeBox);

//////////////////////////////////////////////////////////////////////////////
        JPanel manufacturerLine = new JPanel(new GridLayout(1, 2));//MP Manufacturer tag
        JLabel manufacturerlbl = new JLabel("MP Manufacturer:");
        JComboBox manufacturerBox = new JComboBox(manufacturerOptions);
        manufacturerBox.setSelectedIndex(tagManufacturer);
        manufacturerLine.add(manufacturerlbl);
        manufacturerLine.add(manufacturerBox);
        entries.add(manufacturerBox);

//////////////////////////////////////////////////////////////////////////////  
        //String
        JPanel modelLine = new JPanel(new GridLayout(1, 2));//MP Model tag
        JLabel modellbl = new JLabel("MP Model:");
        JTextField modelBox = new JTextField(tagMPModel);
        modelLine.add(modellbl);
        modelLine.add(modelBox);
        entries.add(modelBox);

//////////////////////////////////////////////////////////////////////////////
        //dropdown
        JPanel orientationLine = new JPanel(new GridLayout(1, 2));//Orientation tag
        JLabel orientationlbl = new JLabel("Orientation:");
        JComboBox orientationBox = new JComboBox(orientationOptions);
        orientationBox.setSelectedIndex(tagOrientation);
        orientationLine.add(orientationlbl);
        orientationLine.add(orientationBox);
        entries.add(orientationBox);

////////////////////////////////////////////////////////////////////////////// 
        //String
        JPanel ipLine = new JPanel(new GridLayout(1, 2));//MP Model tag
        JLabel iplbl = new JLabel("IP address:");
        JTextField ipBox = new JTextField(tagIPAddress);
        ipLine.add(iplbl);
        ipLine.add(ipBox);
        entries.add(ipBox);

//////////////////////////////////////////////////////////////////////////////
        //adding button line
        JPanel buttonLine = new JPanel(new GridLayout(1, 2));
        JButton clearButton = new JButton("CLEAR");

        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                clearButtonPressed();
            }

        });

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                continueButtonPressed();
            }

        });
        buttonLine.add(clearButton);
        buttonLine.add(continueButton);

        //adding lines to canvas
        canvas.add(bannerLine);
        canvas.add(configLine);
        canvas.add(wiredLine);
        canvas.add(locationLine);
        canvas.add(interactiveLine);
        canvas.add(languageLine);
        canvas.add(businessLine);
        canvas.add(idLine);
        canvas.add(locTypeLine);
        canvas.add(manufacturerLine);
        canvas.add(modelLine);
        canvas.add(orientationLine);
        canvas.add(ipLine);
        canvas.add(buttonLine);

    }

    private static void clearButtonPressed() {
        System.out.println("CLEAR");
        clearStateFields();
    }

    private static void continueButtonPressed() {
        if (currentCanvas.compareToIgnoreCase("tech") == 0) {

            handleTechContinue();
        } else if (currentCanvas.compareToIgnoreCase("store") == 0) {

            handleStoreContinue();
        } else if (currentCanvas.compareToIgnoreCase("tags") == 0) {
            handleTagsContinue();
        } else {
            System.out.println("ERROR! BAD STATE CODE,"
                    + " BAD! BAD STATE CODE, BAD!");
            killCanvas();
            return;
        }
        buildCanvas();
    }

    private static void makePanel(String key) {
        if (key.compareToIgnoreCase("tech") == 0) {
            makeTechPanel();
        } else if (key.compareToIgnoreCase("store") == 0) {
            makeStorePanel();
        } else if (key.compareToIgnoreCase("tags") == 0) {
            makeTagsPanel();
        }
    }

    private static void killCanvas() {
        if (canvas != null) {
            canvas.removeAll();
            frame.dispose();
        }
    }

    private static void clearStateFields() {
        System.out.println("CLEAR");
        entries = new ArrayList();
        buildCanvas();
    }

    private static void handleTechContinue() {
        System.out.println("checking tech inputs");
        ArrayList<String> errors = checkInvalidChars();
        boolean badChars = false;
        if (errors.size() > 0) {
            displayErrors(errors);
            badChars = true;
        }

        tName = ((JTextField) entries.get(0)).getText();
        tPhone = ((JTextField) entries.get(1)).getText();
        tCompany = ((JTextField) entries.get(2)).getText();
        poNum = poNumBox.getText();
        if(badChars){
            return;
        }

        errors = device.setTechInfo(tName, tPhone, tCompany, poNum);
        if (errors.size() > 0) {
            displayErrors(errors);
            return;
        }
        currentCanvas = "store";

    }

    private static void handleStoreContinue() {
        ArrayList<String> errors = checkInvalidChars();
        boolean badChars = false;//if their input is flagged by standard check
        if (errors.size() > 0) {//was flagged
            displayErrors(errors);
            badChars = true;
        }
        
        //setting these values so that when the form rebuilds, it remembers
        //the entries
        sPhone = ((JTextField) entries.get(0)).getText();
        sStreet = ((JTextField) entries.get(1)).getText();
        sCity = ((JTextField) entries.get(2)).getText();
        sProvince = provinces[((JComboBox) entries.get(3)).getSelectedIndex()];
        storeProvince = ((JComboBox) entries.get(3)).getSelectedIndex();
        sPostal = ((JTextField) entries.get(4)).getText();
        
        
        if(badChars){//need to check if it failed basic input check
            return;//if so, break out to rebuild form.
        }
        //if the generic test passed, we are ready to push the info
        //to the specific input tests
        errors = device.setStoreInfo(sPhone, sStreet, sCity, sProvince, sPostal);
        if (errors.size() > 0) {//if it failed a specific input check
            displayErrors(errors);
            return;
        }
        currentCanvas = "tags";
    }

    private static void handleTagsContinue() {
        ArrayList<String> errors = checkInvalidChars();
        boolean badChars = false;
        if (errors.size() > 0) {
            displayErrors(errors);
            badChars = true;
        }
        if(badChars){
            return;
        }
        //handle dropdowns
        String banner = bannerOptions[((JComboBox) entries.get(0)).getSelectedIndex()];
        System.out.println("banner:" + banner);

        String configuration = configOptions[((JComboBox) entries.get(1)).getSelectedIndex()];
        System.out.println("Configuration:" + configuration);

        String connectionType = wiredOptions[((JComboBox) entries.get(2)).getSelectedIndex()];
        System.out.println("connection type:" + connectionType);

        String inBuildingLocation = locationOptions[((JComboBox) entries.get(3)).getSelectedIndex()];
        System.out.println("In-Building Location:" + inBuildingLocation);

        String interactive = interactiveOptions[((JComboBox) entries.get(4)).getSelectedIndex()];
        System.out.println("interactive:" + interactive);

        String language = languageOptions[((JComboBox) entries.get(5)).getSelectedIndex()];
        System.out.println("language:" + language);

        String lineOfBusiness = ((JTextField) entries.get(6)).getText();
        String storeID = ((JTextField) entries.get(7)).getText();
        //loctype
        String locationType = locTypeOptions[((JComboBox) entries.get(8)).getSelectedIndex()];
        System.out.println("Location Type:" + locationType);

        String manufacturer = manufacturerOptions[((JComboBox) entries.get(9)).getSelectedIndex()];
        System.out.println("Manufacturer:" + manufacturer);

        String model = ((JTextField) entries.get(10)).getText();

        String orientation = orientationOptions[((JComboBox) entries.get(11)).getSelectedIndex()];
        System.out.println("Orientation:" + orientation);

        String ipAddress = ((JTextField) entries.get(12)).getText();
        errors = device.setTags(banner, configuration, connectionType,
                inBuildingLocation, interactive, language, lineOfBusiness, storeID,
                locationType, manufacturer, model, orientation, ipAddress);
        if (errors.size() > 0) {
            displayErrors(errors);
            return;
        }
        currentCanvas = "done";
    }

    private static ArrayList<String> checkInvalidChars() {
        ArrayList<String> errors = new ArrayList();
        for (int i = 0; i < entries.size(); i++) {//i through entries
            try {
                JTextField temp = (JTextField) entries.get(i);
                //System.out.println("Working with " + temp.getText());
                if (temp.getText().compareToIgnoreCase("") == 0) {
                    errors.add("Blank Value for input line " + (i + 1));
                }
                for (int j = 0; j < temp.getText().length(); j++) {//j through letters
                    for (int k = 0; k < badChars.length; k++) {
                        if ((temp.getText().charAt(j) + "").compareToIgnoreCase(badChars[k]) == 0) {
                            errors.add("invalid character \"" + 
                                    temp.getText().charAt(j) + "\" found in \""
                                    + temp.getText() + "\" on line " + (i+1)
                                    + " slot " + (j+1));
                        }
                    }
                }
            } catch (Exception e) {
                try {
                    JComboBox temp = (JComboBox) entries.get(i);
                    if(temp.getSelectedIndex()==0){
                        errors.add("Line " + (i + 1) + " dropdown is empty");
                    }
                } catch (Exception f) {
                    System.out.println("Invalid JComponent Analyzed:");
                    System.out.println(e.getMessage());
                    System.out.println(f.getMessage());
                }
              
            }
        }
        return errors;
    }

    private static void displayErrors(ArrayList<String> errors) {
        //to console
        String fullErrors = "";
        for (int i = 0; i < errors.size(); i++) {
            fullErrors = fullErrors + errors.get(i) + "\n";
        }
        //to a joptionpane
        //JOptionPane.showMessageDialog(null, "message", "title", JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(null, fullErrors, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}
