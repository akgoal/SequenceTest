package com.deakishin.sequencetestapp.model.storagehelper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.deakishin.sequencetestapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Помощник для работы с файловой системой.
 */
public class StorageHelper implements StorageHelperI {

    private final String TAG = getClass().getSimpleName();

    // Менеджер для допуска к "сырым" ресурсам.
    private AssetManager mAssets;

    // Внешнее хранилище.
    private File mExternalStorage;

    // Файл для записи данных поля ввода.
    private File mEditTextFile;

    /**
     * Конструирует помощника.
     *
     * @param context Контекст приложения.
     */
    public StorageHelper(Context context) {
        build(context);
    }

    // Проводит настройку объекта.
    private void build(Context context) {
        String folderName = context.getString(R.string.ext_storage_folder_name);
        mExternalStorage = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folderName);
        mExternalStorage.mkdir();

        mEditTextFile = new File(mExternalStorage.getAbsolutePath()
                + "/" + context.getString(R.string.ext_storage_editText_filename));

        mAssets = context.getAssets();
    }

    @Override
    public void writeEditTextString(String string) {
        Writer writer = null;
        OutputStream out = null;
        try {
            out = new FileOutputStream(mEditTextFile);
            writer = new OutputStreamWriter(out);
            writer.write(string);
        } catch (IOException e) {
            Log.e(TAG, "Unable to write file: " + e);
        } finally {
            if (writer != null)
                try {
                    writer.close();
                } catch (IOException e) {
                }
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                }
        }
    }

    @Override
    public String readEditTextString() {
        BufferedReader reader = null;
        InputStream in = null;
        try {
            in = new FileInputStream(mEditTextFile);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            return reader.readLine();
        } catch (IOException e) {
            Log.e(TAG, "Unable to read file: " + e);
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                }
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
    }

    @Override
    public Bitmap loadAssetBitmap(String filename) {
        InputStream is = null;
        try {
            is = mAssets.open(filename + ".png");
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            Log.e(TAG, "Unable to load asset: " + e);
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public void rebuild(Context context) {
        build(context);
    }
}
