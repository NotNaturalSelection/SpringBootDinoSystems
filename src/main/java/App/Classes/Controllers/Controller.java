package App.Classes.Controllers;

import App.Classes.DataClasses.HashMapDecorator;
import App.Classes.DataClasses.Person;
import App.Classes.DataClasses.Record;
import App.Classes.Exceptions.PersonNotFoundException;
import App.Classes.Exceptions.RequestHasNoRequiredParametersException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    private HashMapDecorator<Person> persons = new HashMapDecorator<>(eitherObjectMap("data.json"));

    @GetMapping("/persons/{id:\\d+}")
    public Person readPerson(@PathVariable int id) {
        if (persons.getUnmodifiableMap().containsKey(id)) {
            return persons.get(id);
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    @PostMapping("/persons")
    public Person createPerson(@RequestParam(name = "name") String name) {
        if (name == null) {
            throw new RequestHasNoRequiredParametersException("name");
        }
        Person person = new Person(name);
        persons.add(person);
        return person;
    }

    @PutMapping("/persons/{id:\\d+}")
    public Person updatePerson(@PathVariable int id,
                               @RequestParam(name = "name") String name) {
        Person person = persons.get(id);
        person.setName(name);
        persons.update(id, person);
        return person;
    }

    @DeleteMapping("/persons/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Integer id) {
        persons.remove(id);
    }

    @GetMapping("/persons")
    public Map<Integer, Person> readPersons() {
        return persons.getUnmodifiableMap();
    }

    @GetMapping("/persons/search")
    public HashSet<Person> readPersonsByName(@RequestParam(name = "name") String name) {
        if (name == null) {
            throw new RequestHasNoRequiredParametersException("name");
        }
        HashSet<Person> correctPersons = new HashSet<>();
        persons.getUnmodifiableMap().forEach((integer, person) -> {
            if (person.getName().contains(name)) {
                correctPersons.add(person);
            }
        });
        return correctPersons;
    }

    @GetMapping("/persons/{personId:\\d+}/records/{recordId:\\d+}")
    public Record readRecord(@PathVariable int personId,
                             @PathVariable int recordId) {
        return persons.get(personId).recordsDecorator().get(recordId);
    }

    @PostMapping("/persons/{personId:\\d+}/records")
    public Record createRecord(@PathVariable int personId,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "phone") String phone) {
        if (name == null || phone == null) {
            throw new RequestHasNoRequiredParametersException("name", "phone");
        }
        Record record = new Record(name, phone);
        persons.get(personId).recordsDecorator().add(record);
        return record;
    }

    @PutMapping("/persons/{personId:\\d+}/records/{recordId:\\d+}")
    public Record updateRecord(@PathVariable int personId,
                               @PathVariable int recordId,
                               @RequestParam(name = "name") String name,
                               @RequestParam(name = "phone") String phone) {
        Record record = new Record(name, phone);
        persons.get(personId).recordsDecorator().update(recordId, record);
        return record;
    }

    @DeleteMapping("/persons/{personId:\\d+}/records/{recordId:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecord(@PathVariable int personId,
                             @PathVariable int recordId) {
        persons.get(personId).recordsDecorator().remove(recordId);
    }

    @GetMapping("/persons/{id:\\d+}/records")
    public Map<Integer, Record> readRecordsOfPerson(@PathVariable int id) {
        if (persons.getUnmodifiableMap().containsKey(id)) {
            return persons.get(id).recordsDecorator().getUnmodifiableMap();
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    @GetMapping("/persons/records/search")
    public HashSet<Record> readRecordsByPhone(@RequestParam(name = "phone") String phone) {
        if (phone == null) {
            throw new RequestHasNoRequiredParametersException("phone");
        }
        HashSet<Record> correctRecords = new HashSet<>();
        persons.getUnmodifiableMap().forEach((integer, person) -> person.getRecords().forEach((integer1, record) -> {
            if (record.getPhone().contains(phone)) {
                correctRecords.add(record);
            }
        }));
        return correctRecords;
    }

    public HashMap<Integer, Person> eitherObjectMap(String path) {
        try {
            return new ObjectMapper().readValue(new File(path), new TypeReference<HashMap<Integer, Person>>() {});
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    public HashMapDecorator<Person> getPersons() {
        return persons;
    }

}
