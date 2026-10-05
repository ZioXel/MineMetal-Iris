package net.irisshaders.iris.metal.gl;

/**
 * MineMetal facade for Minecraft's {@link com.mojang.renderpearl.backend.opengl.GlStateManager} (GENERATED – do not
 * edit by hand). Non-mixin Iris code imports this class; the methods Iris calls are routed to MineMetal on Metal.
 */
@SuppressWarnings("unused")
public class GlStateManager extends com.mojang.renderpearl.backend.opengl.GlStateManager {
	public static void _activeTexture(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_activeTexture(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._activeTexture(a0);
	}

	public static void _bindTexture(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_bindTexture(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._bindTexture(a0);
	}

	public static void _blendFuncSeparate(final int a0, final int a1, final int a2, final int a3) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_blendFuncSeparate(a0, a1, a2, a3);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._blendFuncSeparate(a0, a1, a2, a3);
	}

	public static void _clear(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_clear(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._clear(a0);
	}

	public static void _colorMask(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_colorMask(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._colorMask(a0);
	}

	public static void _colorMask(final int a0, final int a1) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_colorMask(a0, a1);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._colorMask(a0, a1);
	}

	public static void _deleteTexture(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_deleteTexture(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._deleteTexture(a0);
	}

	public static void _depthFunc(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_depthFunc(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._depthFunc(a0);
	}

	public static void _depthMask(final boolean a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_depthMask(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._depthMask(a0);
	}

	public static void _disableBlend(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_disableBlend(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._disableBlend(a0);
	}

	public static void _disableCull() {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_disableCull();
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._disableCull();
	}

	public static void _disableDepthTest() {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_disableDepthTest();
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._disableDepthTest();
	}

	public static void _disablePolygonOffset() {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_disablePolygonOffset();
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._disablePolygonOffset();
	}

	public static void _disableScissorTest() {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_disableScissorTest();
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._disableScissorTest();
	}

	public static void _drawElements(final int a0, final int a1, final int a2, final long a3) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_drawElements(a0, a1, a2, a3);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._drawElements(a0, a1, a2, a3);
	}

	public static void _enableBlend(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_enableBlend(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._enableBlend(a0);
	}

	public static void _enableCull() {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_enableCull();
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._enableCull();
	}

	public static void _enableDepthTest() {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_enableDepthTest();
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._enableDepthTest();
	}

	public static void _enableScissorTest() {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_enableScissorTest();
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._enableScissorTest();
	}

	public static int _genTexture() {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_genTexture();
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager._genTexture();
	}

	public static int _getInteger(final int a0) {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_getInteger(a0);
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager._getInteger(a0);
	}

	public static java.lang.String _getString(final int a0) {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_getString(a0);
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager._getString(a0);
	}

	public static int _getTexLevelParameter(final int a0, final int a1, final int a2) {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_getTexLevelParameter(a0, a1, a2);
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager._getTexLevelParameter(a0, a1, a2);
	}

	public static void _glBindAttribLocation(final int a0, final int a1, final java.lang.CharSequence a2) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glBindAttribLocation(a0, a1, a2);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._glBindAttribLocation(a0, a1, a2);
	}

	public static void _glBindBuffer(final int a0, final int a1) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glBindBuffer(a0, a1);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._glBindBuffer(a0, a1);
	}

	public static void _glBindFramebuffer(final int a0, final int a1) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glBindFramebuffer(a0, a1);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._glBindFramebuffer(a0, a1);
	}

	public static void _glBindVertexArray(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glBindVertexArray(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._glBindVertexArray(a0);
	}

	public static void _glBufferSubData(final int a0, final long a1, final java.nio.ByteBuffer a2) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glBufferSubData(a0, a1, a2);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._glBufferSubData(a0, a1, a2);
	}

	public static void _glDeleteFramebuffers(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glDeleteFramebuffers(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._glDeleteFramebuffers(a0);
	}

	public static void _glFramebufferTexture2D(final int a0, final int a1, final int a2, final int a3, final int a4) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glFramebufferTexture2D(a0, a1, a2, a3, a4);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._glFramebufferTexture2D(a0, a1, a2, a3, a4);
	}

	public static int _glGenBuffers() {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_glGenBuffers();
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager._glGenBuffers();
	}

	public static int _glGenVertexArrays() {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_glGenVertexArrays();
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager._glGenVertexArrays();
	}

	public static int _glGetUniformLocation(final int a0, final java.lang.CharSequence a1) {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_glGetUniformLocation(a0, a1);
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager._glGetUniformLocation(a0, a1);
	}

	public static void _glUniform1i(final int a0, final int a1) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glUniform1i(a0, a1);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._glUniform1i(a0, a1);
	}

	public static void _glUseProgram(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glUseProgram(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._glUseProgram(a0);
	}

	public static void _pixelStore(final int a0, final int a1) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_pixelStore(a0, a1);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._pixelStore(a0, a1);
	}

	public static void _scissorBox(final int a0, final int a1, final int a2, final int a3) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_scissorBox(a0, a1, a2, a3);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._scissorBox(a0, a1, a2, a3);
	}

	public static void _viewport(final int a0, final int a1, final int a2, final int a3) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_viewport(a0, a1, a2, a3);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager._viewport(a0, a1, a2, a3);
	}

	public static void glAttachShader(final int a0, final int a1) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glAttachShader(a0, a1);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager.glAttachShader(a0, a1);
	}

	public static void glCompileShader(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glCompileShader(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager.glCompileShader(a0);
	}

	public static int glCreateProgram() {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_glCreateProgram();
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager.glCreateProgram();
	}

	public static int glCreateShader(final int a0) {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_glCreateShader(a0);
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager.glCreateShader(a0);
	}

	public static void glDeleteProgram(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glDeleteProgram(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager.glDeleteProgram(a0);
	}

	public static void glDeleteShader(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glDeleteShader(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager.glDeleteShader(a0);
	}

	public static int glGenFramebuffers() {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_glGenFramebuffers();
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager.glGenFramebuffers();
	}

	public static java.lang.String glGetProgramInfoLog(final int a0, final int a1) {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_glGetProgramInfoLog(a0, a1);
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager.glGetProgramInfoLog(a0, a1);
	}

	public static int glGetProgrami(final int a0, final int a1) {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_glGetProgrami(a0, a1);
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager.glGetProgrami(a0, a1);
	}

	public static int glGetShaderi(final int a0, final int a1) {
		if (MetalGlBridge.isMetal()) {
			return dev.minemetal.client.glcompat.GLDispatch.gsm_glGetShaderi(a0, a1);
		}
		return com.mojang.renderpearl.backend.opengl.GlStateManager.glGetShaderi(a0, a1);
	}

	public static void glLinkProgram(final int a0) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glLinkProgram(a0);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager.glLinkProgram(a0);
	}

	public static void glShaderSource(final int a0, final java.lang.String a1) {
		if (MetalGlBridge.isMetal()) {
			dev.minemetal.client.glcompat.GLDispatch.gsm_glShaderSource(a0, a1);
			return;
		}
		com.mojang.renderpearl.backend.opengl.GlStateManager.glShaderSource(a0, a1);
	}
}
