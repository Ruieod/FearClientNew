package fear.client.mixin;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.Entity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import fear.client.module.ModuleManager;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {
    @Inject(method = "render", at = @At("HEAD"))
    public void onRender(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vcp, int light, CallbackInfo ci) {}
}