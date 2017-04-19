package com.deakishin.sequencetestapp.model.analyzer;

import com.deakishin.sequencetestapp.R;
import com.deakishin.sequencetestapp.model.analyzer.sequencecheckers.ArithmeticSequenceChecker;
import com.deakishin.sequencetestapp.model.analyzer.sequencecheckers.ConstantSequenceChecker;
import com.deakishin.sequencetestapp.model.analyzer.sequencecheckers.GeometricSequenceChecker;
import com.deakishin.sequencetestapp.model.analyzer.sequencecheckers.RandomSequenceChecker;
import com.deakishin.sequencetestapp.model.analyzer.sequencecheckers.SequenceChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для анализа строки, содержащей последовательность, и выдачи
 * результата в виде id строкового ресурса.
 */
public class Analyzer implements AnalyzerI {

    // Ресурс результата для случая, когда анализируемая строка не содержит последовательность целых чисел.
    private static final int NOT_SEQ_RES_ID = R.string.analysis_result_not_sequence;

    // Массив объектов проверяющий последовательность чисел на принадлежность к тому или
    // иному типу последовательностей. Проверки будут выполнятся в соответствие с порядком в массиве,
    // поэтому объекты должны располагаться от частного к общему.
    private static final SequenceChecker[] SEQUENCE_CHECKERS = {
            new ConstantSequenceChecker(),
            new GeometricSequenceChecker(),
            new ArithmeticSequenceChecker(),
            new RandomSequenceChecker()
    };

    public Analyzer() {
    }

    @Override
    public int analyze(String line) {
        if (line == null || line.length() < 1)
            return NOT_SEQ_RES_ID;

        // Извлекаем последовательность чисел из строки.
        // Если ее нет в строке, возвращаем соответствующий результат.
        String[] parts = line.split(" ");

        // Создаем список не пустых частей из разбиения.
        List<String> notEmptyParts = new ArrayList<>();
        for (String part:parts){
            if (!part.equals("")){
                notEmptyParts.add(part);
            }
        }

        if (notEmptyParts.size() < 1) {
            return NOT_SEQ_RES_ID;
        }

        int[] seq = new int[notEmptyParts.size()];
        try {
            for (int i = 0; i < notEmptyParts.size(); i++) {
                seq[i] = Integer.parseInt(notEmptyParts.get(i));
            }
        } catch (Exception e) {
            // В строке содержатся неправильные символы.
            return NOT_SEQ_RES_ID;
        }

        // Пропускаем последовательность через проверки, пока одна из них не даст положительный результат.
        for (SequenceChecker sequenceChecker : SEQUENCE_CHECKERS) {
            if (sequenceChecker.check(seq)) {
                return sequenceChecker.getResultResId();
            }
        }

        return NOT_SEQ_RES_ID;
    }
}
