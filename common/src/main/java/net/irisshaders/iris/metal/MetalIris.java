package net.irisshaders.iris.metal;

import com.mojang.renderpearl.api.textures.GpuTexture;
import dev.minemetal.client.glcompat.MetalGL;
import net.irisshaders.iris.gl.IrisRenderSystem;
import net.irisshaders.iris.gl.blending.BlendModeOverride;
import net.irisshaders.iris.metal.gl.GlStateManager;
import com.mojang.renderpearl.api.pipeline.BindGroupLayout;
import com.mojang.renderpearl.api.vertex.VertexFormat;
import com.mojang.renderpearl.backend.opengl.GlProgram;
import com.mojang.renderpearl.frontend.FrontendRenderPipeline;
import dev.minemetal.client.glcompat.GlOverridePipeline;
import net.irisshaders.iris.pipeline.programs.IrisBindings;
import net.irisshaders.iris.pipeline.programs.IrisProgram;
import java.util.ArrayList;
import java.util.List;
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

	static {
		// The shadow pass draws through renderpearl passes on the main target; only Iris' shadow programs may draw then.
		dev.minemetal.client.metal.MetalRenderPass.setSkipVanillaDraws(net.irisshaders.iris.shadows.ShadowRenderingState::areShadowsCurrentlyBeingRendered);
	}

	public static void phase(String name) {
		dev.minemetal.client.metal.MetalProfiler.phase(name);
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

	/** Like {@link #drawFullscreenPass} but keeps the blend state the caller set up (shadow composites). */
	public static void drawFullscreenQuadKeepBlend() {
		GlStateManager._disableScissorTest();
		GlStateManager._disableDepthTest();
		GlStateManager._depthMask(false);
		GlStateManager._colorMask(15);
		MetalGL.drawFullscreenQuad();
	}

	/**
	 * Metal replacement for the GL override pipeline Iris builds in MixinShaderManager_Overrides: draws with the Iris
	 * program through MineMetal's GL emulation, using the original pipeline's state and the same GL binding points
	 * Iris uses on OpenGL (see IrisBindings / MixinGlProgram).
	 */
	public static FrontendRenderPipeline overridePipeline(FrontendRenderPipeline original, GlProgram program, List<VertexFormat> formats) {
		IrisProgram irisProgram = (IrisProgram) program;
		List<BindGroupLayout.UniformDescription> uniforms = original.uniforms();
		GlOverridePipeline.Hooks hooks = new GlOverridePipeline.Hooks() {
			@Override
			public void setupState() {
				irisProgram.iris$setupState(uniforms);
			}

			@Override
			public void clearState() {
				irisProgram.iris$clearState();
			}

			@Override
			public int uniformBinding(String name) {
				return switch (name) {
					case "DynamicTransforms", "TerrainUniform" -> IrisBindings.DYNAMIC_TRANSFORMS;
					case "CloudInfo" -> IrisBindings.CLOUD_INFO;
					case "Projection" -> IrisBindings.PROJECTION;
					case "Globals" -> IrisBindings.GLOBALS;
					case "Fog" -> IrisBindings.FOG;
					case "Lighting" -> IrisBindings.LIGHTING;
					case "u_Globals" -> IrisBindings.SODIUM_GLOBALS;
					default -> -1;
				};
			}

			@Override
			public int samplerBinding(String name) {
				return switch (name) {
					case "Sampler0", "u_BlockTex" -> IrisBindings.ALBEDO_TEXTURE;
					case "Sampler1" -> IrisBindings.OVERLAY_TEXTURE;
					case "Sampler2", "u_LightTex" -> IrisBindings.LIGHTMAP_TEXTURE;
					case "CloudFaces", "u_SectionTimeInfo" -> IrisBindings.AUX_TEXTURE;
					default -> -1;
				};
			}

			@Override
			public int pushConstantBinding() {
				return IrisBindings.PUSH_CONSTANTS;
			}

			@Override
			public boolean shadowPass() {
				return net.irisshaders.iris.shadows.ShadowRenderingState.areShadowsCurrentlyBeingRendered();
			}
		};
		List<VertexFormat> vertexFormats = new ArrayList<>(formats);
		GlOverridePipeline backend = new GlOverridePipeline(original.backendRenderPipeline(), program.getProgramId(), vertexFormats, hooks);
		return new FrontendRenderPipeline(original.name(), backend, formats, original.uniformIndices(), original.uniforms(),
			original.colorTargetStates(), original.wantsDepthTexture(), original.pushConstantSize());
	}
}
