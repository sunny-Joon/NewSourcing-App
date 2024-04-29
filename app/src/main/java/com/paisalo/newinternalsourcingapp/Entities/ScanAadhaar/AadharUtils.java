package com.paisalo.newinternalsourcingapp.Entities.ScanAadhaar;

import android.util.Log;

import androidx.room.Room;

import com.paisalo.newinternalsourcingapp.RoomDatabase.DatabaseClass;
import com.paisalo.newinternalsourcingapp.RoomDatabase.RangeCategoryDataClass;
import com.paisalo.newinternalsourcingapp.Utils.Utils;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;


public class AadharUtils {

    static DatabaseClass database;
    public static Map<String, String> ParseAadhar(String AadharXMLString) throws Exception {
        Map<String, String> AadharDataMap = new HashMap<>();

        String pivotString = "";
        String pattern = ".*QP?D[A-Z].*";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(AadharXMLString);
        String[] AadharQrTag = {"PrintLetterBarcodeData", "QDA", "QPDA", "QDB", "QPDB", "QDC", "QPDC", "QDD", "QPDD"}; //
        for (int i = 0; i < AadharQrTag.length; i++) {
            if (AadharXMLString.contains(AadharQrTag[i])) {
                pivotString = AadharQrTag[i];
            }
        }
        if (pivotString == "") {
            throw new Exception("This QR Code is not supported at this time");
        } else {
            String data = AadharXMLString.substring(AadharXMLString.indexOf(pivotString) + pivotString.length() + 1, AadharXMLString.length() - 3);
            data = data.replaceAll("=\" ", "=\"");
            data = data.replaceAll(" \" ", "\" ");
            data = data.replaceAll("\"\"", "\"?\"");
            String datas[] = data.split("\" ");
            String[] keyval;
            for (String node : datas) {
                keyval = node.split("=\"");
                AadharDataMap.put(keyval[0], keyval[1].replace("?", "").trim());
            }
        }
        return AadharDataMap;
    }

