package services.task_presentation;

import data_gateway.TaskReader;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoListInfoFromTaskReaders implements TodoListsInfo {

    private final List<TaskReader> taskReaders;

    public TodoListInfoFromTaskReaders(Map<Long, List<TaskReader>> taskReaders) {
        Map<Long, Map<Long, TaskReader>> taskMap = convertInnerListToMap(taskReaders);
        this.taskReaders = flattenMap(taskReaders);
    }

    private List<TaskReader> flattenMap(Map<Long, List<TaskReader>> taskReaders) {
        List<TaskReader> readers = new ArrayList<>();

        for (Map.Entry<Long, List<TaskReader>> e : taskReaders.entrySet())
            readers.addAll(e.getValue());

        return readers;
    }

    private Map<Long, Map<Long, TaskReader>> convertInnerListToMap(Map<Long, List<TaskReader>> readers) {
        Map<Long, Map<Long, TaskReader>> map = new HashMap<>();

        for (Map.Entry<Long, List<TaskReader>> e : readers.entrySet()) {
            Map<Long, TaskReader> todoListMap = new HashMap<>();
            for (TaskReader tr : e.getValue())
                todoListMap.put(tr.getId(), tr);
            map.put(e.getKey(), todoListMap);
        }
        return map;
    }

    @Override
    public List<TaskInfo> getAllTasks() {
        List<TaskInfo> taskInfos = new ArrayList<>();
        for (TaskReader tr : taskReaders)
            taskInfos.add(new TaskInfoFromTaskReader(tr));
        return taskInfos;
    }
}
