package com.pagatodo.apolo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Cipher;

/**
 * Created by jvazquez on 19/05/2017.
 */

public class Utils {

    public static String objectToString(Serializable obj) {
        if (obj == null)
            return "";
        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream;
            objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            return asHexStr(serialObj.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
    public static String getFechaForSendToServer() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -1);
        String fechayhora;
        fechayhora = DateFormat.format("yyyyMMdd kk:mm:ss", cal.getTime()).toString();
        return fechayhora;
    }
    public static String getUdid(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
    public static Serializable stringToObject(String str) {
        if (str == null || str.length() == 0)
            return null;
        try {
            ByteArrayInputStream serialObj = new ByteArrayInputStream(asBytes(str));
            ObjectInputStream objStream;
            objStream = new ObjectInputStream(serialObj);
            return (Serializable) objStream.readObject();
        } catch (Exception e) {
            return null;
        }
    }
    private static String asHexStr (byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (( buf[i] & 0xff) < 0x10)
                strbuf.append("0");
            strbuf.append(Long.toString( buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }
    private static byte[] asBytes (String s) {
        String s2;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++) {
            s2 = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte)(Integer.parseInt(s2, 16) & 0xff);
        }
        return b;
    }

    /**
     * Metodo que cambia la fecha como se recibe del servidor al formato en el que se va a mostrar en la aplicación.
     * @param fecha Fecha en formato yyyyMMdd kk:mm:ss
     * @return regresa una fecha en formato "dd/MMM/yy"
     */
    @SuppressLint("SimpleDateFormat")
    public static String cambiarFormatoFecha(String fecha) {
        DateFormatSymbols mesesFormato = cambioFormatoMeses();
        String input = "yyyyMMdd kk:mm:ss";
        String output = "dd/MMM/yy";
        SimpleDateFormat formatoEntrada = new SimpleDateFormat(input);
        SimpleDateFormat formatoSalida = new SimpleDateFormat(output, mesesFormato);

        Date date = null;
        String fechaFormateada = "";

        try {
            date = formatoEntrada.parse(fecha);
            fechaFormateada = formatoSalida.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaFormateada;
    }
    public static DateFormatSymbols cambioFormatoMeses() {
        DateFormatSymbols fechasActual = new DateFormatSymbols(new Locale("es", "ES"));
        String[] anteriorMeses = fechasActual.getShortMonths();
        String[] actualMeses = new String[anteriorMeses.length];
        for (int i = 0; i < 12; i++) {
            if (anteriorMeses.length > 0) {
                actualMeses[i] = Character.toUpperCase(anteriorMeses[i].charAt(0)) + anteriorMeses[i].substring(1).replace(".", "");
            } else {
                actualMeses[i] = "";
            }
        }
        fechasActual.setShortMonths(actualMeses);
        return fechasActual;
    }

    public static String getFormattedDate(String date)
    {
        //Note: SimpleDateFormat was tried for this purpose, but it got a time like 24:00:00
        //Marabunta server expects a date like yyyyMMdd 00:00:00.
        try {
            String[] dateAuxiliar = date.split("/");
            StringBuffer buffer = new StringBuffer();
            for (int index = dateAuxiliar.length - 1; index > -1; index--) {
                buffer.append(dateAuxiliar[index]);
            }

            buffer.append(" 00:00:00");
            return buffer.toString();
        } catch (Exception ex) {
        }

       return "";
    }

    public static String cipherRSA(String text){
        String result;
        try{
            byte[] expBytes = Base64.decode("AQAB".getBytes("UTF-8"), Base64.DEFAULT);
            byte[] modBytes = Base64.decode("wrqWM9l9UB7W6p6MAfMMQMVydqCY3L4hNO/YmVkLUPvbR6JThCugi15P0Dcn2JhVk/wUMRqRVAjt81ZI+V2H7EJpeV22D3r23Sj3pmHuhCVALm/gO20DzZT3RQGogJynXjUO683LQt5uHQWZ0GhcmWM+x0yUTQI4J6oTwPjDI0s=".getBytes("UTF-8"), Base64.DEFAULT);

            BigInteger modules = new BigInteger(1, modBytes);
            BigInteger exponent = new BigInteger(1, expBytes);

            KeyFactory factory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");

            RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(modules, exponent);

            PublicKey pubKey = factory.generatePublic(pubSpec);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            result = Base64.encodeToString(encrypted, Base64.DEFAULT);
        }
        catch(Exception e){
            result = null;
        }
        return result;
    }

    public static String getFormattedCardNumber(String rawNumber)
    {
        if(rawNumber.length() == 16)
        {
            StringBuffer buffer = new StringBuffer();
            char [] auxiliar = rawNumber.toCharArray();

            for(int index = 0; index < rawNumber.length() ; index++)
            {
                if((index - 3) % 4 == 0 && index != rawNumber.length()-1)
                    buffer.append(auxiliar[index]+ "  ");
                else
                    buffer.append(auxiliar[index]);
            }

            return buffer.toString();
        }
        else
            return rawNumber;
    }


    public static String getStringForPurseHistory(String rawDate) {

        try{
            String preTreatedDate = cambiarFormatoFecha(rawDate);
            StringBuffer buffer = new StringBuffer();
            String[] dateAuxiliar = preTreatedDate.split("/");
            return buffer.append(dateAuxiliar[0] + "-" +  dateAuxiliar[1].toUpperCase()).toString();

        }catch (Exception ex)
        {
            return rawDate;
        }
    }

}
