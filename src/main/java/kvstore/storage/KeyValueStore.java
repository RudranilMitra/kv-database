package kvstore.storage;

import java.io.IOException;

public interface KeyValueStore {
    void set(String key, String value) throws IOException;
    String get(String key);
    void delete(String key) throws IOException;
}