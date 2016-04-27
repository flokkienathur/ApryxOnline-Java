package net.apryx.apryxonline;

import net.apryx.game.NetworkGameObject;
import net.apryx.graphics.SpriteBatch;

public class GameObjectFireball extends NetworkGameObject {
	
	
	public GameObjectFireball(float x, float y, float targetX, float targetY) {
		super(x, y);
		this.targetX = targetX;
		this.targetY = targetY;
	}
	
	public void update(){
		if(moveToTarget(128)){
			this.world.removeGameObject(this);
		}
	}

	public void render(SpriteBatch batch) {
		super.render(batch);

		batch.color(1,0.5f,0.5f);
		batch.depth(-10);
		batch.drawRectangle(x-2, y-2, 4, 4);
		batch.depth(0);
		batch.color(1, 1, 1);
	}
}
