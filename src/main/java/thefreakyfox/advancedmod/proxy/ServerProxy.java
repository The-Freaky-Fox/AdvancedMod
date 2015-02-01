package thefreakyfox.advancedmod.proxy;

import cpw.mods.fml.relauncher.Side;


public class ServerProxy extends CommonProxy {

	@Override
	public void preInit() {}

	@Override
	public void init() {}

	@Override
	public void postInit() {}

	@Override
	public Side getSide() {
		return Side.SERVER;
	}

}
