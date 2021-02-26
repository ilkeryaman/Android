package com.reset4.passlock.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

import com.reset4.fourwork.datalayer.BusinessObject;
import com.reset4.fourwork.engine.general.FourContext;
import com.reset4.fourwork.engine.general.FourException;
import com.reset4.passlock.businessobjects.PasswordInfoBO;
import com.reset4.passlock.security.Encryption;
import com.reset4.passlock.security.EncryptionResult;

public class XmlFileOperator {
    FourContext fourContext;
    private String masterPassword;
    private String reset4passlock;
    private String encryptedConstantPasswordInfoBO;
    private String encryptedConstantID;
    private String encryptedConstantAccountName;
    private String encryptedConstantUserId;
    private String encryptedConstantPassword;
    private String encryptedConstantUrl;
    private String encryptedConstantNotes;

    public XmlFileOperator(FourContext fourContext, String masterPassword){
        this.fourContext = fourContext;
        this.masterPassword = masterPassword;
        reset4passlock = "r4PassLock";
        encryptedConstantPasswordInfoBO = "botw";
        encryptedConstantID = "r4";
        encryptedConstantAccountName = "trxyz";
        encryptedConstantUserId = "qwelkj";
        encryptedConstantPassword = "prytux";
        encryptedConstantUrl = "wlr";
        encryptedConstantNotes = "mtsgh";
    }

    public void writeXml(String path, List<PasswordInfoBO> passwordInfoBOList){
        File newXmlFile = new File(path);
        try{
            newXmlFile.createNewFile();
        }catch(IOException e){
            Log.e("IOException", "exception in createNewFile() method");
        }
        //we have to bind the new file with a FileOutputStream
        FileOutputStream fileos = null;
        try{
            fileos = new FileOutputStream(newXmlFile);
        }catch(FileNotFoundException e){
            Log.e("FileNotFoundException", "can't create FileOutputStream");
        }
        //we create a XmlSerializer in order to write xml data
        XmlSerializer serializer = Xml.newSerializer();
        try {
            //we set the FileOutputStream as output for the serializer, using UTF-8 encoding
            serializer.setOutput(fileos, "UTF-8");
            //Write <?xml declaration with encoding (if encoding not null) and standalone flag (if standalone not null)
            serializer.startDocument(null, Boolean.valueOf(true));
            //set indentation option
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            //start a tag called "r4PassLock"
            serializer.startTag(null, reset4passlock);

            for(PasswordInfoBO passwordInfoBO : passwordInfoBOList){
                serializer.startTag(null, encryptedConstantPasswordInfoBO);
                serializer.startTag(null, encryptedConstantID);
                serializer.text(encrypt(passwordInfoBO.getEntity().getPrimaryKeyField().getValue().toString()));
                serializer.endTag(null, encryptedConstantID);
                serializer.startTag(null, encryptedConstantAccountName);
                serializer.text(encrypt(passwordInfoBO.getEntity().getAccountName()));
                serializer.endTag(null, encryptedConstantAccountName);
                serializer.startTag(null, encryptedConstantUserId);
                serializer.text(encrypt(passwordInfoBO.getEntity().getUserId()));
                serializer.endTag(null, encryptedConstantUserId);
                serializer.startTag(null, encryptedConstantPassword);
                serializer.text(passwordInfoBO.getEntity().getPassword());
                serializer.endTag(null, encryptedConstantPassword);
                serializer.startTag(null, encryptedConstantUrl);
                serializer.text(encrypt(passwordInfoBO.getEntity().getUrl()));
                serializer.endTag(null, encryptedConstantUrl);
                serializer.startTag(null, encryptedConstantNotes);
                serializer.text(encrypt(passwordInfoBO.getEntity().getNotes()));
                serializer.endTag(null, encryptedConstantNotes);
                serializer.endTag(null, encryptedConstantPasswordInfoBO);
            }

            serializer.endTag(null, reset4passlock);
            serializer.endDocument();
            //write xml data into the FileOutputStream
            serializer.flush();
            //finally we close the file stream
            fileos.close();


        } catch (Exception e) {
            Log.e("Exception","error occurred while creating xml file");
        }
    }

    public List<PasswordInfoBO> readXml(String path){
        List<PasswordInfoBO> passwordInfoBOList = new ArrayList<>();
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = new FileInputStream(new File(path));
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            passwordInfoBOList = parseXML(parser, masterPassword);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return passwordInfoBOList;
    }

    private List<PasswordInfoBO> parseXML( XmlPullParser parser, String masterPassword) throws XmlPullParserException,IOException
    {
        List<PasswordInfoBO> passwordInfoBOList = null;
        int eventType = parser.getEventType();
        PasswordInfoBO passwordInfoBO = null;

        try {
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        passwordInfoBOList = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equals(encryptedConstantPasswordInfoBO)) {
                            try {
                                passwordInfoBO = (PasswordInfoBO) BusinessObject.getBusinessObject(PasswordInfoBO.tableName,
                                        fourContext);
                            } catch (FourException e) {
                                e.printStackTrace();
                            }
                        } else if (name.equals(encryptedConstantAccountName)) {
                            passwordInfoBO.getEntity().setAccountName(decrypt(parser.nextText()));
                        } else if (name.equals(encryptedConstantUserId)) {
                            passwordInfoBO.getEntity().setUserId(decrypt(parser.nextText()));
                        } else if (name.equals(encryptedConstantPassword)) {
                            passwordInfoBO.getEntity().setPassword(parser.nextText());
                        } else if (name.equals(encryptedConstantUrl)) {
                            passwordInfoBO.getEntity().setUrl(decrypt(parser.nextText()));
                        } else if (name.equals(encryptedConstantNotes)) {
                            passwordInfoBO.getEntity().setNotes(decrypt(parser.nextText()));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(encryptedConstantPasswordInfoBO) && passwordInfoBO != null) {
                            passwordInfoBOList.add(passwordInfoBO);
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (FourException e){

        }

        return passwordInfoBOList;
    }

    private String encrypt(String value){
        if(value == null){
            value = "";
        }
        return Encryption.encrypt(masterPassword, value).getValue();
    }

    private String decrypt(String value) throws FourException {
        EncryptionResult encryptionResult = Encryption.decrypt(masterPassword, value);
        if(encryptionResult.getExceptionClass() != null){
            throw new FourException(encryptionResult.getExceptionMessage());
        }
        return encryptionResult.getValue();
    }
}

