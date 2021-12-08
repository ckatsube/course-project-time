import datagateway.event.CalendarManager;
import datagateway.event.EventReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.eventfromtaskcreation.CalendarAnalyzer;
import services.eventfromtaskcreation.EventScheduler;
import services.taskpresentation.TaskInfo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalendarAnalyzerTest {
    TaskInfo taskInfo;
    CalendarAnalyzer scheduler;
    CalendarManager manager;

    @BeforeEach
    void setup() {
        taskInfo = new MockTaskInfo();
        manager = new MockCalendarManager();
        scheduler = new EventScheduler(manager);
    }

    private LocalDateTime toMinutes(LocalDateTime dateTime) {
        return dateTime.truncatedTo(ChronoUnit.MINUTES);
    }

    @Test
    void getAvailableTime() {
        LocalDateTime actual = scheduler.getAvailableTime(new ArrayList<>(), taskInfo.getDuration());
        LocalDateTime expected = LocalDateTime.now().plus(taskInfo.getDuration()).plusHours(1);
        assertEquals(toMinutes(expected), toMinutes(actual));
    }

    @Test
    void checkTimeAvailability() {
        LocalDateTime userSuggestedTime = LocalDateTime.of(2021, 11, 26, 10, 0);
        boolean actual = scheduler.isAvailable(userSuggestedTime.toLocalTime(), taskInfo.getDuration(), userSuggestedTime.toLocalDate());
        assertTrue(actual);
    }

    private static class MockTaskInfo implements TaskInfo {

        @Override
        public long getId() {
            return 207L;
        }

        @Override
        public String getName() {
            return "CSC207";
        }

        @Override
        public Duration getDuration() {
            return Duration.ofHours(2);
        }

        @Override
        public LocalDateTime getDeadline() {
            return LocalDateTime.of(2021, 11, 25, 12, 0);
        }

        @Override
        public List<String> getSubtasks() {
            List<String> subtasks = new ArrayList<>();
            subtasks.add("Project");
            subtasks.add("Quiz");
            return subtasks;
        }

        @Override
        public boolean getCompleted() {
            return false;
        }
    }

    private static class MockCalendarManager implements CalendarManager {

        @Override
        public long addEvent(String eventName, LocalDateTime startTime, LocalDateTime endTime, Set<String> tags, LocalDate date) {
            return 0;
        }

        @Override
        public long addEvent(long taskId, LocalDateTime startTime, Set<String> tags, LocalDate date) {
            return 0;
        }

        @Override
        public void deleteEvent(long eventId) {

        }

        @Override
        public void markEventAsCompleted(long eventId) {

        }

        @Override
        public List<EventReader> getAllEvents() {
            List<EventReader> events = new ArrayList<>();
            events.add(new MockEventReader());
            return events;
        }

        @Override
        public void updateName(long id, String newName) {

        }

        @Override
        public void updateStartTime(long id, LocalTime newStartTime) {

        }

        @Override
        public void updateEndTime(long id, LocalTime newEndTime) {

        }

        @Override
        public void addTag(long id, String tag) {

        }

        @Override
        public void removeTag(long id, String tag) {

        }

        @Override
        public void loadEvents(String filePath) {
        }

        @Override
        public void saveEvents(String savePath) {

        }
    }

    private static class MockEventReader implements EventReader {

        @Override
        public long getId() {
            return 0;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public LocalTime getStartTime() {
            return null;
        }

        @Override
        public LocalTime getEndTime() {
            return null;
        }

        @Override
        public Set<String> getTags() {
            return null;
        }

        @Override
        public Set<LocalDate> getDates() {
            return new HashSet<>();
        }

        @Override
        public boolean getCompleted() {
            return false;
        }
    }
}
