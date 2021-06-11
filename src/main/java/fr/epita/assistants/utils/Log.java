package fr.epita.assistants.utils;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    private static Logger LOGGER = null;

    static {
        LOGGER = Logger.getLogger("com.logicbig");
        LOGGER.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            private static final String FORMAT = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(FORMAT, new Date(lr.getMillis()), lr.getLevel().getLocalizedName(),
                        lr.getMessage());
            }
        });
        LOGGER.addHandler(handler);
    }

    private static String constructString(Object... params) {
        var stringBuilder = new StringBuilder();
        for (Object obj : params) {
            var str = obj == null ? "null" : obj.toString();
            stringBuilder.append(str);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    public static void log(Object... params) {
        LOGGER.info(constructString(params));
    }

    public static void err(Object... params) {
        LOGGER.log(Level.SEVERE, constructString(params));
    }
}
