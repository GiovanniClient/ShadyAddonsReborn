package cheaters.get.banned.features.routines.actions;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.routines.RoutineElementData;
import cheaters.get.banned.features.routines.RoutineRuntimeException;

public class LeaveServerAction extends Action {

    public LeaveServerAction(RoutineElementData data) {
        super(data);
    }

    @Override
    public void doAction() throws RoutineRuntimeException {
        Shady.mc.theWorld.sendQuittingDisconnectingPacket();
    }
}
