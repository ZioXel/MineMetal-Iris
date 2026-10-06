package net.irisshaders.iris.metal.gl;

/**
 * Switch between real OpenGL and MineMetal's GL emulation. Iris runs unchanged on OpenGL; when Minecraft runs on
 * MineMetal's Metal backend, the facade classes in this package route the GL calls Iris makes to MineMetal.
 */
public final class MetalGlBridge {
	private static volatile int state; // 0 = unknown, 1 = metal, 2 = not metal

	private MetalGlBridge() {
	}

	/** Marks a frame phase for MineMetal's profiler (-Dminemetal.gpuProfile); no-op on OpenGL. */
	public static void phase(String name) {
		if (isMetal()) {
			net.irisshaders.iris.metal.MetalIris.phase(name);
		}
	}

	/** True when the active renderpearl device is MineMetal's Metal backend (cached once the device exists). */
	public static boolean isMetal() {
		int s = state;
		if (s != 0) {
			return s == 1;
		}
		try {
			var device = com.mojang.blaze3d.systems.RenderSystem.tryGetDevice();
			if (device == null) {
				return false;
			}
			boolean metal = "Metal".equals(device.getDeviceInfo().backendName());
			state = metal ? 1 : 2;
			return metal;
		} catch (RuntimeException | LinkageError e) {
			return false;
		}
	}
}
