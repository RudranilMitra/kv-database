package kvstore.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WriteAheadLog {
    private final String logFilePath;
    private PrintWriter writer;
    private FileOutputStream fileOutputStream;

    public WriteAheadLog(String logFilePath) throws IOException {
        this.logFilePath = logFilePath;
        fileOutputStream = new FileOutputStream(logFilePath, true);
        writer = new PrintWriter(fileOutputStream, true);
    }

    public void append(String operation, String key, String value) throws IOException {
        if (value != null) {
            writer.write(operation + " " + key + " " + value);
        } else {
            writer.write(operation + " " + key);
        }
        fileOutputStream.getChannel().force(true);
    }

    public List<String> readAllLines() throws IOException {
        return Files.readAllLines(Paths.get(logFilePath));
    }

    public void close() {
        writer.close();
    }
}
