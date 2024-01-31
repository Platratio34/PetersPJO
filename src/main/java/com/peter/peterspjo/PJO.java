package com.peter.peterspjo;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peter.peterspjo.entities.PJOEntities;
import com.peter.peterspjo.items.PJOItems;
import com.peter.peterspjo.worldgen.UnderworldChunkGenerator;

public class PJO implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("peterspjo");

	public static final String NAMESPACE = "peterspjo";

	public static final RegistryKey<DamageType> CELESTIAL_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,
			new Identifier(NAMESPACE, "celestial"));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Loading Peter's PJO");

		PJOItems.init();
		PJOBlocks.init();
		PJOEntities.init();

		PJOItemGroups.init();

		UnderworldChunkGenerator.register();

		LOGGER.info("Loaded Peter's PJO");
		// LOGGER.info(" __         ");
		// LOGGER.info("|__) \\    /");
		// LOGGER.info("|     \\/\\/");
		// LOGGER.info("           ");
	}
	
	public static DamageSource damageSourceOf(World world, RegistryKey<DamageType> key) {
		return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
	}


}