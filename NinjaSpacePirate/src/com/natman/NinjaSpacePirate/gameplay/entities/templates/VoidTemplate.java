package com.natman.NinjaSpacePirate.gameplay.entities.templates;

import com.badlogic.gdx.math.Vector2;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.templates.EntityTemplate;
import com.natman.NinjaSpacePirate.gameplay.entities.components.render.scenery.MiniStarField;
import com.natman.NinjaSpacePirate.gameplay.entities.processes.VacuumProcess;

/**
 * Creates a Void entity that sucks the player in.
 * @author Natman64
 * @created Oct 12, 2013
 */
public class VoidTemplate implements EntityTemplate {

	@Override
	public void dispose() {
		
	}

	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		e.init("", "Obstacles", "Void");
		
		Entity target = (Entity) args[0];
		Vector2 position = (Vector2) args[1];
		Float force = (Float) args[2];
		
		VacuumProcess process = new VacuumProcess(world, target, position, force);
		e.addComponent(process);
		
		MiniStarField stars = new MiniStarField(world);
		e.addComponent(stars);
		
		return e;
	}

}
