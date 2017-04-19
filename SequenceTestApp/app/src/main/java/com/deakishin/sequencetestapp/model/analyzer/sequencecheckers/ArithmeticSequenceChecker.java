package com.deakishin.sequencetestapp.model.analyzer.sequencecheckers;

import com.deakishin.sequencetestapp.R;

/**
 * Класс для проверки того, является ли последовательность целых чисел арифметической.
 * Последовательность a арифметическая, если существует d такое, что a[i+1] = a[i]+d для любого i.
 * Длина последовательности должна быть не меньше 3.
 */
public class ArithmeticSequenceChecker implements SequenceChecker {
    @Override
    public boolean check(int[] sequence) {
        if (sequence.length < 3) {
            return false;
        }

        int d = sequence[0] - sequence[1];
        for (int i = 1; i < sequence.length - 1; i++) {
            if ((sequence[i] - sequence[i + 1]) != d) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getResultResId() {
        return R.string.analysis_result_arithmetic_sequence;
    }
}
