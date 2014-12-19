package process;

import middle.Elevator;
import middle.InputBuffer;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by ksh on 2014-12-07.
 */
public class ProcessJobTest {
    @Test
    public void testInElevatorSelect1() throws Exception {
        InputBuffer inputBuffer = new InputBuffer();
        Elevator elevator = new Elevator();
        ProcessJob processJob =new ProcessJob(inputBuffer,elevator);
        ArrayList result = new ArrayList();

        elevator.setCurrentFloor(8);

        inputBuffer.selectFloorInElevator(1);
        ArrayList targetFloor = processJob.createTargetFloorList();
        result.add(1);

        assertThat(targetFloor, is(result));
    }

    @Test
    public void testInElevatorSelect2_3_1()throws Exception{
        InputBuffer inputBuffer = new InputBuffer();
        Elevator elevator = new Elevator();
        ProcessJob processJob =new ProcessJob(inputBuffer,elevator);
        ArrayList result = new ArrayList();

        elevator.setCurrentFloor(8);

        inputBuffer.selectFloorInElevator(2);
        inputBuffer.selectFloorInElevator(3);
        inputBuffer.selectFloorInElevator(1);
        ArrayList targetFloor = processJob.createTargetFloorList();
        result.add(3);
        result.add(2);
        result.add(1);

        assertThat(targetFloor, is(result));
    }
}
