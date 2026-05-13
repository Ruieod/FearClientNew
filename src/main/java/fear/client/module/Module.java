package fear.client.module;
public class Module {
    public String name, description;
    public Category category;
    public boolean enabled;
    public int key;
    public Module(String name, String desc, Category cat) {
        this.name = name; this.description = desc; this.category = cat; this.enabled = false;
    }
    public void toggle() { enabled = !enabled; if (enabled) onEnable(); else onDisable(); }
    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
    public enum Category { COMBAT, MOVEMENT, VISUAL, WORLD, MISC }
}