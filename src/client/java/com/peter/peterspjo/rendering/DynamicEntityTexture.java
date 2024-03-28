package com.peter.peterspjo.rendering;

import java.awt.Color;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

public class DynamicEntityTexture {

    public NativeImage image;
    public NativeImageBackedTexture texture;
    public Identifier id;

    public DynamicEntityTexture(String prefix, int w, int h) {
        image = new NativeImage(w, h, false);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                image.setColor(i, j, 0);
            }
        }
        texture = new NativeImageBackedTexture(image);
        id = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture(prefix, texture);
    }

    public void setColor(int x, int y, Color color) {
        image.setColor(x, y, color.getAlpha() << 24 | color.getBlue() << 16 | color.getGreen() << 8 | color.getRed());
    }
}
