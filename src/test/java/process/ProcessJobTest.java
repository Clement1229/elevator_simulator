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
        InputBuffer inputBuffer = InputBuffer.getInstance();
        Elevator elevator = Elevator.getInstance();
        ProcessJob processJob =new ProcessJob(inputBuffer,elevator);
        ArrayList result = new ArrayList();

        inputBuffer.selectFloorInElevator(1);
        ArrayList targetFloor = processJob.createTargetFloorList();
        result.add(1);

        assertThat(targetFloor,is(result));
    }
}
