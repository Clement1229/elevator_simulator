package process;

import middle.Elevator;
import middle.InputBuffer;

import java.util.*;

/**
* Created by ksh on 2014-12-07.
*/
class ProcessJob extends TimerTask {
    private InputBuffer inputBuffer;
    private Elevator elevator;
    public ProcessJob(InputBuffer _inputBuffer, Elevator _elevator){
        inputBuffer=_inputBuffer;
        elevator=_elevator;
    }

    public ArrayList<Integer> createTargetFloorList() {

        Elevator.MoveState moveState = elevator.getMoveState();
        int currentFloor = elevator.getCurrentFloor();


        ArrayList<Integer> temp = getDeDuplicationSelectionFloorList(moveState);
        Collections.sort(temp);

        ArrayList<Integer> result = new ArrayList<Integer>();
        List<Integer> selectionFloorOutsideList = null;

        if (isElevatorUp(moveState)) {
            for (int i = 0; i < temp.size(); i++) {
                int targetFloor = temp.get(i);
                if (currentFloor <= targetFloor) {
                    result.add(targetFloor);
                }
            }
            for (int i = temp.size() - 1; i >= 0; i--) {
                int targetFloor = temp.get(i);
                if (!result.contains(targetFloor) && currentFloor > targetFloor) {
                    result.add(targetFloor);
                }
            }
            if(inputBuffer.getAllSelectionFloorOutside("UP").isEmpty()){
                selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside("DOWN");
                Collections.sort(selectionFloorOutsideList);
                for (int i = 0; i < selectionFloorOutsideList.size(); i++) {
                    int targetFloor = selectionFloorOutsideList.get(i);
                    if (!result.contains(targetFloor) && targetFloor > currentFloor) {
                        result.add(0,targetFloor);
                    }
                }
            }
        } else if (isElevatorDown(moveState)) {
            for (int i = temp.size() - 1; i >= 0; i--) {
                int targetFloor = temp.get(i);
                if (currentFloor >= targetFloor) {
                    result.add(targetFloor);
                }
            }
            for (int i = 0; i < temp.size(); i++) {
                int targetFloor = temp.get(i);
                if (!(result.contains(targetFloor)) && currentFloor < targetFloor) {
                    result.add(targetFloor);
                }
            }
            if(inputBuffer.getAllSelectionFloorOutside("DOWN").isEmpty()){
                selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside("UP");
                Collections.sort(selectionFloorOutsideList);
                for (int i = selectionFloorOutsideList.size()-1; i >=0 ; i--) {
                    int targetFloor = selectionFloorOutsideList.get(i);
                    if (!result.contains(targetFloor) && targetFloor < currentFloor) {
                        result.add(0,targetFloor);
                    }
                }
            }
        } else if (isElevatorNoMove(moveState)) {
            if (currentFloor == 1) {
                for (int i = 0; i < temp.size(); i++) {
                    int targetFloor = temp.get(i);
                    result.add(targetFloor);
                }
            }else {
                for (int i = temp.size()-1; i >=0 ; i--) {
                    int targetFloor = temp.get(i);
                    result.add(targetFloor);
                }
            }
        }
        return result;
    }

    private boolean isElevatorUp(Elevator.MoveState moveState) {
        return moveState == Elevator.MoveState.UP;
    }

    private boolean isElevatorDown(Elevator.MoveState moveState) {
        return moveState == Elevator.MoveState.DOWN;
    }

    private boolean isElevatorNoMove(Elevator.MoveState moveState) {
        return moveState == Elevator.MoveState.NO_MOVE;
    }

    private ArrayList<Integer> getDeDuplicationSelectionFloorList(Elevator.MoveState moveState) {
        Set<Integer> set = Collections.synchronizedSet(new HashSet<Integer>());
        set.addAll(inputBuffer.getAllSelectionFloorInElevator());
        if (getSelectionFloorOutsideList(moveState) != null) {
            set.addAll(getSelectionFloorOutsideList(moveState));
        }
        return new ArrayList<Integer>(set);
    }

    private List<Integer> getSelectionFloorOutsideList(Elevator.MoveState moveState) {
        List<Integer> selectionFloorOutsideList;
        if (isElevatorUp(moveState)) {
            selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside("UP");
        } else if (isElevatorDown(moveState)) {
            selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside("DOWN");
        }else {
            selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside();
        }
        return selectionFloorOutsideList;
    }

    @Override
    public void run() {
        ArrayList<Integer> targetFloors = null;
        if (!elevator.isService())
            targetFloors = createTargetFloorList();
        elevator.setTargetFloors(targetFloors);
    }
}
