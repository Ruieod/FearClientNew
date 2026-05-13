package fear.client.module.combat;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;

public class TriggerBot extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    public TriggerBot() { super("TriggerBot", "Auto attack on crosshair", Category.COMBAT); }
    public void onTick() {
        if (mc.player == null || mc.crosshairTarget instanceof EntityHitResult hit && hit.getEntity() instanceof PlayerEntity p && p.isAlive()) {
            if (mc.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
                mc.interactionManager.attackEntity(mc.player, p);
                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }
}