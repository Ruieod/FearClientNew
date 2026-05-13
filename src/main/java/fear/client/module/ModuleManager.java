package fear.client.module;
import java.util.*;
import net.minecraft.client.gui.DrawContext;
import fear.client.module.combat.*;
import fear.client.module.movement.*;
import fear.client.module.visual.*;
import fear.client.module.world.*;
import fear.client.module.misc.*;

public class ModuleManager {
    public static List<Module> modules = new ArrayList<>();
    public static void init() {
        modules.add(new KillAura());
        modules.add(new TriggerBot());
        modules.add(new Velocity());
        modules.add(new Reach());
        modules.add(new Fly());
        modules.add(new Speed());
        modules.add(new NoFall());
        modules.add(new ESP());
        modules.add(new Tracers());
        modules.add(new XRay());
        modules.add(new Fullbright());
        modules.add(new Nametags());
        modules.add(new NoHurtCam());
        modules.add(new Scaffold());
        modules.add(new FastBreak());
        modules.add(new ChestStealer());
        modules.add(new AntiAFK());
        modules.add(new Disabler());
    }
    public static void onTick() { for (Module m : modules) if (m.enabled) m.onTick(); }
    public static void onRender(DrawContext ctx) { for (Module m : modules) if (m.enabled) m.onRender(ctx); }
    public static List<Module> getByCategory(Module.Category cat) {
        List<Module> list = new ArrayList<>();
        for (Module m : modules) if (m.category == cat) list.add(m);
        return list;
    }
}