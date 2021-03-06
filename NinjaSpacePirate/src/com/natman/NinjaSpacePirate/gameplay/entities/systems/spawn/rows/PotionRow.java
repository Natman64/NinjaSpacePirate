package com.natman.NinjaSpacePirate.gameplay.entities.systems.spawn.rows;

import com.badlogic.gdx.math.Vector2;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.utils.Random;
import com.natman.NinjaSpacePirate.gameplay.StealthWorld;
import com.natman.NinjaSpacePirate.gameplay.entities.processes.PowerupProcess;
import com.natman.NinjaSpacePirate.gameplay.entities.processes.powerups.GhostPowerup;
import com.natman.NinjaSpacePirate.gameplay.entities.processes.powerups.InvulnPowerup;
import com.natman.NinjaSpacePirate.gameplay.entities.processes.powerups.ReverseControlPowerup;
import com.natman.NinjaSpacePirate.gameplay.entities.processes.powerups.SpeedLockPowerup;
import com.natman.NinjaSpacePirate.gameplay.entities.systems.spawn.TileArgs;
import com.natman.NinjaSpacePirate.gameplay.entities.systems.spawn.TileRow;

public class PotionRow extends TileRow {

	private static final int POWERUPS = 4;
	
	private Random r = new Random();
	
	public PotionRow(TileArgs... tiles) {
		super(tiles);
	}

	@Override
	public void onCreated(EntityWorld world, float y) {
		
		Entity e = ((StealthWorld) world).getPlayer();
		
		world.createEntity("Potion", new Vector2(0, y), randomPowerup(e));
		
	}
	
	private PowerupProcess randomPowerup(Entity e) {
		int num = r.nextInt(POWERUPS);
		
		switch (num) {
		
		case 0:
			return new ReverseControlPowerup(3, e);
		
		case 1:
			return new SpeedLockPowerup(3, e);
			
		case 2:
			return new GhostPowerup(5, e);
			
		case 3:
			return new InvulnPowerup(5, e);
			
		default:
			return null;
			
		}
	}
	
}
