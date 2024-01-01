package com.toyproject.crave.service.namuStep;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.DTO.Page.NamuPageDTO;
import com.toyproject.crave.controller.ConfigController;
import com.toyproject.crave.service.NamuStep;

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


    protected boolean resultIsDestionation(String result){
        if (config.getMethod() == ConfigController.EMethodType.FRONT.ordinal())
            return result.equals(ConfigController.getDestination());
        return false;
    }

    @Override
    protected void insertPage (String result){
        if (!historyMap.containsKey(result)
            &&!result.contains("분류")
                &&!result.contains("/")
                &&!result.contains("&")
                &&!result.contains("?")
                &&!result.equals(currentTarget.getName())
            ) {
            historyMap.put(result, currentTarget.getName());
            NamuPageDTO namuPage = new NamuPageDTO(result);
            namuPage.setStage(currentTarget.getStage()+1);
            currentTarget.next.add(namuPage);
            namuPage.prev.add(currentTarget);
            namuPage.setTmp(currentTarget);
        }
    }


    @Override
    protected Boolean processHtmlForResults (String html){
        MatchResult match;
        String input;
        String decodedString = "";

        Pattern hrefRegex = Pattern.compile("href=['\"]/w/([^'\"]*)['\"]");
        Matcher searchMatcher = hrefRegex.matcher(html);

        while (searchMatcher.find()){
            match = searchMatcher.toMatchResult();

            if (match.groupCount() == 1){
                input = match.group(1);
                StringBuilder resultBuilder = new StringBuilder();
                try {
                    decodedString = URLDecoder.decode(input, StandardCharsets.UTF_8.name());
                }
                catch (IllegalArgumentException | UnsupportedEncodingException e) {
                    e.printStackTrace(); // Handle the exception according to your requirements
                }

                int indexOfHash = decodedString.indexOf('#');
                String result = (indexOfHash != -1) ? decodedString.substring(0, indexOfHash) : decodedString;

                insertPage (result);
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

    protected void moveTarget() throws UnsupportedEncodingException {
        currentTarget = algorithm.moveTarget(currentTarget);
    }

    public void routine() throws UnsupportedEncodingException {
        if (resultIsDestionation(currentTarget.getName())){
            NamuPageDTO tmp = currentTarget;
            Deque<String> route = new ArrayDeque<>();
            route = method.createRoute(currentTarget);

            foundRoute.add(route);
            return;
        }
        String uri = makeUri(currentTarget.getName());
        String html = getHtml(uri);

        if (blockDetection(html))
        {
            //eventManager logOutput
            System.out.println("block DETECT");
            return;
        }
        if (scope.validation(html)){
            processHtmlForResults (html);
        }
    }

    @Override
    protected void threadMain() throws InterruptedException, UnsupportedEncodingException {
        while (getMThreadStatus() == EThreadStatus.THREAD_ACTIVE){
            try {
                Thread.sleep(duration());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            routine();
            moveTarget();
        }
    }
}
