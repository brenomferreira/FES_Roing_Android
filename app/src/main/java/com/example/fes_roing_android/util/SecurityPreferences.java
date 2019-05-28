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
    
    // String
    public void storeString(String key, String value) {
        this.mSharedPreferences.edit().putString(key, value).apply(); // informa a chave e recupera o valor
    } // fim do método edit (StoreString)

    public String getStoredString(String key) {
        return this.mSharedPreferences.getString(key, "");
    } // Fim do método getStoreString
    
    // Float
    public void storeFloat(String key, float value) {
        this.mSharedPreferences.edit().putFloat(key, value).apply(); // informa a chave e recupera o valor
    } // fim do método edit (StoreFloat)

    public float getStoredFloat(String key) {
        return this.mSharedPreferences.getFloat(key, 0.01f);
    } // Fim do método getStoredFloat
    
    // Int
    public void storeInt(String key, int value) {
        this.mSharedPreferences.edit().putInt(key, value).apply(); // informa a chave e recupera o valor
    } // fim do método edit (StoreInt)

    public int getStoredInt(String key) {
        return this.mSharedPreferences.getInt(key, 1);
    } // Fim do método getStoredInt
    
} // Fim da Classe
