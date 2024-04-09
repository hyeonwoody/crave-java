package com.toyproject.crave.service.namuStep;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.DTO.Page.NamuPageDTO;
import com.toyproject.crave.service.NamuStep;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BackStep extends NamuStep {

    public static NamuStep Create() {return new BackStep();}

    public BackStep(){
        super("BackStep");
    }


    public BackStep(ConfigDTO config) {
        super("FrontStep", config);
    }

    @Override
    protected Boolean processHtmlForResults (String html){
        MatchResult match;
        String input;
        String decodedString = "";

        Pattern hrefRegex = Pattern.compile("href=['\"]/backlink/([^'\"]+)['\"][^>]*>Next");
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

                System.out.println(decodedString);
                int indexOfHash = decodedString.indexOf('#');
                String result = (indexOfHash != -1) ? decodedString.substring(0, indexOfHash) : decodedString;
                insertPage (result, result);
            }
            searchMatcher.region(match.end(), html.length());
        }

        Pattern hrefRegey = Pattern.compile("href=['\"]/backlink/([^'\"]+)['\"][^>]*>Next");
        searchMatcher = hrefRegey.matcher(html);
        if (searchMatcher.find()) { //if there is next backlink page
            if (searchMatcher.groupCount() == 1) {
                input = searchMatcher.group(1);
                if (input.contains("?from=")) {
                    currentTarget.setName(input);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void setCurrentTarget() {
            this.currentTarget = new NamuPageDTO(this.config.getDestination());
    }

    protected void insertPage(String result, String text) {
        NamuPageDTO namuPage = new NamuPageDTO(result);
        namuPage.next.add(currentTarget);
    }

    @Override
    protected String makeUri(String name){
        final String prefix = "https://namu.wiki/w/";
        return prefix + name;
    }

    @Override
    protected void threadMain() throws InterruptedException, IOException {
        System.out.println("Front start");
        System.out.println(currentTarget.getName());
        while (getMThreadStatus() == EThreadStatus.THREAD_ACTIVE){
            try {
                TimeUnit.MILLISECONDS.sleep(duration());
            } catch (InterruptedException e) {
                throw e; // Re-throw the InterruptedException if needed
            }

            String uri = makeUri(currentTarget.getName());
            String html = getHtml(uri);
            if (blockDetection(html))
            {
                //eventManager logOutput
                System.out.println("block DETECT");
                continue;
            }
            processHtmlForResults (html);
            //System.out.println(html);
            //makeurl
            //getHtml
            //blockdetection
            //parseHtml
            //nexttarget
        }


    }
}
