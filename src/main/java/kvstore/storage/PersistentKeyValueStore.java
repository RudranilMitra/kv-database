package kvstore.storage;

import java.io.IOException;

public class PersistentKeyValueStore implements KeyValueStore {
    private final InMemoryStore inMemoryStore = new InMemoryStore();
    private final WriteAheadLog writeAheadLog;

    public PersistentKeyValueStore(String logFilePath) throws IOException {
        writeAheadLog = new WriteAheadLog(logFilePath);
        replayLog();
    }

    @Override
    public void set(String key, String value) throws IOException {
        writeAheadLog.append("SET", key, value);
        inMemoryStore.set(key, value);
    }

    @Override
    public String get(String key) {
        return inMemoryStore.get(key);
    }

    @Override
    public void delete(String key) throws IOException {
        writeAheadLog.append("DELETE", key, null);
        inMemoryStore.delete(key);
    }

    private void replayLog() {
        try {
            for (String line : writeAheadLog.readAllLines()) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String operation = parts[0];
                    String key = parts[1];
                    if (operation.equalsIgnoreCase(parts[0])) {
                        String value = line.substring(line.indexOf(key) + key.length()).trim();
                        inMemoryStore.set(key, value);
                    } else if (operation.equalsIgnoreCase(parts[0])) {
                        inMemoryStore.delete(key);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to replay Write Ahead Log: " + e.getMessage());
        }
    }
}
