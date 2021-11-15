package main.java.console_app.event_adapters;

import main.java.console_app.ApplicationDriver;
import main.java.services.event_presentation.CalendarEventPresenter;
import main.java.services.event_presentation.EventInfo;

import java.util.ArrayList;
import java.util.List;

public class ConsoleEventPresenter implements CalendarEventPresenter {
    private final ApplicationDriver applicationDriver;

    public ConsoleEventPresenter(ApplicationDriver applicationDriver) {
        this.applicationDriver = applicationDriver;
    }

    /**
     * Given the event information in a DTO, present all events
     * by printing them on the console.
     * @param eventInfos list of information of event
     */
    @Override
    public void presentAllEvents(List<EventInfo> eventInfos) {
        List<String> eventFormattedInfo = new ArrayList<>();
        for (EventInfo ei : eventInfos) {

            String name = ei.getName();
            String startTime = ei.getStartTime().toString();
            String endTime = ei.getEndTime().toString();
            String tags = ei.getTags().toString();
            String dates = ei.getDates().toString();

            String output = "Event: " + name + ", "
                    + "start time = " + startTime + ", "
                    + "end time = " + endTime + ", "
                    + "tags = " + tags + ", "
                    + "dates = " + dates;
            eventFormattedInfo.add(output);
        }
        applicationDriver.printEvents(eventFormattedInfo);
    }
}
