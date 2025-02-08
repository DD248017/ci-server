package dd2480.group17.ciserver.application;

import dd2480.group17.ciserver.domain.Build;

public class CIServerApplication {
    public static void main(String[] args) {
        Build build = new Build();
        try {
            build.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}