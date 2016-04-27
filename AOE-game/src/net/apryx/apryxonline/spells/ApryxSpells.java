package net.apryx.apryxonline.spells;

public class ApryxSpells {
	
	public static Spell fireball = new Spell("fireball", 0.5f)
		.addKeyframe(new SpellKeyframe(0).setUnmoveable(true))
		.addKeyframe(new SpellKeyframe(0.5f).setUnmoveable(false));

}
