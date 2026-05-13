package fear.client.module.combat;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.math.Vec3d;
import java.util.Random;

public class KillAura extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    private Random random = new Random();
    public static boolean enabled = true;
    public static double range = 3.5;
    public static boolean throughWalls = false;
    public static boolean crits = true;
    public static int critMode = 1; // 0=Jump,1=Packet,2=Strict
    public static boolean antiBot = true;
    public static boolean playersOnly = false;
    public static boolean mobs = true;
    public static boolean noSwing = false;
    public static boolean keepSprint = true;
    public static double fov = 360;
    private int tick = 0;
    private int nextDelay = 8;

    public KillAura() { super("KillAura", "Auto attack entities", Category.COMBAT); }

    public void onTick() {
        if (!enabled || mc.player == null || mc.world == null || mc.interactionManager == null) return;
        if (mc.player.isDead()) return;
        tick++;
        if (tick < nextDelay) return;
        nextDelay = 8 + random.nextInt(5);
        tick = 0;

        if (keepSprint && mc.player.isSprinting()) mc.player.setSprinting(true);

        LivingEntity target = null;
        double closest = range;
        for (var ent : mc.world.getEntities()) {
            if (!(ent instanceof LivingEntity e)) continue;
            if (e == mc.player || !e.isAlive() || e.isDead()) continue;
            if (antiBot && isBot(e)) continue;
            if (playersOnly && !(e instanceof PlayerEntity)) continue;
            if (!playersOnly && !mobs && !(e instanceof Monster || e instanceof AnimalEntity)) continue;
            if (!playersOnly && mobs && !(e instanceof PlayerEntity || e instanceof Monster || e instanceof AnimalEntity)) continue;
            double d = mc.player.distanceTo(e);
            if (d > range) continue;
            if (!throughWalls && !canSee(e)) continue;
            float yaw = Math.abs(mc.player.getYaw() - getYaw(e));
            if (yaw > 180) yaw = 360 - yaw;
            if (yaw > fov / 2) continue;
            if (d < closest) { closest = d; target = e; }
        }

        if (target == null) return;

        if (crits) {
            switch (critMode) {
                case 0: doJumpCrit(); break;
                case 1: doPacketCrit(); break;
                case 2: doStrictCrit(); break;
            }
        }

        if (throughWalls && !canSee(target)) {
            mc.getNetworkHandler().sendPacket(PlayerInteractEntityC2SPacket.attack(target, mc.player.isSneaking()));
        } else {
            mc.interactionManager.attackEntity(mc.player, target);
        }
        if (!noSwing) mc.player.swingHand(Hand.MAIN_HAND);
    }

    private boolean isBot(LivingEntity e) {
        if (e.hasCustomName()) {
            String n = e.getCustomName().getString().toLowerCase();
            if (n.contains("[npc]") || n.contains("bot") || n.contains("shop")) return true;
        }
        return false;
    }

    private float getYaw(LivingEntity t) {
        double dx = t.getX() - mc.player.getX();
        double dz = t.getZ() - mc.player.getZ();
        return (float)(Math.atan2(dz, dx) * 180.0 / Math.PI) - 90;
    }

    private boolean canSee(LivingEntity e) {
        Vec3d eyes = mc.player.getCameraPosVec(1.0F);
        Vec3d pos = e.getPos().add(0, e.getHeight()/2, 0);
        RaycastContext ctx = new RaycastContext(eyes, pos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, mc.player);
        return mc.world.raycast(ctx).getType() == HitResult.Type.MISS;
    }

    private void doJumpCrit() { if (mc.player.isOnGround() && !mc.player.isTouchingWater()) mc.player.jump(); }
    private void doPacketCrit() {
        if (!mc.player.isOnGround() || mc.player.isTouchingWater() || mc.player.isInLava()) return;
        double x = mc.player.getX(), y = mc.player.getY(), z = mc.player.getZ();
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y+0.0625, z, false, true));
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false, true));
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y+0.0625, z, false, true));
    }
    private void doStrictCrit() {
        if (!mc.player.isOnGround() || mc.player.isTouchingWater() || mc.player.isInLava()) return;
        double x = mc.player.getX(), y = mc.player.getY(), z = mc.player.getZ();
        for (int i=1;i<=3;i++) mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y+0.0001*i, z, false, true));
        mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, true, true));
    }
}