package production;

public class KeyValuePair<K, V> {
    K Key;
    V Value;
    KeyValuePair(K k, V v) {
        Key = k;
        Value = v;
    }
}