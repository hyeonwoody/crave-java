package libs;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventManager {
    private static final int MAX_LOG_MESSAGE = 1024;
    public enum LOG_LEVEL{
        ERROR,
        INFO,
        WARNING,
        SKIPABLE,
        DEBUG
    }

    private static void LogExecute (int level, String session, String func, int outputIndex, String time, String buffer){
        if (session == null){
            session = "NoName";
        }
        System.out.println(time + "|" + session + " | " + func + " | " + buffer);
    }
    private static void Output(int level, String session, String func, int outputIndex, String buffer) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(now);
        LogExecute(level, session, func, outputIndex, time, buffer);
    }

    public static void LogOutput(int level, String session, String func, int outputIndex, String format, Object... args) {
        if (format == null) {
            return;
        }

        String buffer = String.format(format, args);
        Output(level, session, func, outputIndex, buffer);
    }
}
