package com.deakishin.sequencetestapp.model.analyzer;

/** Интерфейс анализотора строки, содержащей последовательность. */
public interface AnalyzerI {
    /** Анализирует строку на тип последовательности.
     * @param line Строка, которую нужно проанализировать на тип последовательности.
     * @return Id строкового ресурса в качестве результата анализа. */
    int analyze(String line);
}
