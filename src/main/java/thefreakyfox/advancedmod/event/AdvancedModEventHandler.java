package thefreakyfox.advancedmod.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import thefreakyfox.advancedmod.init.ModBlocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;


public class AdvancedModEventHandler {

	@SubscribeEvent
	public void addPigDrops( LivingDropsEvent event ) {
		if ( event.entityLiving instanceof EntityPig && event.entityLiving.getRNG().nextInt( 3 ) == 0 ) {
			final ItemStack stack = new ItemStack( ModBlocks.dutchFlag );
			event.drops.add( new EntityItem( event.entity.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, stack ) );
		}
	}

	@SubscribeEvent
	public void onPlayerTick( PlayerTickEvent event ) {
		if ( event.side == Side.SERVER && event.phase == TickEvent.Phase.END ) {
			// LogHelper.info( String.format( "Player %s ticked in phase: %s",
			// event.player.getCommandSenderName(), event.phase ) );
		}
	}

}
