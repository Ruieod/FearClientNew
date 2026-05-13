package fear.client.module.movement;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
public class Fly extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    public static double speed = 1.0;
    public Fly() { super("Fly", "Fly in survival", Category.MOVEMENT); }
    public void onTick() {
        if (mc.player == null) return;
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().setFlySpeed((float)(speed * 0.1));
    }
    public void onDisable() { if (mc.player != null) { mc.player.getAbilities().flying = false; mc.player.getAbilities().setFlySpeed(0.05f); } }
}