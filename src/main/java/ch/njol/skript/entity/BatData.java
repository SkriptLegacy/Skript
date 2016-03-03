package ch.njol.skript.entity;

import org.bukkit.entity.Bat;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class BatData extends EntityData<Bat> {
	
	static {
		if(Skript.classExists("org.bukkit.entity.Bat")){
			EntityData.register(BatData.class, "bat", Bat.class,1, "awake bat", "bat", "sleeping bat");
		}
	}

	private int awake = 0;
	
	@Override
	protected boolean init(Literal<?>[] exprs, int matchedPattern, ParseResult parseResult) {
		this.awake = matchedPattern - 1;
		return true;
	}

	@Override
	protected boolean init(Class<? extends Bat> c, Bat e) {
		this.awake = e == null ? 0 : e.isAwake() ? 1 : -1;
		return true;
	}

	@Override
	public void set(Bat bat) {
		if (this.awake != 0)
			bat.setAwake(this.awake == 1);
		
	}

	@Override
	protected boolean match(Bat bat) {
		return this.awake == 0 || bat.isAwake() == (awake == 1);
	}

	@Override
	public Class<Bat> getType() {
		return Bat.class;
	}

	@Override
	public EntityData getSuperType() {
		return new BatData();
	}

	@Override
	protected int hashCode_i() {
		return this.awake;
	}

	@Override
	protected boolean equals_i(EntityData<?> obj) {
		if (!(obj instanceof BatData))
			return false;
		final BatData other = (BatData) obj;
		return awake == other.awake;
	}

	@Override
	public boolean isSupertypeOf(EntityData<?> e) {
		if (e instanceof BatData)
			return this.awake == 0 || ((BatData) e).awake == awake;
		return false;
	}
	
	@Override
	protected boolean deserialize(final String s) {
		try {
			awake = Integer.parseInt(s);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}
}