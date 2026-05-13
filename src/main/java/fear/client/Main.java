package fear.client;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import fear.client.ui.ClickGUI;
import fear.client.module.ModuleManager;

public class Main implements ClientModInitializer {
    private static KeyBinding guiKey;
    public void onInitializeClient() {
        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("FearClient", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "FearClient"));
        ModuleManager.init();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (guiKey.wasPressed()) ClickGUI.open();
            ModuleManager.onTick();
        });
        HudRenderCallback.EVENT.register((ctx, tickDelta) -> ModuleManager.onRender(ctx));
    }
}