package net.apryx.apryxonline.spells;

import net.apryx.apryxonline.GameObjectFireball;
import net.apryx.game.GameObject;

public class SpellFireball extends Spell{

	public SpellFireball() {
		super("fireball", 0.5f);
		addKeyframe(new SpellKeyframe(0f).setOffset(0, 0, 2).setUnmoveable(true));
		addKeyframe(new SpellKeyframe(0.5f).setUnmoveable(false).setCast(true));
	}
	
	public void cast(GameObject caster, float targetX, float targetY){
		caster.getWorld().addGameObject(new GameObjectFireball(caster.x, caster.y, targetX, targetY));
	}

}