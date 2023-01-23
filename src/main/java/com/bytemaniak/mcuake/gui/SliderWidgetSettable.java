package com.bytemaniak.mcuake.gui;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public abstract class SliderWidgetSettable extends SliderWidget {
    public SliderWidgetSettable(int x, int y, int width, int height, Text text, double value) {
        super(x, y, width, height, text, value);
    }

    public void setValue(double value) {
        this.value = value;
        this.updateMessage();
    }
}
