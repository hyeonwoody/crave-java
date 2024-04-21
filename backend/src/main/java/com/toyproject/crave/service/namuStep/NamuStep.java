package com.toyproject.crave.service.namuStep;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.DTO.Page.NamuPageDTO;
import com.toyproject.crave.controller.ConfigController;
import com.toyproject.crave.service.Algorithm;
import com.toyproject.crave.service.Method;
import com.toyproject.crave.service.Scope;
import com.toyproject.crave.service.algorithm.BFS;
import com.toyproject.crave.service.algorithm.DFS;
import com.toyproject.crave.service.method.Front;
import com.toyproject.crave.service.scope.All;
import com.toyproject.crave.service.scope.PersonOnly;
import libs.ThreadClass;
import lombok.Setter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public abstract class NamuStep extends ThreadClass{


    public NamuPageDTO currentTarget;
    public Map<String, String> historyMap;
    public static List<Deque<String>> foundRoute =
            Collections.synchronizedList(new ArrayList<>());
    

    @Setter
    protected ConfigDTO config;
    public Scope scope;
    public Algorithm algorithm;
    public Method method;

    private static int count = 0;
    public NamuStep(){
        super("UniverseStep");
        count++;
    }
    public NamuStep (String stepMethod){
        super(stepMethod);
        count++;
    }

    public NamuStep(String stepMethod, ConfigDTO config) {
        super(stepMethod);
        this.setConfig(config);
        count++;
    }
    public static long duration() {
        Random rand = new Random();
        long min = 900;
        long max = 1550;
        return rand.nextLong() % (max - min + 1) + min * count;
    }


    protected abstract void setCurrentTarget();

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

    protected void settings() {
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

    public void init(ConfigDTO config) {
        this.setConfig(config);
        this.settings();
        this.setCurrentTarget();
    }
}
