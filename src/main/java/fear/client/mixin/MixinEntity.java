package fear.client.mixin;
import fear.client.module.combat.Velocity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {
    @Inject(method = "setVelocity", at = @At("HEAD"), cancellable = true)
    public void onSetVelocity(double x, double y, double z, CallbackInfo ci) {
        if (Velocity.enabledStatic) {
            ci.cancel();
        }
    }
}