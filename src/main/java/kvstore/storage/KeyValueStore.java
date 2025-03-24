package kvstore.storage;
public interface KeyValueStore {
    void set(String key, String value);
    String get(String key);
    void delete(String key);
}