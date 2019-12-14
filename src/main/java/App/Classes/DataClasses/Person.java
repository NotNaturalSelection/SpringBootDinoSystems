package App.Classes.DataClasses;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Person implements PreparedById {
    private int id;
    private String name;
    private HashMap<Integer, Record> records;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
        records = new HashMap<>();
    }

    public Person(String name, Record ... records) {
        this.name = name;
        this.records = new HashMap<>();
        Arrays.stream(records).forEach(record -> {
            recordsDecorator().add(record);
        });
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, Record> getRecords() {
        return records;
    }

    public void prepareById(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(name, person.name) &&
                Objects.equals(records, person.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, records);
    }

    public HashMapDecorator<Record> recordsDecorator() {
        return new HashMapDecorator<>(records);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", records=" + records +
                '}';
    }
}
