package com.example.fes_roing_android.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SecurityPreferences {

    private final SharedPreferences mSharedPreferences;

    public SecurityPreferences(Context context) {

        this.mSharedPreferences = context.getSharedPreferences("Parametros", Context.MODE_PRIVATE);
    }

    public void storeString(String key, String value) {
        this.mSharedPreferences.edit().putString(key, value);
    }

    public String getStoreString(String key) {

        return this.mSharedPreferences.getString(key, "");

    }


}
