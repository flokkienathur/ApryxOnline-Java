package net.apryx.apryxonline;

import java.io.File;

import net.apryx.game.GameObject;
import net.apryx.game.NetworkGameObject;
import net.apryx.graphics.SpriteBatch;
import net.apryx.graphics.texture.Sprite;
import net.apryx.graphics.texture.Texture;
import net.apryx.graphics.texture.TextureLoader;
import net.apryx.input.Input;
import net.apryx.input.Mouse;
import net.apryx.math.Mathf;
import net.apryx.time.Time;

public class GameObjectPlayer extends NetworkGameObject{
	
	private float movementSpeed = 64;
	private Sprite sprite;
	
	public GameObjectPlayer(float x, float y){
		super(x,y);
		Texture t = TextureLoader.loadTexture(new File("texture/player.png"));
		sprite = new Sprite(t);
		sprite.center();
		sprite.setyOffset(sprite.getHeight());
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		
		batch.depth(GameObject.getDepthByPosition(y));
		
		if(isLocal()){
			batch.color(1, 0, 0);
			batch.drawRectangle(targetX - 1, targetY - 1, 2, 2);
		}
		batch.color(1,1,1);
		batch.drawSprite(sprite, x, y, x > targetX ? -1 : 1, 1);
	}
	
	@Override
	public void update() {
		super.update();
		
		if(isLocal()){
			updateLocal();
		}else{
			updateNetwork();
		}
		
	}
	
	public void updateLocal(){
		if(Input.isMouseButtonPressed(Mouse.RIGHT)){
			targetX = world.getMouseX();
			targetY = world.getMouseY();
			setChanged();
		}
		
		moveToTarget(movementSpeed);
	}
	
	public void moveToTarget(float speed){
		float xDir = targetX - x;
		float yDir = targetY - y;
		
		float l = Mathf.sqrt(xDir * xDir + yDir * yDir);
		if(l < 1f)
			return;
		
		xDir /= l;
		yDir /= l;

		x += xDir * speed * Time.deltaTime;
		y += yDir * speed * Time.deltaTime;
	}
	
	public void updateNetwork(){
		moveToTarget(movementSpeed);
	}
}
