package net.irisshaders.iris.metal.gl;

/**
 * MineMetal facade for {@link org.lwjgl.opengl.GL11}. Iris imports this class instead of the LWJGL one; every member is
 * inherited, so behaviour is identical to OpenGL until a method is redirected here (see {@link MetalGlBridge}).
 */
@SuppressWarnings("unused")
public class GL11 extends org.lwjgl.opengl.GL11 {
	protected GL11() {
	}
}
