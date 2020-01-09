package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Данный класс обязан использовать StreamApi из функционала Java 8. Функциональность должна быть идентична
 * {@link SimpleTextStatisticsAnalyzer}.
 */
public class StreamApiTextStatisticsAnalyzer implements TextStatisticsAnalyzer {
    @Override
    public int countSumLengthOfWords(String text) {
        return getWords(text).stream().mapToInt(word->word.length()).sum();
    }

    @Override
    public int countNumberOfWords(String text) {
        return (int) Pattern.compile("([A-z]+(-[A-z]+(-[A-z])?)?)").matcher(text).
                results().count();
    }

    @Override
    public int countNumberOfUniqueWords(String text) {
        return (int) getWords(text).stream().distinct().count();
    }

    @Override
    public List<String> getWords(String text) {
        return Pattern.compile("([A-z]+(-[A-z]+(-[A-z])?)?)").matcher(text)
                .results()
                .map(matcher -> matcher.group())
                .collect(Collectors.toList());
    }

//   чтобы использовать метод results(), изменил версию Java на 1.9 в файле pom.xml
    @Override
    public Set<String> getUniqueWords(String text) {
        return Pattern.compile("([A-z]+(-[A-z]+(-[A-z])?)?)").matcher(text)
                .results()
                .map(matcher -> matcher.group())
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        return Pattern.compile("([A-z]+(-[A-z]+(-[A-z])?)?)").matcher(text)
                .results()
                .collect(Collectors.toMap(match -> match.group(), value -> 1, (value,otherValue) -> (value + otherValue)));
    }

    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        return getWords(text).stream().sorted((s1,s2) -> {
            switch (direction) {
                case ASC:
                    return s1.length() - s2.length();
                case DESC:
                    return s2.length() - s1.length();
                default:
                    return 0;
        }
    }).collect(Collectors.toList());
}
}
