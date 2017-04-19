package com.deakishin.sequencetestapp.model.analyzer.sequencecheckers;

import com.deakishin.sequencetestapp.R;

/**
 * Класс для проверки того, является ли последовательность целых чисел произвольной.
 * Длина последовательности должна быть не меньше 2.
 */
public class RandomSequenceChecker implements SequenceChecker {
    @Override
    public boolean check(int[] sequence) {
        return sequence.length >= 2;
    }

    @Override
    public int getResultResId() {
        return R.string.analysis_result_random_sequence;
    }
}
