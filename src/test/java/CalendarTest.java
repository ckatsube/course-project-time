
import entity.Calendar;
import entity.Event;
import entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalendarTest {
    List<LocalDateTime> timesToIgnore;
    Calendar calendar;

    @BeforeEach
    void setUp() {
        timesToIgnore = new ArrayList<>();
        timesToIgnore.add(LocalDateTime.of(2021, 10, 14, 12, 0, 0));
        LocalDateTime startDate = LocalDateTime.of(2021, 10, 14, 14, 0, 0);
        LocalTime endTime = LocalTime.of(16, 0);
        Task task = new Task(0, "Math Homework");
        Event event = new Event(0, task.getId(), startDate, endTime);
        List<Event> eventlst = new ArrayList<>();
        eventlst.add(event);
        calendar = new Calendar("New Calendar", eventlst);
    }

    @Test
    void getName() {
        assertEquals("New Calendar", calendar.getName());
    }

}