package com.deakishin.sequencetestapp.model.settings;

/**
 * Интерфейс для настроек приложения.
 */
public interface SettingsI {
    /**
     * @return True, если нудно показать инструкцию, иначе false.
     */
    boolean getShowInstructions();

    /**
     * Устанавливает, нужно ли в будущем показывать инструкцию.
     *
     * @param toShow True, если инструцию показывать нужно, false в обратном случае.
     */
    void setShowInstructions(boolean toShow);
}
