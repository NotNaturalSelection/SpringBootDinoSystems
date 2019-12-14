package App.Classes.DataClasses;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class HashMapDecorator<V extends PreparedById> {

    private final Map<Integer, V> map;
    private AtomicInteger counter;

    public HashMapDecorator(Map<Integer, V> map) {
        this.map = map;
        counter = new AtomicInteger(map.size());
    }

    public V get(Object key) {
        return map.get(key);
    }

    public void add(V value) {
        value.prepareById(counter.get());
        while (map.containsKey(counter.get())) {
            counter.incrementAndGet();
        }
        map.put(counter.getAndIncrement(), value);
    }

    public void update(int id, V value) {
        if (map.containsKey(id)) {
            map.replace(id, value);
        }
    }

    public void remove(Integer id) {
        map.remove(id);
    }

    public Map<Integer, V> getUnmodifiableMap() {
        return Collections.unmodifiableMap(map);
    }

    public boolean contains(V value){
        return map.containsValue(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashMapDecorator<?> that = (HashMapDecorator<?>) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return "HashMapDecorator{" +
                "map=" + map +
                '}';
    }
}
