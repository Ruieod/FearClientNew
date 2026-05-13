package fear.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import fear.client.module.Module;
import fear.client.module.ModuleManager;
import java.awt.Color;
import java.util.List;

public class ClickGUI extends Screen {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private int panelX = 80, panelY = 30, panelW = 300, panelH = 350;
    private boolean dragging = false;
    private int dragOffX, dragOffY;
    private int selectedTab = 0;
    private String[] tabs = {"COMBAT", "MOVEMENT", "VISUAL", "WORLD", "MISC"};
    private Module.Category[] cats = {Module.Category.COMBAT, Module.Category.MOVEMENT, Module.Category.VISUAL, Module.Category.WORLD, Module.Category.MISC};
    private int scroll = 0;

    public ClickGUI() { super(Text.literal("FearClient")); }

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        if (btn == 0 && mx >= panelX && mx <= panelX + panelW && my >= panelY && my <= panelY + 25) {
            dragging = true; dragOffX = (int)mx - panelX; dragOffY = (int)my - panelY; return true;
        }
        // Вкладки
        if (btn == 0 && my >= panelY + 30 && my <= panelY + 48) {
            int tabW = panelW / tabs.length;
            for (int i = 0; i < tabs.length; i++) {
                if (mx >= panelX + i * tabW && mx <= panelX + (i + 1) * tabW) { selectedTab = i; scroll = 0; return true; }
            }
        }
        // Модули
        if (btn == 0 && mx >= panelX + 5 && mx <= panelX + panelW - 5 && my >= panelY + 55 && my <= panelY + panelH) {
            List<Module> mods = ModuleManager.getByCategory(cats[selectedTab]);
            for (int i = 0; i < mods.size(); i++) {
                int y = panelY + 55 + i * 22 - scroll;
                if (y < panelY + 50 || y > panelY + panelH) continue;
                if (mx >= panelX + 5 && mx <= panelX + panelW - 5 && my >= y && my <= y + 20) {
                    mods.get(i).toggle(); return true;
                }
            }
        }
        return super.mouseClicked(mx, my, btn);
    }

    @Override
    public boolean mouseReleased(double mx, double my, int btn) { dragging = false; return super.mouseReleased(mx, my, btn); }

    @Override
    public boolean mouseDragged(double mx, double my, int btn, double dx, double dy) {
        if (dragging) { panelX = MathHelper.clamp((int)mx - dragOffX, 0, width - panelW); panelY = MathHelper.clamp((int)my - dragOffY, 0, height - 30); return true; }
        return super.mouseDragged(mx, my, btn, dx, dy);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double hz, double amt) {
        scroll = MathHelper.clamp(scroll - (int)amt * 20, 0, Math.max(0, ModuleManager.getByCategory(cats[selectedTab]).size() * 22 - panelH + 50));
        return true;
    }

    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        this.renderDarkening(ctx);
        int alpha = 230;

        // Фон панели
        ctx.fill(panelX, panelY, panelX + panelW, panelY + panelH, new Color(15, 15, 22, alpha).getRGB());
        // Заголовок
        ctx.fill(panelX, panelY, panelX + panelW, panelY + 25, new Color(25, 25, 35, alpha).getRGB());
        ctx.drawHorizontalLine(panelX, panelX + panelW, panelY + 24, new Color(200, 30, 30).getRGB());
        String title = "FEAR CLIENT v1.0";
        ctx.drawTextWithShadow(textRenderer, title, panelX + panelW/2 - textRenderer.getWidth(title)/2, panelY + 8, new Color(255, 50, 50).getRGB());

        // Вкладки
        int tabW = panelW / tabs.length;
        for (int i = 0; i < tabs.length; i++) {
            int tabX = panelX + i * tabW;
            boolean hovered = mx >= tabX && mx <= tabX + tabW && my >= panelY + 30 && my <= panelY + 48;
            boolean sel = i == selectedTab;
            ctx.fill(tabX, panelY + 30, tabX + tabW, panelY + 48, sel ? new Color(200, 30, 30, 200).getRGB() : (hovered ? new Color(50, 50, 60, 200).getRGB() : new Color(30, 30, 40, 200).getRGB()));
            String tabName = tabs[i];
            ctx.drawTextWithShadow(textRenderer, tabName, tabX + tabW/2 - textRenderer.getWidth(tabName)/2, panelY + 38, Color.WHITE.getRGB());
        }
        ctx.drawHorizontalLine(panelX, panelX + panelW, panelY + 48, new Color(200, 30, 30).getRGB());

        // Модули
        ctx.enableScissor(panelX, panelY + 50, panelX + panelW, panelY + panelH);
        List<Module> mods = ModuleManager.getByCategory(cats[selectedTab]);
        for (int i = 0; i < mods.size(); i++) {
            Module m = mods.get(i);
            int y = panelY + 55 + i * 22 - scroll;
            if (y < panelY + 48 || y > panelY + panelH) continue;
            boolean hovered = mx >= panelX + 5 && mx <= panelX + panelW - 5 && my >= y && my <= y + 20;
            ctx.fill(panelX + 5, y, panelX + panelW - 5, y + 20, hovered ? new Color(45, 45, 58, 255).getRGB() : new Color(22, 22, 32, 255).getRGB());
            if (hovered) ctx.drawHorizontalLine(panelX + 5, panelX + panelW - 6, y, new Color(200, 30, 30).getRGB());
            ctx.drawTextWithShadow(textRenderer, m.name, panelX + 10, y + 6, Color.WHITE.getRGB());
            String state = m.enabled ? "ON" : "OFF";
            int stateCol = m.enabled ? new Color(0, 255, 100).getRGB() : new Color(150, 150, 150).getRGB();
            ctx.drawTextWithShadow(textRenderer, state, panelX + panelW - 10 - textRenderer.getWidth(state), y + 6, stateCol);
        }
        ctx.disableScissor();

        // Низ
        ctx.fill(panelX, panelY + panelH, panelX + panelW, panelY + panelH + 2, new Color(200, 30, 30, 200).getRGB());
        ctx.drawTextWithShadow(textRenderer, "Right Shift - hide", panelX + 5, panelY + panelH + 8, new Color(150, 150, 150).getRGB());
    }

    @Override
    public boolean shouldPause() { return false; }
    public static void open() { mc.setScreen(new ClickGUI()); }
}