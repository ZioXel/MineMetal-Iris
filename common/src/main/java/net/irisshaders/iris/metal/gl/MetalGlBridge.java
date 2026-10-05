package net.irisshaders.iris.metal.gl;

/**
 * Switch between real OpenGL and the MineMetal emulation layer. Iris runs unchanged on OpenGL; when Minecraft runs on
 * MineMetal's Metal backend, intercepted GL calls in the facade classes of this package are routed to MineMetal.
 */
public final class MetalGlBridge {
	private MetalGlBridge() {
	}

	/** True when the active renderpearl device is MineMetal's Metal backend. */
	public static boolean isMetal() {
		try {
			return "Metal".equals(com.mojang.blaze3d.systems.RenderSystem.getDevice().getDeviceInfo().backendName());
		} catch (RuntimeException e) {
			return false;
		}
	}
}
