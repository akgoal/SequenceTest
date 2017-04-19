package com.deakishin.sequencetestapp.model.instructions;

import com.deakishin.sequencetestapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Синглетон-менеджер для доступа к элементам инструкции.
 */
public class InstructionsManager {

    // Элементы инструкции.
    private List<SingleInstruction> mInstructions = new ArrayList<>();

    private static InstructionsManager sInstrucionsManager;

    /**
     * @return Единственный экземпляр менеджера инструкций.
     */
    public static InstructionsManager getInstance() {
        if (sInstrucionsManager == null) {
            sInstrucionsManager = new InstructionsManager();
        }
        return sInstrucionsManager;
    }

    private InstructionsManager() {
        // Создаем страницы инструкции.
        addInstructions(new SingleInstruction("instruction1", R.string.instructions_page1));
        addInstructions(new SingleInstruction("instruction2", R.string.instructions_page2));
        addInstructions(new SingleInstruction("instruction3", R.string.instructions_page3));
    }

    // Добавляет элемент инструкции.
    private void addInstructions(SingleInstruction singleInstruction) {
        mInstructions.add(singleInstruction);
    }

    /**
     * @return Список элементов справки. Если элементов нет, возвращает пустой список.
     */
    public List<SingleInstruction> getInstructions() {
        return mInstructions;
    }
}
