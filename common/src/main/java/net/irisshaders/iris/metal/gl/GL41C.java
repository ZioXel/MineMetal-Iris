package net.irisshaders.iris.metal.gl;

/**
 * MineMetal facade for {@link org.lwjgl.opengl.GL41C}. Iris imports this class instead of the LWJGL one; every member is
 * inherited, so behaviour is identical to OpenGL until a method is redirected here (see {@link MetalGlBridge}).
 */
@SuppressWarnings("unused")
public class GL41C extends org.lwjgl.opengl.GL41C {
	protected GL41C() {
	}
}
