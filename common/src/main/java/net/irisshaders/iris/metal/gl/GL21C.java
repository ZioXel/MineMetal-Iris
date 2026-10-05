package net.irisshaders.iris.metal.gl;

/**
 * MineMetal facade for {@link org.lwjgl.opengl.GL21C}. Iris imports this class instead of the LWJGL one; every member is
 * inherited, so behaviour is identical to OpenGL until a method is redirected here (see {@link MetalGlBridge}).
 */
@SuppressWarnings("unused")
public class GL21C extends org.lwjgl.opengl.GL21C {
	protected GL21C() {
	}
}
