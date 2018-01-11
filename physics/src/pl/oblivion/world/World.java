package pl.oblivion.world;

import org.apache.log4j.Logger;
import org.joml.Vector3f;

public class World {

	private static final Logger logger = Logger.getLogger(World.class);
	private Vector3f gravity;
	private World world;

	public World (){
		this.world = this;
		logger.info("Initializing world...");
	}

	public World gravity(Vector3f gravity){
		logger.info("Setting worlds gravity to: "+gravity.y+"m/s with x/z:"+gravity.x+"/"+gravity.z+" side forces");
		if(this.world == null){
			this.world = new World();
		}
		this.world.setGravity(gravity);
		return this.world;
	}

	private void setGravity(Vector3f gravity){
		this.gravity = gravity;
	}

	public Vector3f getGravity() {
		return gravity;
	}
}
