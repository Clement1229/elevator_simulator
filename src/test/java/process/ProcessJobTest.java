package process;

import middle.Elevator;
import middle.InputBuffer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by ksh on 2014-12-07.
 */
public class ProcessJobTest {

    private InputBuffer inputBuffer;
    private Elevator elevator;
    private ProcessJob processJob;
    private ArrayList result;

    @Test
    public void testInElevatorSelect1() throws Exception {
        elevator.setCurrentFloor(8);

        inputBuffer.selectFloorInElevator(1);
        ArrayList targetFloor = processJob.createTargetFloorList();
        result.add(1);

        assertThat(targetFloor, is(result));
    }

    @Before
    public void setUp() throws Exception {
        inputBuffer = new InputBuffer();
        elevator = new Elevator();
        processJob = new ProcessJob(inputBuffer, elevator);
        result = new ArrayList();
    }

    @Test
    public void testInElevatorSelect2_3_1()throws Exception{
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