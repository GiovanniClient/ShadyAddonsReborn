package cheaters.get.banned.gui.polyconfig;

import cc.polyfrost.oneconfig.hud.SingleTextHud;

public class Hud extends SingleTextHud {
    public Hud() {
        super("Test", true);
    }

    @Override
    public String getText(boolean example) {
        return "I'm an example HUD";
    }
}
