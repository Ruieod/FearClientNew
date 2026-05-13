package fear.client.module.visual;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import java.awt.Color;

public class ESP extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    public ESP() { super("ESP", "Player boxes", Category.VISUAL); }
    public void onRender(DrawContext ctx) {
        if (mc.world == null || mc.player == null) return;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player || !p.isAlive()) continue;
            Box box = p.getBoundingBox();
            Vec3d pos = p.getPos().add(0, p.getHeight(), 0).subtract(mc.gameRenderer.getCamera().getPos());
            float x = (float) pos.x, y = (float) pos.y, z = (float) pos.z;
        }
    }
}