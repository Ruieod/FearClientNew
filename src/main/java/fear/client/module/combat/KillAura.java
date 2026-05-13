package fear.client.module.combat;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.client.gui.DrawContext;

public class KillAura extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    public static double range = 3.5;
    public static boolean crits = true;
    private int tick = 0;

    public KillAura() { super("KillAura", "Auto attack with crits", Category.COMBAT); }
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        tick++; if (tick < 8) return; tick = 0;
        LivingEntity target = null; double close = range;
        for (var e : mc.world.getEntities()) {
            if (!(e instanceof PlayerEntity p) || p == mc.player || !p.isAlive()) continue;
            double d = mc.player.distanceTo(p); if (d < close) { close = d; target = p; }
        }
        if (target == null) return;
        if (crits && mc.player.isOnGround()) {
            double x = mc.player.getX(), y = mc.player.getY(), z = mc.player.getZ();
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.0625, z, false, true));
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false, true));
        }
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }
}