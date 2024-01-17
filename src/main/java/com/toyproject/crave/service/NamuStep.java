package com.toyproject.crave.service;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.DTO.Page.NamuPageDTO;
import com.toyproject.crave.controller.ConfigController;
import com.toyproject.crave.service.algorithm.BFS;
import com.toyproject.crave.service.algorithm.DFS;
import com.toyproject.crave.service.method.Front;
import com.toyproject.crave.service.scope.All;
import com.toyproject.crave.service.scope.PersonOnly;
import libs.ThreadClass;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.*;

public abstract class NamuStep extends ThreadClass{


    public NamuPageDTO currentTarget;
    public Map<String, String> historyMap;
    public ArrayList<Deque<String>> foundRoute = new ArrayList<>();


    protected ConfigDTO config;
    public Scope scope;
    public Algorithm algorithm;
    public Method method;

    private static int count = 0;


    public NamuStep(String stepMethod, ConfigDTO config) {
        super(stepMethod);
        this.config = config;
        count++;
        this.historyMap = new HashMap<>();
        if (this.config.getScope() == ConfigController.EScope.PERSONONLY.ordinal()){
            scope = new PersonOnly();
        } else if (this.config.getScope() == ConfigController.EScope.ALL.ordinal()) {
            scope = new All();
        }

        if (this.config.getAlgorithm() == ConfigController.EAlgorithmType.BFS.ordinal()){
            algorithm = new BFS();
        }
        else if(this.config.getAlgorithm() == ConfigController.EAlgorithmType.DFS.ordinal()){
            algorithm = new DFS();
        }

        if (this.config.getMethod() == ConfigController.EMethodType.FRONT.ordinal()){
            method = new Front();
        }



    }
    public long duration() {
        Random rand = new Random();
        long min = 900;
        long max = 1550;
        return rand.nextLong() % (max - min + 1) + min * count;
    }



    protected abstract void insertPage (String result, String text);
    protected abstract Boolean processHtmlForResults(String html);

    protected abstract String makeUri(String name);

    public boolean blockDetection(String html) {
        return html.contains("<h1>비정상");
    }
    public String getHtml(String uri) throws UnsupportedEncodingException {
        try {


            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }
        catch (IOException | InterruptedException | IllegalArgumentException e) {
            System.out.println(uri);
            throw new RuntimeException(e);
        }
    }

}
