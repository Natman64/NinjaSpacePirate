package com.natman.NinjaSpacePirate.gameplay.entities.systems.spawn.locations;

import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.utils.Random;
import com.natman.NinjaSpacePirate.gameplay.entities.components.render.hud.HudWarning.WarningType;
import com.natman.NinjaSpacePirate.gameplay.entities.systems.spawn.LocationTemplate;

/**
 * Template for a hull breach obstacle.
 * @author Natman64
 * @created Oct 12, 2013
 */
public class HullBreachLocation extends LocationTemplate {

	private static final int ROWS = 7;
	private static final int PADDING_ROWS = 10;
	
	private Random r = new Random();
	
	private boolean leftSide;
	
	/**
	 * Constructs a HullBreachLocation.
	 */
	public HullBreachLocation(int difficulty, int weight) {
		super(null, difficulty, weight);
	}

	@Override
	public String[] getRows() {
		String prefix;
		
		String[] rows = new String[ROWS + PADDING_ROWS];
		
		leftSide = r.nextBoolean();
		
		if (leftSide) {
			//left side
			prefix = "Left";
		} else {
			//right side
			prefix = "Right";
		}
		
		for (int i = 0; i < ROWS; i++) {
			rows[i] = prefix + "Breach" + i;
		}
		
		for (int i = ROWS; i < ROWS + PADDING_ROWS; i++) {
			rows[i] = "HallSegment";
		}
		
		return rows;
	}

	@Override
	public void onQueue(EntityWorld world) {
		int lane = 1;
		
		if (!leftSide) {
			lane = 5;
		}
		
		world.createEntity("HudWarning", WarningType.WarningSign, lane);
	}
	
	
	
}
