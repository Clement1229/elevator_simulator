package process;

import middle.Elevator;
import middle.InputBuffer;

import java.util.*;

/**
 * Created by ksh on 2014-05-24.
 */
/*
엘레베이터가 도착할 목표 위치를 계산하는 클래스
 */
public class Process {
    private InputBuffer inputBuffer;
    private Elevator elevator;

    public Process() {
        inputBuffer = InputBuffer.getInstance();
        elevator = Elevator.getInstance();
    }

    public void process() {
        Timer timer = new Timer();
        ProcessJob job = new ProcessJob(inputBuffer,elevator);
        timer.schedule(job, 0, 34);
    }

}
