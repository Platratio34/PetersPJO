package com.peter.peterspjo.entities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.jetbrains.annotations.Nullable;

import com.peter.peterspjo.PJO;
import com.peter.peterspjo.rendering.DynamicEntityTexture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.passive.HorseColor;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoRenderer;

public class CentaurGeoModel<T extends Centaur> extends GeoAnimatedModel<T> {
    
    private static final Map<HorseColor, Identifier> HORSE_TEXTURES = Map.of(
        HorseColor.WHITE, Identifier.ofVanilla("textures/entity/horse/horse_white.png"),
        HorseColor.CREAMY, Identifier.ofVanilla("textures/entity/horse/horse_creamy.png"),
        HorseColor.CHESTNUT, Identifier.ofVanilla("textures/entity/horse/horse_chestnut.png"),
        HorseColor.BROWN, Identifier.ofVanilla("textures/entity/horse/horse_brown.png"),
        HorseColor.BLACK, Identifier.ofVanilla("textures/entity/horse/horse_black.png"),
        HorseColor.GRAY, Identifier.ofVanilla("textures/entity/horse/horse_gray.png"),
        HorseColor.DARK_BROWN, Identifier.ofVanilla("textures/entity/horse/horse_darkbrown.png")
    );
    
    private static final Identifier[] UPPER_TEXTURES = {
        PJO.id("textures/entity/centaur/centaur.png"),
        PJO.id("textures/entity/centaur/centaur.png"),
        PJO.id("textures/entity/centaur/centaur.png"),
        PJO.id("textures/entity/centaur/centaur.png"),
        PJO.id("textures/entity/centaur/centaur.png"),
        PJO.id("textures/entity/centaur/centaur.png"),
        PJO.id("textures/entity/centaur/centaur.png"),
        PJO.id("textures/entity/centaur/centaur.png")
    };

    private static HashMap<UUID, DynamicEntityTexture> textures = new HashMap<UUID, DynamicEntityTexture>();

    public CentaurGeoModel() {
        super(Centaur.NAME);
    }

    @Override
    public Identifier getTextureResource(T animatable, @Nullable GeoRenderer<T> renderer) {
        return getOrMake(animatable).id;
    }

    public DynamicEntityTexture getOrMake(T entity) {
        UUID uuid = entity.getUuid();
        if (!textures.containsKey(uuid)) {
            makeFor(entity);
        }
        return textures.get(uuid);
    }

    public void makeFor(T entity) {
        DynamicEntityTexture texture = new DynamicEntityTexture(PJO.NAMESPACE, 64, 64);
        textures.put(entity.getUuid(), texture);

        // AnimatableTexture cTexture = (AnimatableTexture)MinecraftClient.getInstance().getTextureManager()
        //         .getTexture(super.getTextureResource(entity));
        // cTexture.bindTexture();
        Identifier upperId = UPPER_TEXTURES[entity.getUpperVariant()];
        try {
            Resource baseTextureResource = MinecraftClient.getInstance().getResourceManager()
                .getResource(upperId).get();
            BufferedImage baseTexture = ImageIO.read(baseTextureResource.getInputStream());
            for (int x = 0; x < baseTexture.getHeight(); x++) {
                for (int y = 0; y < baseTexture.getHeight(); y++) {
                    int color = baseTexture.getRGB(x, y);
                    Color c = new Color(color);
                    texture.setColor(x, y, c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (NoSuchElementException e) {
            PJO.LOGGER.error("Could not load texture: `" + upperId + "`");
            return;
        }
        Identifier horseIdentifier = HORSE_TEXTURES.get(entity.getVariant());
        try {
            Resource baseTextureResource = MinecraftClient.getInstance().getResourceManager()
                .getResource(horseIdentifier).get();
            BufferedImage baseTexture = ImageIO.read(baseTextureResource.getInputStream());
            for (int x = 0; x < baseTexture.getHeight(); x++) {
                for (int y = 0; y < baseTexture.getHeight(); y++) {
                    if (y < 16)
                        continue;
                    if (y < 32 && x < 48)
                        continue;
                    if (y < 48 && x < 16)
                        continue;
                    int color = baseTexture.getRGB(x, y);
                    Color c = new Color(color);
                    texture.setColor(x, y, c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (NoSuchElementException e) {
            PJO.LOGGER.error("Could not load texture: `"+horseIdentifier+"`");
            return;
        }

        texture.texture.upload();
    }

}
