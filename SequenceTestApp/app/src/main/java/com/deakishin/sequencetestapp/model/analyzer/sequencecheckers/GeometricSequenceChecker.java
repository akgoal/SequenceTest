package com.deakishin.sequencetestapp.model.analyzer.sequencecheckers;

import com.deakishin.sequencetestapp.R;

/**
 * Класс для проверки того, является ли последовательность целых чисел геометрической.
 * Последовательность g арифметическая, если существует b такое, что g[i+1] = b*g[i] для любого i.
 * Длина последовательности должна быть не меньше 3.
 */
public class GeometricSequenceChecker implements SequenceChecker {

    @Override
    public boolean check(int[] sequence) {
        if (sequence.length < 3) {
            return false;
        }

        float b;
        if (sequence[0] == 0) {
            if (sequence[1] == 0) {
                b = 0;
            } else {
                return false;
            }
        } else {
            b = sequence[1] / (float) sequence[0];
        }

        for (int i = 1; i < sequence.length - 1; i++) {
            /*if (sequence[i] * b > sequence[i + 1] + EPS
                    || sequence[i] * b < sequence[i + 1] - EPS) {
                return false;
            }*/
            if (sequence[i] * b != sequence[i + 1]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getResultResId() {
        return R.string.analysis_result_geometric_sequence;
    }
}
