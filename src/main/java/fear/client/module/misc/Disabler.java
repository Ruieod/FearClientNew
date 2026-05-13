package fear.client.module.misc;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Disabler extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    public Disabler() { super("Disabler", "Bypass anti-cheat (FunTime)", Category.MISC); }
    public void onTick() {
        if (mc.player == null) return;
        // Спам пакетами движения для сброса проверок
        double x = mc.player.getX(), y = mc.player.getY(), z = mc.player.getZ();
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.000001, z, false, true));
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, true, true));
    }
}