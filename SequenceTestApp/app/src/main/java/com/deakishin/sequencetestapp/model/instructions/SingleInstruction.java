package com.deakishin.sequencetestapp.model.instructions;

import android.os.Bundle;

/**
 * Элемент инструкции. Содержит имя изображения и идентификатор ресурса текста.
 */
public class SingleInstruction {
    private String mImageName;
    private int mTextResId;

    /**
     * Создает объект элемента инструкции.
     *
     * @param imageName Имя изображения инструкции.
     * @param textResId Id ресурса текста инструкции.
     */
    public SingleInstruction(String imageName, int textResId) {
        mImageName = imageName;
        mTextResId = textResId;
    }

    // Геттеры-сеттеры.
    public String getImageName() {
        return mImageName;
    }

    public void setImageName(String imageName) {
        mImageName = imageName;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }
}
