package com.peter.peterspjo;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peter.peterspjo.entities.Entities;
import com.peter.peterspjo.items.Items;

public class PJO implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("peterspjo");

	public static final String NAMESPACE = "peterspjo";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Items.init();
		Blocks.init();
		Entities.init();
		
		ItemGroups.init();


		LOGGER.info("Loaded Peter's PJO");
		// LOGGER.info(" __         ");
		// LOGGER.info("|__) \\    /");
		// LOGGER.info("|     \\/\\/");
		// LOGGER.info("           ");
	}


}