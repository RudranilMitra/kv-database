package kvstore.storage;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryStore implements KeyValueStore {

    private final ConcurrentMap<String, String> store = new ConcurrentHashMap<>();

    @Override
    public void set(String key, String value) {
        store.put(key, value);
    }

    @Override
    public String get(String key) {
        return store.get(key);
    }

    @Override
    public void delete(String key) {
        store.remove(key);
    }
}