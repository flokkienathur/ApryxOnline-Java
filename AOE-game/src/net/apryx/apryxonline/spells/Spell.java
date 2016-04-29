package net.apryx.apryxonline.spells;

import java.util.ArrayList;
import java.util.List;

import net.apryx.game.GameObject;
import net.apryx.logger.Log;

public class Spell {
	
	private String name;
	private float duration;
	private List<SpellKeyframe> keyframes;
	
	public Spell(String n, float duration){
		this.name = n;
		this.duration = duration;
		this.keyframes = new ArrayList<SpellKeyframe>();
	}
	
	public float getDuration() {
		return duration;
	}
	
	public Spell setDuration(float duration) {
		this.duration = duration;
		return this;
	}
	
	
	public void cast(GameObject caster, float targetX, float targetY){
		
	}
	
	public SpellKeyframe getKeyframe(float time){
		if(keyframes.size() == 0)
			return null;
		
		for(int i = 0; i < keyframes.size() - 1; i++){
			SpellKeyframe low = keyframes.get(i);
			SpellKeyframe high = keyframes.get(i + 1);
			if(time >= low.getTime() && time <= high.getTime()){
				return low;
			}
		}

		SpellKeyframe low = keyframes.get(0);
		SpellKeyframe high = keyframes.get(keyframes.size() - 1);
		
		if(time <= low.getTime())
			return low;
		if(time >= high.getTime())
			return high;
		
		Log.debug("Should be printed, ever. (getKeyframe Spell)");
		return null;
		
	}
	
	public String getName() {
		return name;
	}
	
	public Spell setName(String name) {
		this.name = name;
		return this;
	}
	
	public Spell addKeyframe(SpellKeyframe frame){
		keyframes.add(frame);
		return this;
	}
}
