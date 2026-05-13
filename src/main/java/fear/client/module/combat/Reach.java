package fear.client.module.combat;
import fear.client.module.Module;
public class Reach extends Module {
    public static double distance = 4.5;
    public static boolean enabledStatic = false;
    public Reach() { super("Reach", "Extended attack range", Category.COMBAT); }
    public void onEnable() { enabledStatic = true; }
    public void onDisable() { enabledStatic = false; }
}