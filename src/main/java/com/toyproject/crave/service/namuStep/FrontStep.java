package com.toyproject.crave.service.namuStep;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.DTO.Page.NamuPageDTO;
import com.toyproject.crave.controller.ConfigController;
import com.toyproject.crave.service.NamuStep;
import libs.EventManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.*; // searching for links

public class FrontStep extends NamuStep {




    public FrontStep(ConfigDTO config) {
        super("FrontStep", config);
        currentTarget = new NamuPageDTO(config.getOrigin());
        currentTarget.setStage(0);
    }

    protected boolean resultIsDate (String result){
        Pattern datePattern = Pattern.compile("((\\d{4}년)?(\\d{1,2}월)?(\\d{1,2}일)?)");
        if (datePattern.matcher(result).matches()) {
            return true;
        }

        // Pattern for date with space in the format: (MM월)? (dd일)?
        Pattern dateWithSpacePattern = Pattern.compile("(\\d{1,2}월)? (\\d{1,2}일)?");
        if (dateWithSpacePattern.matcher(result).matches()) {
            return true;
        }

        return false;
    }

    protected boolean resultIsDestionation(String result){
        if (config.getMethod() == ConfigController.EMethodType.FRONT.ordinal())
            return result.equals(ConfigController.getDestination());
        return false;
    }

    @Override
    protected void insertPage (String result, String text){
        if (resultIsDestionation(result)){
            Deque<String> route = new ArrayDeque<>();
            route = method.createRoute(currentTarget);
            route.addLast(result);
            foundRoute.add(route);
            currentTarget = algorithm.justMoveTarget();
            return;
        }
        if (historyMap.containsKey(result)
            ||result.contains("분류")
                ||result.contains("/")
                ||result.contains("&")
                ||result.contains("?")
                ||result.equals(currentTarget.getName())
            ) {
            return;
        }

        if (resultIsDate(result)){
            return;
        }

        historyMap.put(result, currentTarget.getName());
        NamuPageDTO namuPage = new NamuPageDTO(result);
        namuPage.setDisplayName(text);
        namuPage.setStage(currentTarget.getStage()+1);
        currentTarget.next.add(namuPage);
        namuPage.prev.add(currentTarget);
        namuPage.setTmp(currentTarget);
        return;
    }


    @Override
    protected Boolean processHtmlForResults (String html){
        MatchResult match;
        String input;
        String text;
        String decodedString = "";

        Pattern hrefRegex = Pattern.compile("<a[^>]*\\s*href=['\"]/w/([^'\"]*)['\"][^>]*>(.*?)<\\/a>");
        Matcher matcher = hrefRegex.matcher(html);
        Matcher searchMatcher = hrefRegex.matcher(html);

        while (searchMatcher.find()){
            match = searchMatcher.toMatchResult();

            if (0 < match.groupCount() && match.groupCount() <= 2){
                input = match.group(1);
                text = match.group(2);
                StringBuilder resultBuilder = new StringBuilder();
                try {
                    decodedString = URLDecoder.decode(input, StandardCharsets.UTF_8.name());
                }
                catch (IllegalArgumentException | UnsupportedEncodingException e) {
                    e.printStackTrace(); // Handle the exception according to your requirements
                }

                int indexOfHash = decodedString.indexOf('#');
                String result = (indexOfHash != -1) ? decodedString.substring(0, indexOfHash) : decodedString;

                insertPage (result, text);
            }
            searchMatcher.region(match.end(), html.length());
        }
        return true;
    }
    @Override
    protected String makeUri(String name){
        final String prefix = "https://namu.wiki/w/";
        return prefix + name;
    }
    protected void printCurrent(){
        EventManager.LogOutput(EventManager.LOG_LEVEL.SKIPABLE.ordinal(), mName, new Object(){}.getClass().getEnclosingMethod().getName(), 0, "%s: (%d)", currentTarget.getName(), currentTarget.getStage());
    }
    @Override
    protected void threadMain() throws InterruptedException, UnsupportedEncodingException {
        while (getMThreadStatus() == EThreadStatus.THREAD_ACTIVE){
            try {
                Thread.sleep(duration());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //printCurrent();

            String uri = makeUri(currentTarget.getName());
            String html = getHtml(uri);

            if (blockDetection(html))
            {
                //eventManager logOutput
                EventManager.LogOutput(EventManager.LOG_LEVEL.ERROR.ordinal(), mName, new Object(){}.getClass().getEnclosingMethod().getName(), 0, "Block Detected");
                continue;
            }
            if (scope.validation(html)){
                processHtmlForResults (html);
            }
            currentTarget = algorithm.moveTarget(currentTarget);
        }
    }
}
