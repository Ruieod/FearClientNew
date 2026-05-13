package fear.client.module.visual;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

public class ESP extends Module {
    private MinecraftClient mc = MinecraftClient.getInstance();
    public ESP() { super("ESP", "Player ESP + see invisible", Category.VISUAL); }
    
    public void onRender(DrawContext ctx) {
        if (mc.world == null || mc.player == null) return;
        for (PlayerEntity p : mc.world.getPlayers()) {
            if (p == mc.player || !p.isAlive()) continue;
            // Показываем невидимок
            p.setInvisible(false);
        }
    }
}