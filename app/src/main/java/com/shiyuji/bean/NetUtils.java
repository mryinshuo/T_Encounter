package com.shiyuji.bean;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.SAXParserFactory;

public class NetUtils {
private static ArrayList<Video> MyList;
    public static byte[] readBytes(InputStream is) {
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String readString(InputStream is) {
        return new String(readBytes(is));
    }


}