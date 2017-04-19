package com.deakishin.sequencetestapp.controller;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.deakishin.sequencetestapp.R;
import com.deakishin.sequencetestapp.model.analyzer.Analyzer;
import com.deakishin.sequencetestapp.model.analyzer.AnalyzerI;
import com.deakishin.sequencetestapp.model.storagehelper.StorageHelper;
import com.deakishin.sequencetestapp.model.storagehelper.StorageHelperI;
import com.deakishin.sequencetestapp.model.instructions.InstructionsManager;
import com.deakishin.sequencetestapp.model.instructions.SingleInstruction;
import com.deakishin.sequencetestapp.model.settings.Settings;
import com.deakishin.sequencetestapp.model.settings.SettingsI;

import java.util.List;

/**
 * Активность для главного экрана приложения.
 */
public class MainActivity extends AppCompatActivity implements InstructionsPageFragment.OnOkButtonClickListener {

    /* Коды запросов для дочерних активностей. */
    private static final int REQUEST_ASK_PERMISSIONS = 0;

    // Ключи для сохранения состояния при повороте.
    private static final String EXTRA_RESULT_TEXT = "resultText";

    // Виджеты.
    private ViewPager mInstructionsViewPager;
    private EditText mSeqEditText;
    private Button mAnalyzeButton;
    private TextView mResultTextView;
    private View mAnalyzePanel;

    // Объект для работы с настройками приложения.
    private SettingsI mSettings;

    // Объект для анализа введенной последовательности.
    private AnalyzerI mAnalyzer;

    // Объект для работы с файловой системой.
    private StorageHelperI mStorageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Восстанавливаем сохраненное состояние, если есть.
        String resultText = null;
        if (savedInstanceState != null) {
            resultText = savedInstanceState.getString(EXTRA_RESULT_TEXT);
        }

        // Подключаем виджеты.
        mInstructionsViewPager = (ViewPager) findViewById(R.id.instructions_viewPager);
        mSeqEditText = (EditText) findViewById(R.id.sequence_editText);
        mAnalyzeButton = (Button) findViewById(R.id.analyze_button);
        mAnalyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnalyzeButtonClicked();
            }
        });
        mResultTextView = (TextView) findViewById(R.id.result_textView);
        mResultTextView.setText(resultText);
        mAnalyzePanel = findViewById(R.id.analyze_panel);

        mSettings = Settings.getInstance(this);
        mAnalyzer = new Analyzer();
        mStorageHelper = new StorageHelper(this);

        showInstructions(mSettings.getShowInstructions());

        checkPermissions();
    }

    /* Показывает или скрывает инструкцию в зависимости от параметра toShow. */
    private void showInstructions(boolean toShow) {
        if (toShow) {
            mInstructionsViewPager.setVisibility(View.VISIBLE);
            mAnalyzePanel.setVisibility(View.GONE);

            // Список элементов справки.
            final List<SingleInstruction> mInstructions = InstructionsManager.getInstance().getInstructions();

            // Настраиваем ViewPager.
            mInstructionsViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return InstructionsPageFragment.getInstance(
                            mInstructions.get(position), position == getCount() - 1);
                }

                @Override
                public int getCount() {
                    return mInstructions.size();
                }
            });
        } else {
            mInstructionsViewPager.setVisibility(View.GONE);
        }
    }

    // Запускаем анализ введенной строки.
    private void onAnalyzeButtonClicked() {
        String line = getEditTextString();
        if (line.length() > 0) {
            mResultTextView.setText(mAnalyzer.analyze(line));
            saveEditTextContent();
        }
    }

    // Возвращает строку, введенную в поле ввода.
    private String getEditTextString() {
        if (mSeqEditText.getText() == null) {
            return "";
        }
        return mSeqEditText.getText().toString();
    }

    @Override
    public void onOkButtonClicked() {
        // Нажата кнопка ОК в инструкции.
        // Скрываем инструкцию и сохраняем настройки, чтобы она больше не показывалась.
        mInstructionsViewPager.setVisibility(View.GONE);
        mAnalyzePanel.setVisibility(View.VISIBLE);
        mSettings.setShowInstructions(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Сохраняем состояние.
        if (mResultTextView.getText() != null) {
            outState.putString(EXTRA_RESULT_TEXT, mResultTextView.getText().toString());
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        saveEditTextContent();

        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadEditTextContent();
    }

    // Сохраняет содержимое поля ввода.
    private void saveEditTextContent() {
        mStorageHelper.writeEditTextString(getEditTextString());
    }

    // Загружает содержимое поля ввода.
    private void loadEditTextContent() {
        mSeqEditText.setText(mStorageHelper.readEditTextString());
    }

    // Проверяет разрешения на работу с внешним хранилищем.
    // Необходимо изз-за введеных Runtime Permissions начиная с Android 6.0.
    private void checkPermissions() {
        int hasWriteExtStoragePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExtStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showMessageOKCancel(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions();
                    }
                });
                return;
            }
            requestPermissions();
        }
    }

    /*
     * Performs requesting necessary permission.
     */
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_ASK_PERMISSIONS);
    }

    // Показывает диалог запроса разрешений.
    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this).setMessage(R.string.permission_ext_storage_rationale_text)
                .setPositiveButton(android.R.string.ok, okListener).setNegativeButton(android.R.string.cancel, null).create().show();
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mStorageHelper.rebuild(this);
                }
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
        }
    }
}
