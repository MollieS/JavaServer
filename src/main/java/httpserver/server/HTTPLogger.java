package httpserver.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HTTPLogger {

    private final File logFile;

    public HTTPLogger(String logPath) {
        this.logFile = new File(logPath);
        System.out.println(logPath);
    }

    public void log(String request) {
        PrintWriter printWriter = null;
        try {
            createNewFile();
            printWriter = createPrintWriter();
        } catch (IOException e) {
//            throw new RuntimeException("Cannot write to logs", e);
            System.out.println(e);
        }
        CharSequence chars = request + "\n";
        printWriter.append(chars);
        printWriter.close();
    }

    private PrintWriter createPrintWriter() throws IOException {
        return new PrintWriter(new FileWriter(logFile, true));
    }

    private void createNewFile() throws IOException {
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
    }
}
