package com.deakishin.sequencetestapp;

import com.deakishin.sequencetestapp.model.analyzer.Analyzer;
import com.deakishin.sequencetestapp.model.analyzer.AnalyzerI;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Тесты для анализотары строки с последовательностью чисел.
 */
public class AnalyzerUnitTest {

    private AnalyzerI mAnalyzer;

    @Before
    public void configureAnalyzer() {
        mAnalyzer = new Analyzer();
    }

    @Test
    public void analyze_notSequence() throws Exception {
        int result = R.string.analysis_result_not_sequence;
        assertEquals(mAnalyzer.analyze(""), result);
        assertEquals(mAnalyzer.analyze(" "), result);
        assertEquals(mAnalyzer.analyze(null), result);
        assertEquals(mAnalyzer.analyze("abc"), result);
        assertEquals(mAnalyzer.analyze("abc de"), result);
        assertEquals(mAnalyzer.analyze("5a"), result);
        assertEquals(mAnalyzer.analyze("5 a"), result);
        assertEquals(mAnalyzer.analyze("a6"), result);
        assertEquals(mAnalyzer.analyze("a 6"), result);
        assertEquals(mAnalyzer.analyze("--5"), result);
        assertEquals(mAnalyzer.analyze("1,4"), result);
        assertEquals(mAnalyzer.analyze("1.4"), result);
        assertEquals(mAnalyzer.analyze("-5 ?5"), result);
        assertEquals(mAnalyzer.analyze("6"), result);
        assertEquals(mAnalyzer.analyze("-6"), result);
        assertEquals(mAnalyzer.analyze("1.4 5"), result);
    }

    @Test
    public void analyze_constantSequence() throws Exception {
        int result = R.string.analysis_result_constant_sequence;
        assertEquals(mAnalyzer.analyze("5 5"), result);
        assertEquals(mAnalyzer.analyze("-6 -6  -6"), result);
        assertEquals(mAnalyzer.analyze("0   0 0"), result);
    }

    @Test
    public void analyze_randomSequence() throws Exception {
        int result = R.string.analysis_result_random_sequence;
        assertEquals(mAnalyzer.analyze("5 6 3 -7"), result);
        assertEquals(mAnalyzer.analyze("5 5 6"), result);
        assertEquals(mAnalyzer.analyze("7 0 4"), result);
        assertEquals(mAnalyzer.analyze("5 6 0"), result);
        assertEquals(mAnalyzer.analyze("5 -5"), result);
    }

    @Test
    public void analyze_arithmeticSequence() throws Exception {
        int result = R.string.analysis_result_arithmetic_sequence;
        assertEquals(mAnalyzer.analyze("5 6 7"), result);
        assertEquals(mAnalyzer.analyze("-6 0 6 12"), result);
        assertEquals(mAnalyzer.analyze("-7 -8 -9 -10 -11"), result);
    }

    @Test
    public void analyze_geometricSequence() throws Exception {
        int result = R.string.analysis_result_geometric_sequence;
        assertEquals(mAnalyzer.analyze("5 10 20"), result);
        assertEquals(mAnalyzer.analyze("81 27  9 3"), result);
        assertEquals(mAnalyzer.analyze("-7 0 0 0   0"), result);
        assertEquals(mAnalyzer.analyze("-4 16 -64 256 -1024"), result);
        assertEquals(mAnalyzer.analyze("2401 49 1"), result);
        assertEquals(mAnalyzer.analyze("100020001 10001 1"), result);
    }
}