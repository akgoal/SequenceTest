package com.deakishin.sequencetestapp.model.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Синглетон класс для хранения настроек приложения и доступа к ним.
 */
public class Settings implements SettingsI {

    // Объект для хранения настроек во внутренней памяти.
    private SharedPreferences mSharedPreferences;

    // Ключи для хранения настроек.
    private static final String PREF_SHOW_INSTRUCTIONS = "showInstructions";

    // Настройки с установленными значениями по умолчанию.
    private boolean mShowInstructions = true;

    private static Settings sSettings;

    /***
     * Возвращает экземпляр класса для работы с настройками.
     *
     * @param context Контекст приложения. Необходим для сохранения настроек в памяти.
     * @return Единственный экземпляр Синглетон-класса.
     */
    public static Settings getInstance(Context context) {
        if (sSettings == null) {
            sSettings = new Settings(context.getApplicationContext());
        }
        return sSettings;
    }

    private Settings(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        // Загружаем настройки.
        mShowInstructions = mSharedPreferences.getBoolean(PREF_SHOW_INSTRUCTIONS, mShowInstructions);
    }


    @Override
    public boolean getShowInstructions() {
        return mShowInstructions;
    }

    @Override
    public void setShowInstructions(boolean toShow) {
        mShowInstructions = toShow;
        mSharedPreferences.edit().putBoolean(PREF_SHOW_INSTRUCTIONS, mShowInstructions).apply();
    }
}
