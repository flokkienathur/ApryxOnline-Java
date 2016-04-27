package net.apryx.apryxonline;

import net.apryx.apryxonline.spells.ApryxSpells;
import net.apryx.apryxonline.spells.Spell;
import net.apryx.apryxonline.spells.SpellKeyframe;
import net.apryx.apryxonline.tile.ApryxResources;
import net.apryx.game.NetworkGameObject;
import net.apryx.graphics.SpriteBatch;
import net.apryx.graphics.texture.Sprite;
import net.apryx.input.Input;
import net.apryx.input.Keys;
import net.apryx.input.Mouse;
import net.apryx.logger.Log;
import net.apryx.network.aoe.BMessage;
import net.apryx.time.Time;

public class GameObjectPlayer extends NetworkGameObject{
	
	private float movementSpeed = 64;
	private Sprite sprite;
	
	private boolean isTryCasting = false;
	
	private Spell currentSpell = null;
	private float spellTime = 0;
	
	public GameObjectPlayer(float x, float y){
		super(x,y);
		sprite = new Sprite(ApryxResources.player);
		sprite.center();
		sprite.setyOffset(sprite.getHeight());
		sprite.setStraightUp(true);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		
		if(isLocal()){
			batch.color(1, 0, 0);
			batch.depth(0);
			batch.drawRectangleZ(targetX-1, targetY, -1, 2, 2);
			if(isTryCasting){
				batch.color(0.5f, 0.5f, 1);
				batch.depth(0);
				batch.drawRectangleZ(world.getMouseX()-5, world.getMouseY(), -5, 10, 10);
			}
		}
		
		float drawXOffset = 0;
		float drawYOffset = 0;
		float drawZOffset = 0;
		
		if(currentSpell != null){
			SpellKeyframe frame = currentSpell.getKeyframe(spellTime);
			drawXOffset = frame.getxOffset();
			drawYOffset = frame.getyOffset();
			drawZOffset = frame.getzOffset();
		}
		
		batch.color(1,1,1);
		batch.drawSprite(sprite, x + drawXOffset, y + drawYOffset, drawZOffset, x > targetX ? -1 : 1, 1);
	}
	
	@Override
	public void update() {
		super.update();
		
		if(isLocal()){
			updateLocal();
		}else{
			updateNetwork();
		}
		
		if(currentSpell != null){
			spellTime += Time.deltaTime;
			if(spellTime > currentSpell.getDuration()){
				currentSpell = null;
				setChanged();
			}
		}
		
		
	}
	
	@Override
	public void process(BMessage m) {
		super.process(m);
		
		if(m.getType() == BMessage.S_CAST){
			this.world.addGameObject(new GameObjectFireball(x,y, m.getFloat("x", 0), m.getFloat("y", 0)));
			this.currentSpell = ApryxSpells.fireball;
			spellTime = 0;
		}
	}
	
	public void updateLocal(){
		if(!isTryCasting){
			if(Input.isMouseButtonPressed(Mouse.RIGHT)){
				targetX = world.getMouseX();
				targetY = world.getMouseY();
				setChanged();
			}
			
			if(Input.isKeyPressed(Keys.Q)){
				isTryCasting = true;
			}
		}else{
			if(Input.isMouseButtonPressed(Mouse.RIGHT)){
				isTryCasting = false;
			}
			if(Input.isMouseButtonPressed(Mouse.LEFT)){
				isTryCasting = false;
				
				BMessage message = new BMessage(BMessage.C_CAST);
				message.set("spell", "fireball");
				message.set("x", world.getMouseX());
				message.set("y", world.getMouseY());
				
				ApryxGame.instance.network.getClient().send(message);
			}
		}
		
		
		moveToTarget(movementSpeed);
	}
	
	public void updateNetwork(){
		moveToTarget(movementSpeed);
	}
	
	@Override
	public boolean moveToTarget(float speed) {
		if(currentSpell != null){
			SpellKeyframe frame = currentSpell.getKeyframe(spellTime);
			if(frame.isUnmoveable()){
				return true;
			}
		}
		return super.moveToTarget(speed);			
		
	}
}
