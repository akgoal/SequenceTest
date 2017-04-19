package com.deakishin.sequencetestapp.model.analyzer.sequencecheckers;

import com.deakishin.sequencetestapp.R;

/**
 * Класс для проверки того, является ли последовательность целых чисел постоянной.
 * То есть все элементы последотельность одинаковы.
 * Длина последовательности должна быть не меньше 2.
 */
public class ConstantSequenceChecker implements SequenceChecker {
    @Override
    public boolean check(int[] sequence) {
        if (sequence.length < 2) {
            return false;
        }
        int first = sequence[0];
        for (int i = 1; i < sequence.length; i++) {
            if (sequence[i] != first) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getResultResId() {
        return R.string.analysis_result_constant_sequence;
    }
}
