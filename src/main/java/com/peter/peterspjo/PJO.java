package com.peter.peterspjo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peter.peterspjo.blocks.PJOBlocks;
import com.peter.peterspjo.entities.PJOEntities;
import com.peter.peterspjo.items.PJOItems;
import com.peter.peterspjo.items.armor.PJOArmorMaterials;
import com.peter.peterspjo.worldgen.UnderworldChunkGenerator;
import com.peter.peterspjo.worldgen.labyrinth.DoorManager;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthChunkGenerator;
import com.peter.peterspjo.worldgen.labyrinth.LabyrinthMap;

public class PJO implements ModInitializer {
    
    public static final String NAMESPACE = "peterspjo";
    
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

    public static MinecraftServer server;

	@Override
	public void onInitialize() {

        LOGGER.info("Loading Peter's PJO");

		PJOItems.init();
		PJOBlocks.init();
        PJOEntities.init();
        PJOArmorMaterials.init();

        PJOItemGroups.init();

        UnderworldChunkGenerator.register();
        LabyrinthChunkGenerator.register();
        
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            PJO.server = server;
            LabyrinthMap.getServerState(server);
            DoorManager.getServerState(server);
        });

		LOGGER.info("Loaded Peter's PJO");
		LOGGER.info(" __   ___  _  ");
		LOGGER.info("|__)   |  / \\ ");
		LOGGER.info("|    \\_/  \\_/ ");
		LOGGER.info("              ");
	}
	
    public static DamageSource damageSourceOf(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
    
    public static Identifier id(String id) {
        return Identifier.of(NAMESPACE, id);
    }

    public static MutableText translatable(String category, String name) {
        return Text.translatable(String.format("%s.%s.%s", category, NAMESPACE, name));
    }

    public static MutableText tooltip(String category, String name) {
        return Text.translatable(String.format("%s.%s.%s.tooltip", category, NAMESPACE, name));
    }

    public static MutableText tooltip(String category, String name, int line) {
        return Text.translatable(String.format("%s.%s.%s.tooltip%s", category, NAMESPACE, name, line+""));
    }


}