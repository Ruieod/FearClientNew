package fear.client.mixin;
import fear.client.module.ModuleManager;
import fear.client.module.combat.Velocity;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {
    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if (Velocity.enabledStatic) {
            // Сбрасываем отдачу полностью
            ClientPlayerEntity self = (ClientPlayerEntity)(Object)this;
            self.setVelocity(0, self.getVelocity().y, 0);
        }
    }
}