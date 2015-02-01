package thefreakyfox.advancedmod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;


public abstract class CommonProxy {

	public abstract void preInit();

	public abstract void init();

	public abstract void postInit();

	public abstract Side getSide();

	public abstract EntityPlayer getClientPlayer();

}
