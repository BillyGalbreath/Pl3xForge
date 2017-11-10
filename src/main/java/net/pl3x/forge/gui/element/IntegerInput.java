package net.pl3x.forge.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.util.Validator;

@SideOnly(Side.CLIENT)
public class IntegerInput extends GuiButton {
    private GuiTextField textField;
    private Button plusBtn;
    private Button minusBtn;

    private final int min;
    private final int max;
    private final boolean wrap;

    private int value;

    public IntegerInput(int buttonId, int x, int y, int width, int height, int min, int max, int value, boolean wrap, FontRenderer fontRenderer) {
        super(buttonId, x, y, width, height, "");
        this.min = min;
        this.max = max;
        this.wrap = wrap;
        this.value = value;

        textField = new GuiTextField(0, fontRenderer, x + 13, y + 1, width - 26, height);
        textField.setValidator(Validator::predicateInteger);
        textField.setText(Integer.toString(value));
        plusBtn = new Button(0, x + width - 12, y, 12, 12, "+");
        minusBtn = new Button(1, x, y, 12, 12, "-");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!visible) {
            return;
        }
        plusBtn.drawButton(mc, mouseX, mouseY, partialTicks);
        minusBtn.drawButton(mc, mouseX, mouseY, partialTicks);
        textField.drawTextBox();
    }

    @Override
    public boolean mousePressed(Minecraft mc, int x, int y) {
        if (plusBtn.mousePressed(mc, x, y)) {
            increment();
            return true;
        }
        if (minusBtn.mousePressed(mc, x, y)) {
            decrement();
            return true;
        }
        return super.mousePressed(mc, x, y);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        plusBtn.mouseReleased(mouseX, mouseY);
        minusBtn.mouseReleased(mouseX, mouseY);
    }

    public boolean tick() {
        if (plusBtn.tick()) {
            increment();
            return true;
        } else if (minusBtn.tick()) {
            decrement();
            return true;
        }
        return false;
    }

    public void increment() {
        if (++value > max) {
            value = wrap ? min : max;
        }
        textField.setText(Integer.toString(value));
    }

    public void decrement() {
        if (--value < min) {
            value = wrap ? max : min;
        }
        textField.setText(Integer.toString(value));
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        textField.setText(Integer.toString(value));
    }
}
