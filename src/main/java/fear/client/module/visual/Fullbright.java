package fear.client.module.visual;
import fear.client.module.Module;
import net.minecraft.client.MinecraftClient;
public class Fullbright extends Module {
    private double oldGamma;
    public Fullbright() { super("Fullbright", "Night vision", Category.VISUAL); }
    public void onEnable() { oldGamma = MinecraftClient.getInstance().options.getGamma().getValue(); MinecraftClient.getInstance().options.getGamma().setValue(15.0); }
    public void onDisable() { MinecraftClient.getInstance().options.getGamma().setValue(oldGamma); }
}