//package com.toyproject.crave.configuration.ufw;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UfwConfig implements CommandLineRunner {
//
//    @Override
//    public void run(String... args) throws Exception {
//        runCommand("sudo", "scripts/ufw_rules.sh", "start")
//;    }
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
