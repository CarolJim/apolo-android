package com.pagatodo.apolo.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Base64;
import android.util.Log;
import android.view.Display;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Omar on 31/07/2017.
 */

public class Base64Utils {


    public static String getEncodedString(Bitmap mBitmap)
    {
        ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 60, mOutputStream);
        byte [] mByteArray = mOutputStream.toByteArray();

        mBitmap.recycle();

        try {
            mOutputStream.flush();
            mOutputStream.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return Base64.encodeToString(mByteArray, Base64.DEFAULT);
    }

    public static Bitmap getDecodedBitmap(String encodedBase64, Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point displayPointer = new Point();
        display.getSize(displayPointer);

        byte [] encodedData = Base64.decode(encodedBase64, Base64.DEFAULT);
        Bitmap mBitmap = BitmapFactory.decodeByteArray(encodedData, 0 , encodedData.length);
        return Bitmap.createScaledBitmap(mBitmap, displayPointer.x, displayPointer.y, false);
    }

}
