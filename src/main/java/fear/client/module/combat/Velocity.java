package fear.client.module.combat;
import fear.client.module.Module;
public class Velocity extends Module {
    public static boolean enabledStatic = false;
    public Velocity() { super("Velocity", "Cancel horizontal knockback", Category.COMBAT); }
    public void onEnable() { enabledStatic = true; }
    public void onDisable() { enabledStatic = false; }
}