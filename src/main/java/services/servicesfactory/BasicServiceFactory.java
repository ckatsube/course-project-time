package services.servicesfactory;


import datagateway.event.CalendarManager;
import datagateway.task.TodoListManager;
import services.eventcreation.CalendarEventCreationBoundary;
import services.eventcreation.EventAdder;
import services.eventcreation.EventSaver;
import services.eventfromtaskcreation.CalendarAnalyzer;
import services.eventfromtaskcreation.EventScheduler;
import services.eventpresentation.CalendarEventDisplayBoundary;
import services.eventpresentation.CalendarEventPresenter;
import services.eventpresentation.CalendarEventRequestBoundary;
import services.eventpresentation.EventGetter;
import services.eventpresentation.EventOutputter;
import services.taskcreation.TaskAdder;
import services.taskcreation.TaskSaver;
import services.taskcreation.TodoListTaskCreationBoundary;
import services.taskpresentation.TaskGetter;
import services.taskpresentation.TaskOutputter;
import services.taskpresentation.TodoListDisplayBoundary;
import services.taskpresentation.TodoListPresenter;
import services.taskpresentation.TodoListRequestBoundary;
import services.updateentities.EventUpdater;
import services.updateentities.TaskUpdater;
import services.updateentities.UpdateEventBoundary;
import services.updateentities.UpdateTaskBoundary;

public class BasicServiceFactory implements ServicesFactory {

    private final CalendarManager eventRepository;
    private final TodoListManager taskRepository;

    private CalendarAnalyzer cachedAnalyzer;
    private CalendarEventCreationBoundary cachedEventCreator;
    private CalendarEventDisplayBoundary cachedEventOutputter;
    private CalendarEventRequestBoundary cachedEventGetter;
    private EventSaver cachedEventSaver;
    private UpdateEventBoundary cachedEventUpdater;
    private TodoListTaskCreationBoundary cachedTaskCreator;
    private TodoListDisplayBoundary cachedTaskOutputter;
    private TodoListRequestBoundary cachedTaskGetter;
    private TaskSaver cachedTaskSaver;
    private UpdateTaskBoundary cachedTaskUpdater;


    public BasicServiceFactory(RepositoryFactory repositoryFactory) {
        this.eventRepository = repositoryFactory.makeEventRepository();
        this.taskRepository = repositoryFactory.makeTaskRepository();
    }

    @Override
    public CalendarAnalyzer makeCalendarAnalyzer() {
        if (cachedAnalyzer == null)
            cachedAnalyzer = new EventScheduler(eventRepository);
        return cachedAnalyzer;
    }

    @Override
    public CalendarEventCreationBoundary makeEventCreator() {
        if (cachedEventCreator == null)
            cachedEventCreator = new EventAdder(eventRepository);
        return cachedEventCreator;
    }

    @Override
    public CalendarEventDisplayBoundary makeEventOutputter(CalendarEventPresenter eventPresenter) {
        if (cachedEventOutputter == null)
            cachedEventOutputter = new EventOutputter(eventRepository, eventPresenter);
        return cachedEventOutputter;
    }

    @Override
    public CalendarEventRequestBoundary makeEventGetter() {
        if (cachedEventGetter == null)
            cachedEventGetter = new EventGetter(eventRepository);
        return cachedEventGetter;
    }

    @Override
    public EventSaver makeEventSaver() {
        if (cachedEventSaver == null)
            cachedEventSaver = new EventSaver(eventRepository);
        return cachedEventSaver;
    }

    @Override
    public UpdateEventBoundary makeEventUpdater() {
        if (cachedEventUpdater == null)
            cachedEventUpdater = new EventUpdater(eventRepository);
        return cachedEventUpdater;
    }

    @Override
    public TodoListTaskCreationBoundary makeTaskCreator() {
        if (cachedTaskCreator == null)
            cachedTaskCreator = new TaskAdder(taskRepository);
        return cachedTaskCreator;
    }

    @Override
    public TodoListDisplayBoundary makeTaskOutputter(TodoListPresenter taskPresenter) {
        if (cachedTaskOutputter == null) {
            cachedTaskOutputter = new TaskOutputter(taskRepository, taskPresenter);
        }
        return cachedTaskOutputter;
    }

    @Override
    public TodoListRequestBoundary makeTaskGetter() {
        if (cachedTaskGetter == null)
            cachedTaskGetter = new TaskGetter(taskRepository);
        return cachedTaskGetter;
    }

    @Override
    public TaskSaver makeTaskSaver() {
        if (cachedTaskSaver == null)
            cachedTaskSaver = new TaskSaver(taskRepository);
        return cachedTaskSaver;
    }

    @Override
    public UpdateTaskBoundary makeTaskUpdater() {
        if (cachedTaskUpdater == null)
            cachedTaskUpdater = new TaskUpdater(taskRepository);
        return cachedTaskUpdater;
    }
}
