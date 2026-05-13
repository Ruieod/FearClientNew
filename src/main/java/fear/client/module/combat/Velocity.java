package fear.client.module.combat;
import fear.client.module.Module;
public class Velocity extends Module {
    public static double horizontal = 0.0, vertical = 0.0;
    public Velocity() { super("Velocity", "Reduce knockback", Category.COMBAT); }
}