//package com.toyproject.crave.configuration.ufw;
//
//import jakarta.annotation.PreDestroy;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class UfwShutdown {
//
//    @PreDestroy
//    public void onShutdown() {
//        // Run the script to close ports when the application stops
//        runCommand("sudo", "scripts/ufw_rules.sh", "stop");
//    }
//
//    private void runCommand(String... command) {
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder(command);
//            Process process = processBuilder.start();
//            process.waitFor();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}