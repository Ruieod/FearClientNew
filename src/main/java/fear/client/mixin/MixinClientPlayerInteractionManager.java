package fear.client.mixin;
import fear.client.module.combat.Reach;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {
    @Redirect(method = "attackEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;squaredDistanceTo(Lnet/minecraft/entity/Entity;)D"))
    public double modifyAttackDistance(PlayerEntity player, Entity entity) {
        if (Reach.enabledStatic) {
            double dist = Reach.distance * Reach.distance;
            double realDist = player.squaredDistanceTo(entity);
            return realDist > dist ? Double.MAX_VALUE : 0.0;
        }
        return player.squaredDistanceTo(entity);
    }
}