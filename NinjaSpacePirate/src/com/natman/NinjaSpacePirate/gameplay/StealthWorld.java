package com.natman.NinjaSpacePirate.gameplay;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.systems.generic.TrackingCameraSystem;
import com.lostcode.javalib.utils.Convert;
import com.lostcode.javalib.utils.SpriteSheet;
import com.natman.NinjaSpacePirate.gameplay.entities.systems.NPCAnimationSystem;
import com.natman.NinjaSpacePirate.gameplay.entities.systems.PitBlockingSystem;
import com.natman.NinjaSpacePirate.gameplay.entities.systems.PlayerControlSystem;
import com.natman.NinjaSpacePirate.gameplay.entities.systems.TileSpawnSystem;
import com.natman.NinjaSpacePirate.gameplay.entities.templates.CoinTemplate;
import com.natman.NinjaSpacePirate.gameplay.entities.templates.HudWarningTemplate;
import com.natman.NinjaSpacePirate.gameplay.entities.templates.NPCSpawnerTemplate;
import com.natman.NinjaSpacePirate.gameplay.entities.templates.NPCTemplate;
import com.natman.NinjaSpacePirate.gameplay.entities.templates.PitTemplate;
import com.natman.NinjaSpacePirate.gameplay.entities.templates.PlayerTemplate;
import com.natman.NinjaSpacePirate.gameplay.entities.templates.PotionMessageTemplate;
import com.natman.NinjaSpacePirate.gameplay.entities.templates.PotionTemplate;
import com.natman.NinjaSpacePirate.gameplay.entities.templates.TileTemplate;
import com.natman.NinjaSpacePirate.gameplay.entities.templates.VoidTemplate;
import com.natman.NinjaSpacePirate.gameplay.stats.TimeStat;

/**
 * The NinjaSpacePirate game world.
 * @author Natman64
 *
 */
public class StealthWorld extends EntityWorld {
	
	/** The first y coordinate where tiles will spawn. */
	public static final int TILE_SPAWN_Y = -6;
	
	/** The location the player spawns at. */
	public static final int PLAYER_SPAWN_Y = -5;
	
	/** The speed of walking enemies. */
	public static final float ENEMY_SPEED = 3f;
	
	private Entity player;
	private GameScore score = new GameScore();
	
	//region Initialization
	
	/**
	 * Makes a StealthWorld.
	 * @param input
	 * @param camera
	 * @param gravity
	 */
	public StealthWorld(InputMultiplexer input, Camera camera) {
		super(input, camera, new Vector2());
		
		physicsWorld.setPositionIterations(4);
		
		//debugView.enabled = true;
		debugView.visible = true;
	}

	//endregion
	
	//region Disposal
	
	@Override
	public void dispose() {
		super.dispose();
		
		Stats.saveStats();
	}
	
	//endregion
	
	//region Templates
	
	@Override
	protected void buildTemplates() {
		super.buildTemplates();
		
		//HUD
		addTemplate("HudWarning", new HudWarningTemplate());
		addTemplate("PotionMessage", new PotionMessageTemplate());
		
		//World
		addTemplate("Tile", new TileTemplate());
		addTemplate("Pit", new PitTemplate());
		addTemplate("EnemySpawner", new NPCSpawnerTemplate());
		addTemplate("Void", new VoidTemplate());
		
		//Characters
		addTemplate("Player", new PlayerTemplate());
		addTemplate("NPC", new NPCTemplate());
		
		//Items
		addTemplate("Potion", new PotionTemplate());
		addTemplate("Coin", new CoinTemplate());
	}
	
	//endregion
	
	//region Systems
	
	@Override
	protected void buildSystems() {
		super.buildSystems();
		
		TrackingCameraSystem cameraSystem = 
				new TrackingCameraSystem("Player", camera);
		
		cameraSystem.setCameraOffset(Convert.metersToPixels(new Vector2(0, 4.5f)));
		systems.addSystem(cameraSystem);
		
		
		systems.addSystem(new TileSpawnSystem());
		systems.addSystem(new PlayerControlSystem(input));
		systems.addSystem(new NPCAnimationSystem());
		systems.addSystem(new PitBlockingSystem());
	}
	
