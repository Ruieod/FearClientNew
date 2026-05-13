package fear.client.module.movement;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
public class NoFall extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    public NoFall() { super("NoFall", "No fall damage", Category.MOVEMENT); }
    public void onTick() {
        if (mc.player == null) return;
        if (mc.player.fallDistance > 2.5f) {
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true, true));
            mc.player.fallDistance = 0;
        }
    }
}