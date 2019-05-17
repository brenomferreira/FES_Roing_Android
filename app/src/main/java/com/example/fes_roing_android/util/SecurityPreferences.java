// para salvar dados
// Aqui os dados sao salvos apenas na aplicaçao, se o app for apagado será perdido
// para dados que nao mudam muito
package com.example.fes_roing_android.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SecurityPreferences {

    private final SharedPreferences mSharedPreferences; // variavel

    // CONSTRUTOR
    public SecurityPreferences(Context mcontext) {
        this.mSharedPreferences = mcontext.getSharedPreferences("BD_Parametros", Context.MODE_PRIVATE);
    } // Fim do Construtor

    public void storeString(String key, String value) {
        this.mSharedPreferences.edit().putString(key, value).apply(); // informa a chave e recupera o valor
    } // fim do método edit (StoreString)

    public void storeFloat(String key, float value) {
        this.mSharedPreferences.edit().putFloat(key, value).apply(); // informa a chave e recupera o valor
    } // fim do método edit (StoreFloat)

    public String getStoreString(String key) {
        return this.mSharedPreferences.getString(key, "");
    } // Fim do método getStoreString

    public float getStoreFloat(String key) {
        return this.mSharedPreferences.getFloat(key, 0.01f);
    } // Fim do método getStoreString
} // Fim da Classe