	//endregion
	
	//region Entities
	
	@Override
	protected void buildEntities() {
		player = createEntity("Player");
	}
	
	//endregion
	
	//region SpriteSheet
	
	@Override
	protected void buildSpriteSheet() {
		
		try {
			spriteSheet = SpriteSheet.fromXML(Gdx.files.internal("data/spritesheet.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Humans
		addHumanSprite("redSuitMan", 0, 9, 1);
		addHumanSprite("greenSuitMan", 0, 18, 1);
		addHumanSprite("yellowSuitMan", 0, 27, 1);
		addHumanSprite("whiteSuitMan", 0, 36, 1);
		
	}

	private void addHumanSprite(String key, int x, int y, int padding) {
		Rectangle frame = new Rectangle(x + padding, y + padding, 8, 8);
		
		spriteSheet.addRegion(key + "Right", frame);
		frame.x += 8 + padding; frame.width = 16 + padding;
		spriteSheet.addRegion(key + "MoveRight", frame);
		frame.x += 16 + padding + padding; frame.width = 8;
		spriteSheet.addRegion(key + "Down", frame);
		frame.x += 8 + padding; frame.width = 16 + padding;
		spriteSheet.addRegion(key + "MoveDown", frame);
		frame.x += 16 + padding + padding; frame.width = 8;
		spriteSheet.addRegion(key + "Left", frame);
		frame.x += 8 + padding; frame.width = 16 + padding;
		spriteSheet.addRegion(key + "MoveLeft", frame);
		frame.x += 16 + padding + padding; frame.width = 8;
		spriteSheet.addRegion(key + "Up", frame);
		frame.x += 8 + padding; frame.width = 16 + padding;
		spriteSheet.addRegion(key + "MoveUp", frame);
		frame.x += 16 + padding + padding; frame.width = 8;
		spriteSheet.addRegion(key + "Dead", frame);
	}
	
	@SuppressWarnings("unused")
	private void addMonsterSprite(String key, int x, int y, int padding) {
		Rectangle frame = new Rectangle(x + padding, y + padding, 8, 8);
		
		spriteSheet.addRegion(key + "Right", frame);
		frame.x += 8 + padding; frame.width = 16 + padding;
		spriteSheet.addRegion(key + "MoveRight", frame);
		frame.x += 16 + padding + padding; frame.width = 8;
		spriteSheet.addRegion(key + "Down", frame);
		frame.x += 8 + padding; frame.width = 16 + padding;
		spriteSheet.addRegion(key + "MoveDown", frame);
		frame.x += 16 + padding + padding; frame.width = 8;
		spriteSheet.addRegion(key + "Left", frame);
		frame.x += 8 + padding; frame.width = 16 + padding;
		spriteSheet.addRegion(key + "MoveLeft", frame);
		frame.x += 16 + padding + padding; frame.width = 8;
		spriteSheet.addRegion(key + "Up", frame);
		frame.x += 8 + padding; frame.width = 16 + padding;
		spriteSheet.addRegion(key + "MoveUp", frame);
	}
	
	//endregion
	
	//region Bounds
	
	@Override
	public Rectangle getBounds() {
		float width = Convert.pixelsToMeters(camera.viewportWidth);
		float height = Convert.pixelsToMeters(camera.viewportHeight);
		
		float x = -width / 2;
		float y = Convert.pixelsToMeters(camera.position.y) - height / 2;
		
		return new Rectangle(x, y, width, height);
	}

	//endregion

	//region Accessors
	
	/**
	 * @return The player Entity.
	 */
	public Entity getPlayer() {
		return player;
	}
	
	public GameScore getScore() {
		return score;
	}
	
	//endregion
	
	//region Processing
	
	@Override
	public void process() {
		super.process();
		
		TimeStat timePlayed = (TimeStat) Stats.getStat("Time Played");
		timePlayed.add(Gdx.graphics.getDeltaTime());
	}
	
	//endregion
	
}
