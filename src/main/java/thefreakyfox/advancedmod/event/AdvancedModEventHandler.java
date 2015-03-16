package thefreakyfox.advancedmod.event;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
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
			final List <Entity> entities = event.player.worldObj.getEntitiesWithinAABB( EntityLivingBase.class, AxisAlignedBB.getBoundingBox( event.player.posX - 3,
					event.player.posY - 3, event.player.posZ - 3, event.player.posX + 3, event.player.posY + 3, event.player.posZ + 3 ) );
			for ( final Entity entity : entities ) {
				if ( !entity.equals( event.player ) ) {
					// entity.setVelocity( 0, 1, 0 ); // Crashes on dedicated server
					entity.motionX = 0;
					entity.motionY = 1;
					entity.motionZ = 0;
				}
			}
		}
		// LogHelper.info( String.format( "Player %s ticked in phase: %s",
		// event.player.getCommandSenderName(), event.phase ) );
	}
}
