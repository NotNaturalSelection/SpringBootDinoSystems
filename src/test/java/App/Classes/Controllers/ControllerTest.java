package App.Classes.Controllers;

import App.Classes.DataClasses.HashMapDecorator;
import App.Classes.DataClasses.Person;
import App.Classes.DataClasses.Record;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.*;

public class ControllerTest {
    HashMapDecorator<Person> data;
    Person testerVanya;
    Person testerValera;
    Person testerValeraWithRecords;
    Record recordVanya =  new Record("vanya", "+79510245232");
    Record recordMisha =  new Record("misha", "+79510247236");
    Record recordSerega =  new Record("serega", "+79512225932");

    @Before
    public void setUp() {
        testerVanya = new Person("vanya");
        testerValera = new Person("valera");
        testerValeraWithRecords = new Person(
                "valera",
                recordVanya,
                recordMisha,
                recordSerega);
        data = new HashMapDecorator<>(new HashMap<>());
        data.add(testerValera);
        data.add(testerValeraWithRecords);
    }

    //personsTesting

    @Test
    public void testReadPerson_ReturnTesterValera() {
        //arrange
        Controller controller = new Controller();
        controller.getPersons().add(testerValera);

        //act
        Person actual = controller.readPerson(0);

        //assert
        assertEquals(testerValera, actual);
    }

    @Test
    public void testCreatePerson_ContainsReturnTrue() {
        //arrange
        Controller controller = new Controller();

        //act
        controller.createPerson(testerValera.getName());

        //assert
        assertTrue(controller.getPersons().contains(testerValera));
    }

    @Test
    public void testUpdatePerson_ReturnTesterValera() {
        //arrange
        Controller controller = new Controller();
        controller.getPersons().add(new Person("igor"));

        //act
        controller.updatePerson(0, testerValera.getName());
        Person actual = controller.getPersons().get(0);

        //assert
        assertEquals(testerValera, actual);
    }

    @Test
    public void testDeletePerson_ContainsReturnFalse() {
        //arrange
        Controller controller = new Controller();
        controller.getPersons().add(testerValera);

        //act
        controller.deletePerson(0);

        //assert
        assertFalse(controller.getPersons().contains(testerValera));
    }

    @Test
    public void testReadPersons_ReturnBothValera() {
        //arrange
        Controller controller = new Controller();
        controller.getPersons().add(testerValera);
        controller.getPersons().add(testerValeraWithRecords);

        //act
        HashMapDecorator<Person> actual = new HashMapDecorator<>(controller.readPersons());

        //assert
        assertEquals(data, actual);
    }

    @Test
    public void testReadPersonsByName_ReturnValeraAndVanyaSet() {
        //arrange
        Controller controller = new Controller();
        controller.getPersons().add(testerValera);
        controller.getPersons().add(testerVanya);

        //act
        HashSet<Person> actualSet = controller.readPersonsByName("va");

        //assert
        HashSet<Person> expectedSet = new HashSet<>();
        expectedSet.add(testerValera);
        expectedSet.add(testerVanya);
        assertEquals(expectedSet, actualSet);
    }

    @Test
    public void testReadPersonsByName_ReturnVanyaSet() {
        //arrange
        Controller controller = new Controller();
        controller.getPersons().add(testerValera);
        controller.getPersons().add(testerVanya);

        //act
        HashSet<Person> actualSet = controller.readPersonsByName("van");

        //assert
        HashSet<Person> expectedSet = new HashSet<>();
        expectedSet.add(testerVanya);
        assertEquals(expectedSet, actualSet);
    }

    //recordsTesting

    @Test
    public void testReadRecord_ReturnRecordVanya() {
        //arrange
        Controller controller = new Controller();
        controller.getPersons().add(testerValeraWithRecords);

        //act
        Record actual = controller.readRecord(0, 0);

        //assert
        assertEquals(controller.getPersons().get(0).recordsDecorator().get(0), actual);
    }

    @Test
    public void testCreateRecord_ContainsReturnTrue() {
        //arrange
        Controller controller = new Controller();
        Record nikita = new Record("nikita", "+79510294332");
        testerValeraWithRecords.recordsDecorator().add(nikita);
        controller.getPersons().add(testerValeraWithRecords);

        //act
        controller.createRecord(0, nikita.getName(), nikita.getPhone());

        //assert
        assertTrue(controller.getPersons().get(0).recordsDecorator().contains(nikita));
    }

    @Test
    public void testUpdateRecord_ReturnRecordNikita() {
        //arrange
        Controller controller = new Controller();
        Record nikita = new Record("nikita", "+79510294332");
        testerValeraWithRecords.recordsDecorator().add(new Record("vitya", "+78987654332"));
        controller.getPersons().add(testerValeraWithRecords);

        //act
        controller.updateRecord(0, 3, nikita.getName(), nikita.getPhone());

        //assert
        assertEquals(nikita, controller.getPersons().get(0).recordsDecorator().get(3));
    }

    @Test
    public void testDeleteRecord_ContainsReturnFalse() {
        //arrange
        Controller controller = new Controller();
        Record nikita = new Record("nikita", "+79510294332");
        testerValeraWithRecords.recordsDecorator().add(nikita);
        controller.getPersons().add(testerValeraWithRecords);

        //act
        controller.deleteRecord(0, 3);

        //assert
        assertFalse(controller.getPersons().get(0).recordsDecorator().contains(nikita));
    }

    @Test
    public void testReadRecordsOfPerson_ReturnAllRecordsOfTesterValeraWithRecords() {
        //arrange
        Controller controller = new Controller();
        controller.getPersons().add(testerValeraWithRecords);

        //act
        Map<Integer, Record> actual = controller.readRecordsOfPerson(0);

        //assert
        assertEquals(testerValeraWithRecords.recordsDecorator().getUnmodifiableMap(), actual);
    }

    @Test
    public void testReadRecordsByPhone_ReturnVanyaAndMishaSet() {
        //arrange
        Controller controller = new Controller();
        controller.getPersons().add(testerValeraWithRecords);

        //act
        HashSet<Record> actualSet = controller.readRecordsByPhone("79510");

        //assert
        HashSet<Record> expectedSet = new HashSet<>();
        expectedSet.add(recordVanya);
        expectedSet.add(recordMisha);
        assertEquals(expectedSet, actualSet);
    }

    @Test
    public void testReadRecordsByPhone_Return3RecordsSet() {
        //arrange
        Controller controller = new Controller();
        controller.getPersons().add(testerValeraWithRecords);

        //act
        HashSet<Record> actualSet = controller.readRecordsByPhone("7951");

        //assert
        HashSet<Record> expectedSet = new HashSet<>();
        expectedSet.add(recordVanya);
        expectedSet.add(recordMisha);
        expectedSet.add(recordSerega);
        assertEquals(expectedSet, actualSet);
    }
}