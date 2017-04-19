package com.deakishin.sequencetestapp.model.storagehelper;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Интерфейс помощника для работы с файловой системой.
 */
public interface StorageHelperI {
    /**
     * Записывает строку поля вводав файловую систему.
     *
     * @param string Строка, которую необходимо записать.
     */
    void writeEditTextString(String string);

    /**
     * Считывает строку поля ввода из файловой системы.
     *
     * @return Считанная строка.
     */
    String readEditTextString();

    /**
     * Загружает изображение из ресурсов.
     *
     * @param filename Название файла.
     * @return Загруженное изображение или null, если загрузка не удалась.
     */
    Bitmap loadAssetBitmap(String filename);

    /**
     * Перестраивает помощника.
     *
     * @param context Контекст приложения.
     */
    void rebuild(Context context);
}