    public static AadharData getAadhar(Map<String, String> aadharDataMap) {
        AadharData aadharData = null;
        if (aadharDataMap.containsKey("uid")) aadharData = getAadharOld(aadharDataMap);
        if (aadharDataMap.containsKey("u")) aadharData = getAadharNew(aadharDataMap);
        return aadharData;
    }
    public static boolean comparedateofbirth(Date aadharDOB,String panDOB){
        boolean datematched;
        Date panDOb=(DateUtils.getParsedDate(panDOB, "dd/MM/yyyy"));

        if (aadharDOB.before(panDOb)) {
            datematched=false;
        } else if (aadharDOB.after(panDOb)) {
            datematched=false;
        } else {
            // System.out.println("Date 1 is equal to Date 2.");
            datematched=true;
        }
        return  datematched;
    }
    public static AadharData getAadhar(String xmlString) {

        Log.d("CheckXMLDATA","AadharData:->" + xmlString);
        AadharData aadharData = null;
        try {
            Document doc = Utils.parseXmlString(xmlString);
            NamedNodeMap nodeMap = getAadharFields(doc);
       //     aadharData = getAadharXML(nodeMap);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return aadharData;
    }

    public static AadharData getAadharOld(Map<String, String> aadharDataMap) {
        AadharData aadharData = null;
        Date dateAadharDob;

        if (aadharDataMap.containsKey("dob"))
            dateAadharDob = getAadharKeyValue(aadharDataMap, "dob", Date.class);
        else if (aadharDataMap.containsKey("dateOfBirth"))
            dateAadharDob = getAadharKeyValue(aadharDataMap, "dateOfBirth", Date.class);
        else
            dateAadharDob = DateUtils.getDate(getAadharKeyValue(aadharDataMap, "yob", int.class), 7, 1);

        if (DateUtils.getAge(dateAadharDob) >= 18) {
            aadharData = new AadharData();
            aadharData.AadharId = getAadharKeyValue(aadharDataMap, "uid", String.class);
            aadharData.Name = getAadharKeyValue(aadharDataMap, "name", String.class).toUpperCase();
            String co = getAadharKeyValue(aadharDataMap, "co", String.class);

            if (co.length() > 4)
                aadharData.GurName = co.substring(4).trim().toUpperCase();
            else {
                co = getAadharKeyValue(aadharDataMap, "gname", String.class);
                if (co.length() > 2) aadharData.GurName = co;
            }
            aadharData.DOB = dateAadharDob;
            aadharData.Age = DateUtils.getAge(aadharData.DOB);
            aadharData.Gender = (getAadharKeyValue(aadharDataMap, "gender", String.class));

            String address = "", addrTemp = "";
            addrTemp = getAadharKeyValue(aadharDataMap, "house", String.class);
            addrTemp += addrTemp.length() > 0 ? "," : "";
            address += addrTemp;

            addrTemp = getAadharKeyValue(aadharDataMap, "street", String.class).toUpperCase();
            addrTemp += addrTemp.length() > 0 ? "," : "";
            address += addrTemp;

            addrTemp = getAadharKeyValue(aadharDataMap, "loc", String.class).toUpperCase();
            addrTemp += addrTemp.length() > 0 ? "," : "";
            address += addrTemp;

            addrTemp = getAadharKeyValue(aadharDataMap, "lm", String.class).toUpperCase();
            addrTemp += addrTemp.length() > 0 ? "," : "";
            address += addrTemp;

            addrTemp = getAadharKeyValue(aadharDataMap, "vtc", String.class).toUpperCase();
            addrTemp += addrTemp.length() > 0 ? "," : "";
            address += addrTemp;

            addrTemp = getAadharKeyValue(aadharDataMap, "po", String.class).toUpperCase();
            addrTemp += addrTemp.length() > 0 ? "," : "";
            address += addrTemp;

            addrTemp = getAadharKeyValue(aadharDataMap, "subdist", String.class).toUpperCase();
            addrTemp += addrTemp.length() > 0 ? "," : "";
            address += addrTemp;

            addrTemp = getAadharKeyValue(aadharDataMap, "dist", String.class).toUpperCase();
            addrTemp += addrTemp.length() > 0 ? "," : "";
            address += addrTemp;

            addrTemp = getAadharKeyValue(aadharDataMap, "state", String.class);
            addrTemp += addrTemp.length() > 0 ? "," : "";
            address += addrTemp;

            addrTemp = getAadharKeyValue(aadharDataMap, "pc", String.class).toUpperCase();
            addrTemp += addrTemp.length() > 0 ? "," : "";
            address += addrTemp;


            Map<String, String> addressData = getNewAadharAddress(address);

            aadharData.Pin = Integer.parseInt(addressData.get("pc"));
            aadharData.State = getAadharKeyValue(addressData, "state", String.class); //addressData.get("state");
            // dbh.getState(getAadharKeyValue(aadharData, "state", String.class)).StateCode;
            aadharData.City = getAadharKeyValue(addressData, "dist", String.class);

            aadharData.Address1 = getAadharKeyValue(addressData, "addr1", String.class).toUpperCase();
            aadharData.Address2 = getAadharKeyValue(addressData, "addr2", String.class).toUpperCase();
            aadharData.Address3 = getAadharKeyValue(addressData, "addr3", String.class).toUpperCase();

            aadharData.isAadharVerified = "Q";
        }
        return aadharData;
    }

    public static AadharData getAadharNew(Map<String, String> aadharDataMap) {
        AadharData aadharData = null;
        Date dateAadharDob;
        if (aadharDataMap.containsKey("d"))
            dateAadharDob = getAadharKeyValue(aadharDataMap, "d", Date.class);
        else
            dateAadharDob = DateUtils.getDate(getAadharKeyValue(aadharDataMap, "y", int.class), 7, 1);

        if (DateUtils.getAge(dateAadharDob) >= 18) {
            aadharData = new AadharData();
            aadharData.AadharId = getAadharKeyValue(aadharDataMap, "u", String.class);
            aadharData.Name = getAadharKeyValue(aadharDataMap, "n", String.class).toUpperCase();
            aadharData.Gender = getAadharKeyValue(aadharDataMap, "g", String.class);

            aadharData.GurName = getAadharKeyValue(aadharDataMap, "c", String.class);
            aadharData.DOB = dateAadharDob;
            aadharData.Age = DateUtils.getAge(aadharData.DOB);

            Map<String, String> addressData = getNewAadharAddress(getAadharKeyValue(aadharDataMap, "a", String.class));

            aadharData.Pin = Integer.parseInt(addressData.get("pc"));
            aadharData.State = getAadharKeyValue(addressData, "state", String.class); //addressData.get("state");
            aadharData.City = getAadharKeyValue(addressData, "dist", String.class);

            aadharData.Address1 = getAadharKeyValue(addressData, "addr1", String.class).toUpperCase();
            aadharData.Address2 = getAadharKeyValue(addressData, "addr2", String.class).toUpperCase();
            aadharData.Address3 = getAadharKeyValue(addressData, "addr3", String.class).toUpperCase();
            aadharData.isAadharVerified = "Q";
        }
        return aadharData;
    }

    /*public static AadharData getAadharXML(NamedNodeMap nodeMap) {
        AadharData aadharData = null;
        Date dateAadharDob;
        String aadhar_dob = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_dob"));
        if (aadhar_dob != null) {
            dateAadharDob = getTypedValue(aadhar_dob, Date.class);
        } else {
            String aadhar_yob = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_yob"));
            dateAadharDob = DateUtils.getDate(getTypedValue(aadhar_yob, int.class), 7, 1);
        }
        if (DateUtils.getAge(dateAadharDob) >= 18) {
            aadharData = new AadharData();
            aadharData.AadharId = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_id"));
            aadharData.Name = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_name")).toUpperCase();
            aadharData.Gender = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_gender"));

            aadharData.GurName = Utils.toUpperCase(getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_co")));
            aadharData.DOB = dateAadharDob;
            aadharData.Age = DateUtils.getAge(aadharData.DOB);
            Map<String, String> addressData;
            if (getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_address")) != null) {
                addressData = getNewAadharAddress(getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_address")));
            } else {
                String address = "", addrTemp = "";
                addrTemp = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_house")); //getAadharKeyValue(aadharDataMap, "house", String.class);
                if (addrTemp != null) {
                    addrTemp += addrTemp.length() > 0 ? "," : "";
                    address += addrTemp;
                }

                addrTemp = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_street")); //getAadharKeyValue(aadharDataMap, "street", String.class).toUpperCase();
                if (addrTemp != null) {
                    addrTemp += addrTemp.length() > 0 ? "," : "";
                    address += addrTemp;
                }

                addrTemp = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_street")); //getAadharKeyValue(aadharDataMap, "loc", String.class).toUpperCase();
                if (addrTemp != null) {
                    addrTemp += addrTemp.length() > 0 ? "," : "";
                    address += addrTemp;
                }

                addrTemp = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_landmark")); //getAadharKeyValue(aadharDataMap, "lm", String.class).toUpperCase();
                if (addrTemp != null) {
                    addrTemp += addrTemp.length() > 0 ? "," : "";
                    address += addrTemp;
                }

                addrTemp = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_vtc")); //getAadharKeyValue(aadharDataMap, "vtc", String.class).toUpperCase();
                if (addrTemp != null) {
                    addrTemp += addrTemp.length() > 0 ? "," : "";
                    address += addrTemp;
                }

                addrTemp = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_postoffice")); //getAadharKeyValue(aadharDataMap, "po", String.class).toUpperCase();
                if (addrTemp != null) {
                    addrTemp += addrTemp.length() > 0 ? "," : "";
                    address += addrTemp;
                }

                addrTemp = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_sub_dist")); //getAadharKeyValue(aadharDataMap, "subdist", String.class).toUpperCase();
                if (addrTemp != null) {
                    addrTemp += addrTemp.length() > 0 ? "," : "";
                    address += addrTemp;
                }

                addrTemp = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_dist")); //getAadharKeyValue(aadharDataMap, "dist", String.class).toUpperCase();
                if (addrTemp != null) {
                    addrTemp += addrTemp.length() > 0 ? "," : "";
                    address += addrTemp;
                }

                addrTemp = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_state")); //getAadharKeyValue(aadharDataMap, "state", String.class);
                if (addrTemp != null) {
                    addrTemp += addrTemp.length() > 0 ? "," : "";
                    address += addrTemp;
                }

                addrTemp = getAadharFieldValue(nodeMap, getAadharFieldMaps("aadhar_pincode")); //getAadharKeyValue(aadharDataMap, "pc", String.class).toUpperCase();
                if (addrTemp != null) {
                    addrTemp += addrTemp.length() > 0 ? "," : "";
                    address += addrTemp;
                }

                addressData = getNewAadharAddress(address);
            }

            aadharData.Pin = Integer.parseInt(addressData.get("pc"));
            aadharData.State = getAadharKeyValue(addressData, "state", String.class); //addressData.get("state");
            // dbh.getState(getAadharKeyValue(aadharData, "state", String.class)).StateCode;
            aadharData.City = getAadharKeyValue(addressData, "dist", String.class);

            aadharData.Address1 = getAadharKeyValue(addressData, "addr1", String.class).toUpperCase();
            aadharData.Address2 = getAadharKeyValue(addressData, "addr2", String.class).toUpperCase();
            aadharData.Address3 = getAadharKeyValue(addressData, "addr3", String.class).toUpperCase();
            aadharData.isAadharVerified = "Q";
        }
        return aadharData;
    }*/

    private static Map<String, String> getNewAadharAddress(String addressString) {
        String datas[] = addressString.split(",");
        Map<String, String> addressMap = new HashMap<>();
        addressMap.put("pc", datas[datas.length - 1]);
        addressMap.put("state", datas[datas.length - 2]);
        addressMap.put("dist", datas[datas.length - 3]);
        addressMap.putAll(getAddress(datas, 4));

        return addressMap;
    }

    private static Map<String, String> getAddress(String datas[], int offset) {
        final int MAXLENGTH = 50;
        Map<String, String> addressMap = new HashMap<>();

        int addCnt = 0;
        int addrLimit = datas.length - offset;
        String addr = "";
        boolean finishAdd = false;

        for (int i = 0; i <= addrLimit; i++) {
            if (addr.equals("")) {
                addr = datas[i];
                finishAdd = false;
            }

            if (i + 1 < addrLimit) {
                if (addr.length() + datas[i + 1].length() < MAXLENGTH) {
                    addr += " " + datas[i + 1];

                } else {
                    finishAdd = true;
                }
            } else {
                finishAdd = true;
            }
            if (finishAdd)
                switch (addCnt) {
                    case 0:
                        addressMap.put("addr1", addr);
                        addr = "";
                        addCnt++;
                        break;
                    case 1:
                        addressMap.put("addr2", addr);
                        addr = "";
                        addCnt++;
                        break;
                    case 2:
                        addressMap.put("addr3", addr);
                        addr = "";
                        addCnt++;
                        break;
                }
        }
        return addressMap;
    }

    public static AadharData getAadhar(JSONObject aadharJson) {

        AadharData aadharData = null;
        Date dateAadharDob;
        if (aadharJson.has("Poidob"))
            dateAadharDob = getAadharKeyValue(aadharJson, "Poidob", Date.class);
        else
            dateAadharDob = DateUtils.getDate(getAadharKeyValue(aadharJson, "yob", int.class), 7, 1);

        if (DateUtils.getAge(dateAadharDob) >= 18) {
            aadharData = new AadharData();
            aadharData.AadharId = "";
            aadharData.UUID = getAadharKeyValue(aadharJson, "UUID", String.class);
            aadharData.Name = getAadharKeyValue(aadharJson, "Poiname", String.class).toUpperCase();
            String co = getAadharKeyValue(aadharJson, "Poaco", String.class);
            if (co.length() > 4)
                aadharData.GurName = co.substring(4).trim().toUpperCase();
            else {
                co = getAadharKeyValue(aadharJson, "gname", String.class);
                if (co.length() > 2) aadharData.GurName = co;
            }
            aadharData.DOB = dateAadharDob;
            aadharData.Age = DateUtils.getAge(aadharData.DOB);
            aadharData.State = "09";// dbh.getState(getAadharKeyValue(aadharData, "state", String.class)).StateCode;
            aadharData.Pin = getAadharKeyValue(aadharJson, "Poapc", int.class);
            aadharData.Gender = (getAadharKeyValue(aadharJson, "Poigender", String.class));
            aadharData.Address1 = (getAadharKeyValue(aadharJson, "Poahouse", String.class)
                    + ((getAadharKeyValue(aadharJson, "Poahouse", String.class)).length() > 0 ? " " : "")
                    + getAadharKeyValue(aadharJson, "Poastreet", String.class)).toUpperCase();
            aadharData.Address2 = (getAadharKeyValue(aadharJson, "Poaloc", String.class)
                    + ((getAadharKeyValue(aadharJson, "Poaloc", String.class)).length() > 0 ? " " : "")
                    + getAadharKeyValue(aadharJson, "Poalm", String.class)).toUpperCase();
            aadharData.Address3 = (getAadharKeyValue(aadharJson, "Poavtc", String.class)
                    + ((getAadharKeyValue(aadharJson, "Poavtc", String.class)).length() > 0 ? " " : "")
                    + getAadharKeyValue(aadharJson, "Poapo", String.class)
                    + ((getAadharKeyValue(aadharJson, "Poapo", String.class)).length() > 0 ? " " : "")
                    + getAadharKeyValue(aadharJson, "Poasubdist", String.class)).toUpperCase();
            aadharData.City = getAadharKeyValue(aadharJson, "Poadist", String.class).toUpperCase();
            if (aadharData.Address1 == null || aadharData.Address1.trim().equals("")) {
                aadharData.Address1 = aadharData.Address2;
                aadharData.Address2 = aadharData.Address3;
                aadharData.Address3 = null;
            }
            aadharData.isAadharVerified = "Y";
        }
        return aadharData;
    }

    public static <T> T getAadharKeyValue(Map<String, String> aadharData, String Key, Class<T> type) {
        T value = null;
        if (aadharData.containsKey(Key)) {
            String aadharValue = aadharData.get(Key);
            value = getTypedValue(aadharValue, type);
        } else {
            if (type.getSimpleName().equals("String"))
                value = (T) "";
            if (type.getSimpleName().equals("int"))
                value = (T) (Object) 0;
        }
        return value;
    }

    public static String getAadharFieldValue(NamedNodeMap nodeMap, List<String> keys) {
        String retVal = null;
        for (String uidKey : keys) {
            Node node = nodeMap.getNamedItem(uidKey);
            if (node != null) {
                retVal = node.getNodeValue();
                break;
            }
        }
        return retVal;
    }

    public static <T> T getAadharKeyValue(JSONObject aadharJson, String Key, Class<T> type) {
        T value = null;

        if (aadharJson.has(Key)) {
            try {
                String aadharValue = aadharJson.getString(Key);
                value = getTypedValue(aadharValue, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (type.getSimpleName().equals("String"))
                value = (T) "";
            if (type.getSimpleName().equals("int"))
                value = (T) (Object) 0;
        }
        return value;
    }

    public static <T> T getTypedValue(String data, Class<T> type) {
        T value = null;
        if (type.getSimpleName().equals("String"))
            value = (T) data;
        else if (type.getSimpleName().equals("int")) {
            if (Utils.isInteger(data)) {
                int intval = Integer.parseInt(data);
                value = (T) (Object) intval;
            }
        } else if (type.getSimpleName().equals("Date")) {
            value = (T) DateUtils.getParsedDate(data);
        }
        return value;
    }

    public static AadharData parseAadhar(Aadhar uuidData) {
        String add;
        AadharData custobj = new AadharData();
        custobj.DOB = DateUtils.getParsedDate(uuidData.getPoidob());
        custobj.Age = DateUtils.getAge(custobj.DOB);
        custobj.Gender = uuidData.getPoigender();
        custobj.Name = uuidData.getPoiname();

        String gurName = Utils.NullIf(uuidData.getPoaco(), "");
        custobj.GurName = gurName.length() > 5 ? gurName.substring(4).trim() : "";

        add = Utils.NullIf(uuidData.getPoahouse(), "");
        add += " " + Utils.NullIf(uuidData.getPoastreet(), "");
        custobj.Address1 = add.trim();

        add = Utils.NullIf(uuidData.getPoaloc(), "");
        add += " " + Utils.NullIf(uuidData.getPoalm(), "");
        custobj.Address2 = add.trim();

        add = Utils.NullIf(uuidData.getPoavtc(), "");
        add += " " + Utils.NullIf(uuidData.getPoapo(), "");
        add += " " + Utils.NullIf(uuidData.getPoasubdist(), "");
        custobj.Address3 = add.trim();

        custobj.City = Utils.NullIf(uuidData.getPoadist(), "");
        custobj.Pin = Utils.NullIf(Integer.valueOf(uuidData.getPoapc()), 0);

        custobj.State = Utils.NullIf(uuidData.getPoastate(), "");
        custobj.isAadharVerified = "Y";
        custobj.UUID = uuidData.getUUID();

        if (custobj.Address1 == null || custobj.Address1.length() == 0) {
            custobj.Address1 = custobj.Address2;
            custobj.Address2 = custobj.Address3;
            custobj.Address3 = "";
        }
        if (custobj.Address2 == null || custobj.Address2.length() == 0) {
            custobj.Address2 = custobj.Address3;
            custobj.Address3 = "";
        }
        return custobj;
    }

    public static NamedNodeMap getAadharFields(Document document) {
        return document.getFirstChild().getAttributes();
    }



//    public static String getStateCode(String stateName) {
//        String stateCode = "";
//        database = Room.databaseBuilder(context, DatabaseClass.class, "app-database")
//                .build();
//
//        // Query the RangeCategory table for a specific stateName
//        RangeCategoryDataClass rangeCategory = database.dao().getAllRCDataListby_catKey("state");
//
//        // If a matching RangeCategory is found, retrieve its RangeCode
//        if (rangeCategory != null) {
//            stateCode = rangeCategory.getRangeCode();
//        }
//
//        return stateCode;
//    }
}
