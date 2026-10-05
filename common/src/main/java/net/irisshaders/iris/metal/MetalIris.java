package net.irisshaders.iris.metal;

import com.mojang.renderpearl.api.textures.GpuTexture;
import dev.minemetal.client.glcompat.MetalGL;
import net.irisshaders.iris.gl.IrisRenderSystem;
import net.irisshaders.iris.gl.blending.BlendModeOverride;
import net.irisshaders.iris.metal.gl.GlStateManager;
import net.irisshaders.iris.targets.RenderTarget;
import net.irisshaders.iris.targets.RenderTargets;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL30C;

/**
 * Metal-only code paths of the MineMetal Iris fork. Only ever called behind {@code MetalGlBridge.isMetal()}, so this
 * class (and MineMetal's classes it references) is never loaded when Iris runs on OpenGL.
 */
public final class MetalIris {
	private MetalIris() {
	}

	/** GL name of a renderpearl texture inside MineMetal's GL emulation. */
	public static int glId(GpuTexture texture) {
		return MetalGL.registerExternalTexture(texture);
	}

	/** Binds an emulated framebuffer that renders into the given renderpearl color (and depth) texture. */
	public static void bindFramebuffer(GpuTexture color, @Nullable GpuTexture depth) {
		MetalGL.glBindFramebuffer(GL30C.GL_FRAMEBUFFER, MetalGL.externalFramebuffer(color, depth));
	}

	/**
	 * Replacement for Iris' "custom pass" trick on a renderpearl RenderPass (which only works on the GL backend): draws
	 * the fullscreen quad with the currently used Iris program into the currently bound framebuffer, using the same
	 * fixed state the GL mixin sets up.
	 */
	public static void drawFullscreenPass(int viewportWidth, int viewportHeight, boolean setViewport) {
		BlendModeOverride.restore();
		IrisRenderSystem.disableBlend();
		GlStateManager._disableScissorTest();
		GlStateManager._disableDepthTest();
		GlStateManager._depthMask(false);
		GlStateManager._colorMask(15);
		if (setViewport) {
			GlStateManager._viewport(0, 0, viewportWidth, viewportHeight);
		}
		MetalGL.drawFullscreenQuad();
	}

	/**
	 * Stage 1 of the Metal port: the world is still drawn by the vanilla (Metal) pipelines into Minecraft's main
	 * target, not by the pack's gbuffer programs. Copy it into colortex0 (main and alt) so the pack's
	 * deferred/composite/final passes have the scene to work with.
	 */
	public static void copyMainColorToColortex0(RenderTargets targets) {
		if (targets.getRenderTargetCount() == 0) {
			return;
		}
		RenderTarget colortex0 = targets.getOrCreate(0);
		int main = glId(Minecraft.getInstance().gameRenderer.mainRenderTarget().getColorTexture());
		MetalGL.blitColor(main, colortex0.getMainTexture());
		MetalGL.blitColor(main, colortex0.getAltTexture());
	}
}
