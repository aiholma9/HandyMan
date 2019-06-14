package com.holma.myserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        printKeyHash();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }

    private void printKeyHash() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo(getOpPackageName(),
                    PackageManager.GET_SIGNATURES);

            for (Signature signature:info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }

        catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
}
