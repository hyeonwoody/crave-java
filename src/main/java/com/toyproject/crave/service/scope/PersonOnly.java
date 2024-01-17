package com.toyproject.crave.service.scope;

import com.toyproject.crave.service.Scope;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonOnly implements Scope {

    final String elements [] = {"본명", "출생", "거주지", "본관", "학력", "부모", "친인척", "형제자매", "자녀", "병역", "별명", "종교", "서명"};
    @Override
    public Boolean validation(String html){
        String pattern = "<strong[^>]*\\s*>\\s*(" + String.join("|", elements) + ")\\s*<\\/strong>";
        Pattern strongElementPattern = Pattern.compile(pattern);

        // Use Matcher to find matches
        Matcher matcher = strongElementPattern.matcher(html);
        // Check if any of the elements exist
        return matcher.find();
    }
}
