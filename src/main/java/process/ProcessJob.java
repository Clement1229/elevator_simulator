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
        if (isElevatorService()) return null;
        Elevator.MoveState moveState = elevator.getMoveState();
        int currentFloor = elevator.getCurrentFloor();
        List<Integer> selectionFloorsInElevator = inputBuffer.getAllSelectionFloorInElevator();

        List<Integer> selectionFloorOutsideList = null;
        selectionFloorOutsideList = getSelectionFloorOutsideList(moveState);

        Set<Integer> set = Collections.synchronizedSet(new HashSet<Integer>());
        set.addAll(selectionFloorsInElevator);
        if (selectionFloorOutsideList != null) {
            set.addAll(selectionFloorOutsideList);
        }

        ArrayList<Integer> result = new ArrayList<Integer>();
        ArrayList<Integer> temp = new ArrayList<Integer>(set);
        Collections.sort(temp);
        if (moveState == Elevator.MoveState.UP) {
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
        } else if (moveState == Elevator.MoveState.DOWN) {
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
        } else if (moveState == Elevator.MoveState.NO_MOVE) {
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

    private boolean isElevatorService() {
        Elevator.ServiceState serviceState = elevator.getServiceState();
        if (serviceState == Elevator.ServiceState.STOP) {
            return true;
        }
        return false;
    }

    private List<Integer> getSelectionFloorOutsideList(Elevator.MoveState moveState) {
        List<Integer> selectionFloorOutsideList;
        if (moveState == Elevator.MoveState.UP) {
            selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside("UP");
        } else if (moveState == Elevator.MoveState.DOWN) {
            selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside("DOWN");
        }else {
            selectionFloorOutsideList = inputBuffer.getAllSelectionFloorOutside();
        }
        return selectionFloorOutsideList;
    }

    @Override
    public void run() {
        ArrayList<Integer> targetFloors = createTargetFloorList();
        elevator.setTargetFloors(targetFloors);
    }
}
