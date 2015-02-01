package thefreakyfox.advancedmod.client.settings;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import thefreakyfox.advancedmod.reference.Names;


public enum KeyBindings {

	EXPLODE( Names.Keys.EXPLODE, Keyboard.KEY_G ),
	EXPLODE_BIG( Names.Keys.EXPLODE_BIG, Keyboard.KEY_H );

	private final KeyBinding keyBinding;

	private KeyBindings( String keyName, int defaultKeyCode ) {
		keyBinding = new KeyBinding( keyName, defaultKeyCode, Names.Keys.CATEGORY );
	}

	public KeyBinding getKeybind() {
		return keyBinding;
	}

	public boolean isPressed() {
		return keyBinding.isPressed();
	}

}
