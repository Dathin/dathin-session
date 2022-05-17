package io.github.dathin.dathinsession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class Ft {

    public static void main(String[] args) {
        //Make the project multimodule
        //Try to use swagger gen
        //create client
        //maintain source
        //create fts
        runContainers();
        stopContainer();
    }

    public static void stopContainer() {
        try {
            Process process = Runtime.getRuntime().exec("docker-compose -f docker-compose.yml -f docker-compose.ft.yml down -t 0");
            process.waitFor();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Success");

    }

    public static void runContainers() {
        try {
            long timeoutInSeconds = 30;
            var timeoutSecondsLater = LocalDateTime.now().plusSeconds(timeoutInSeconds);
            Process process = Runtime.getRuntime().exec("docker-compose -f docker-compose.yml -f docker-compose.ft.yml up");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            var appStarted = false;
            while (timeoutSecondsLater.isAfter(LocalDateTime.now())) {
                var line = bufferedReader.readLine();
                System.out.println(line);
                if (line.contains("Tomcat started on port(s): 9000 (http) with context path ''")) {
                    appStarted = true;
                    break;
                }
            }
            if(!appStarted) {
                throw new RuntimeException("Application failed to start or timeout");
            } else {
                System.out.println("Success");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
