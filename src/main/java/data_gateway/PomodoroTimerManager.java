package data_gateway;

import entity.PomodoroTimer;

public class PomodoroTimerManager {

    public PomodoroTimer createPomodoroTimer(int workTime, int breakTime) {
        return new PomodoroTimer(workTime, breakTime);
    }
}
