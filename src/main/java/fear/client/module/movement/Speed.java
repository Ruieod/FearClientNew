package fear.client.module.movement;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
public class Speed extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    public static double multiplier = 1.5;
    public Speed() { super("Speed", "Move faster", Category.MOVEMENT); }
    public void onTick() {
        if (mc.player == null || !mc.player.isOnGround()) return;
        mc.player.setVelocity(mc.player.getVelocity().multiply(multiplier, 1, multiplier));
    }
}