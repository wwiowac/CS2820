package production;

/**
 * Generic KVP class
 * @param <K>
 * @param <V>
 * This already exists in some Java libraries, but it was decided to not take the dependency.
 */
public class KeyValuePair<K, V> {
    K Key;
    V Value;
    KeyValuePair(K k, V v) {
        Key = k;
        Value = v;
    }
}