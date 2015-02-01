package thefreakyfox.advancedmod.client.handler;

import thefreakyfox.advancedmod.client.settings.KeyBindings;
import thefreakyfox.advancedmod.network.MessageExplode;
import thefreakyfox.advancedmod.network.NetworkHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;


public class KeyInputEventHandler {

	private KeyBindings getPressedKey() {
		for ( KeyBindings key : KeyBindings.values() ) {
			if ( key.isPressed() )
				return key;
		}
		return null;
	}

	@SubscribeEvent
	public void handleKeyInputEvent( InputEvent.KeyInputEvent event ) {
		KeyBindings key = getPressedKey();
		if ( key == null )
			return;
		switch ( key ) {
			case EXPLODE:
				NetworkHandler.sendToServer( new MessageExplode( 3.0F ) );
				break;
			case EXPLODE_BIG:
				NetworkHandler.sendToServer( new MessageExplode( 30.0F ) );
				break;
		}
	}

}
