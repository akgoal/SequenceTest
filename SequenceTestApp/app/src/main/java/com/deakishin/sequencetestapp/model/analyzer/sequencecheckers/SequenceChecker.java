package com.deakishin.sequencetestapp.model.analyzer.sequencecheckers;

/**
 * Интерфейс для проверки последовательности целых чисел на определенный тип.
 */
public interface SequenceChecker {
    /**
     * Проверяет последовательность целых чисел.
     *
     * @param sequence Последовательность чисел.
     * @return True, если проверка прошла успешна, и последовательность
     * является последовательностью правильного типа. Иначе false.
     */
    boolean check(int[] sequence);

    /**
     * @return Id строкового ресурса для результата в случае успешной проверки.
     */
    int getResultResId();
}
