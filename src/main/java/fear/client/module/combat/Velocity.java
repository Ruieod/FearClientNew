package fear.client.module.combat;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.entity.player.PlayerEntity;

public class Velocity extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    public static boolean enabledStatic = false;
    public Velocity() { super("Velocity", "Cancel knockback", Category.COMBAT); }
    public void onEnable() { enabledStatic = true; }
    public void onDisable() { enabledStatic = false; }
}