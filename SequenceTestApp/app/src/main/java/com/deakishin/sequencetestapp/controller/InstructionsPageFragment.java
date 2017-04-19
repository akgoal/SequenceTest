package com.deakishin.sequencetestapp.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.deakishin.sequencetestapp.R;
import com.deakishin.sequencetestapp.model.instructions.SingleInstruction;
import com.deakishin.sequencetestapp.model.storagehelper.StorageHelper;
import com.deakishin.sequencetestapp.model.storagehelper.StorageHelperI;

/**
 * Фрагмент для отображения элемента инструкции.
 */
public class InstructionsPageFragment extends Fragment {

    // Ключи для сохранения данных в аргументах фрагмента.
    private static final String EXTRA_TEXT_RES_ID = "textResId";
    private static final String EXTRA_IMAGE_NAME = "imageName";
    private static final String EXTRA_SHOW_OK_BUTTON = "showOkButton";

    // Данные инструкции.
    private int mTextResId;
    private String mImageName;

    // Показывать ли кнопку ОК.
    private boolean mShowOkButton;

    // Объект для доступа к ресурсам.
    private StorageHelperI mStorageHelper;

    /**
     * Интерфейс слушателя нажатия на кнопку ОК. Родителькая активность должна реализовать этот интерфейс,
     * чтобы эти получать оповещения.
     */
    public interface OnOkButtonClickListener {
        /**
         * Вызывается, когда нажата кнопка ОК.
         */
        void onOkButtonClicked();
    }

    public InstructionsPageFragment() {
    }

    /**
     * Возвращает настроенный фрагмент для отображения конкретного элемента справки.
     *
     * @param instruction  Элемент инструкции, который необходимо отобразить.
     * @param showOkButton Определяет, нужно ли показывать кнопку OK.
     * @return Настроенный фрагмент.
     */
    public static InstructionsPageFragment getInstance(SingleInstruction instruction, boolean showOkButton) {
        InstructionsPageFragment fragment = new InstructionsPageFragment();
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_SHOW_OK_BUTTON, showOkButton);
        if (instruction != null) {
            args.putInt(EXTRA_TEXT_RES_ID, instruction.getTextResId());
            args.putString(EXTRA_IMAGE_NAME, instruction.getImageName());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mTextResId = args.getInt(EXTRA_TEXT_RES_ID);
            mImageName = args.getString(EXTRA_IMAGE_NAME);
            mShowOkButton = args.getBoolean(EXTRA_SHOW_OK_BUTTON, false);
        }

        mStorageHelper = new StorageHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instructions_page, null);

        TextView tvPage = (TextView) view.findViewById(R.id.instructions_page_textView);
        tvPage.setText(mTextResId);

        ImageView imageView = (ImageView) view.findViewById(R.id.instructions_page_imageView);
        imageView.setImageBitmap(mStorageHelper.loadAssetBitmap(mImageName));

        Button okButton = (Button) view.findViewById(R.id.instructions_page_ok_button);
        okButton.setVisibility(mShowOkButton ? View.VISIBLE : View.GONE);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyOkButtonClicked();
            }
        });

        return view;
    }

    // Оповещает родительскую активность о том, что нажата кнопка ОК.
    private void notifyOkButtonClicked() {
        Activity activity = getActivity();
        if (activity != null && activity instanceof OnOkButtonClickListener) {
            ((OnOkButtonClickListener) activity).onOkButtonClicked();
        }
    }
}
