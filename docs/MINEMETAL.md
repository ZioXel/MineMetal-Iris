# MineMetal-Iris

Fork of [Iris](https://github.com/IrisShaders/Iris) that runs shader packs on **MineMetal**, a native Apple Metal
backend for Minecraft 26.x (https://github.com/ZioXel/MineMetal). On OpenGL it behaves exactly like upstream Iris.

Licensed LGPL-3.0 like upstream Iris.

## How the fork is structured (keep upstream merges cheap)

* `upstream` remote = IrisShaders/Iris, our work lives on branch `metal`.
* Iris' own files are changed **only in import lines**: `org.lwjgl.opengl.GLxxC` and (outside mixins)
  `com.mojang.renderpearl.backend.opengl.GlStateManager` are imported from `net.irisshaders.iris.metal.gl` instead.
* The facade classes in `net.irisshaders.iris.metal.gl` extend the originals, so every member is inherited and the
  fork behaves identically on OpenGL. To support Metal, a facade *hides* a static method and routes it to MineMetal
  when `MetalGlBridge.isMetal()` – everything else stays real OpenGL.
* After merging a new upstream release, run `tools/swap-imports.py` so new upstream files use the facades too.

## Roadmap

| Stage | Scope |
|---|---|
| 0 | Feasibility (done): 197/198 BSL shaders translate GLSL → SPIR-V → MSL → Metal |
| 1 | Post-processing: render targets, composite/deferred/final passes, uniforms |
| 2 | G-buffers: terrain/entities through pack programs (hooks in MineMetal's render pass) |
| 3 | Shadow pass |
| 4 | Pack compatibility (BSL → Complementary), compute/SSBO/images via native Metal compute |

## Building

`./metal.sh build` builds the Fabric jar and installs it into `../MineMetal/run/mods-stage0` for testing.

## Stage 1 – post-processing on Metal (in progress)

When MineMetal's Metal backend is active (`MetalGlBridge.isMetal()`), the fork:

- routes all GL calls through the generated facades in `net.irisshaders.iris.metal.gl` to MineMetal's `GLDispatch`/`MetalGL`;
- uses the bind-based (non-DSA) code paths, no compute / image load-store / SSBO / per-buffer blending;
- gives renderpearl textures GL names (`MixinGpuTexture2` → `MetalIris.glId`) and binds Minecraft's main target as an
  emulated framebuffer (`MixinRenderTarget` → `MetalIris.bindFramebuffer`);
- replaces the "custom pass on a renderpearl RenderPass" trick (final pass, center depth sampler) with direct emulated draws;
- does **not** create gbuffer or shadow programs, does not render shadows, keeps Sodium's compact vertex format and
  Minecraft's reversed-Z projection – the world is still drawn by the vanilla Metal pipelines;
- copies the main color target into `colortex0` before the composite passes so deferred/composite/final have a scene.

Build order: `cd ../MineMetal && ./mm.sh build`, then `./metal.sh build`, then `cd ../MineMetal && ./mm.sh run -PirisMetal`.

### Status (2026-10-05)

Stage 1 works on an M2: BSL's deferred/composite/final passes run on Metal at 60 fps (vsync). Expected for now:
washed-out image (passes run on an already-lit vanilla image, reversed-Z depth so no fog) and no shadows.

Next – stage 2 (gbuffers): let ShaderMap build the pack's gbuffer programs on Metal and make the world draw with them:
1. ExtendedShader/FallbackShader extend Mojang's GlProgram – need a Metal-side equivalent (a renderpearl pipeline
   whose shaders are the Iris-patched GLSL, compiled through MineMetal's GLSL→SPIR-V→MSL path).
2. Re-enable the extended vertex formats (IrisVertexFormats.TERRAIN etc.) once those pipelines consume them.
3. Undo reversed-Z on Metal too (flip clears + depth compare ops in MineMetal while a pack is active).
4. Drop the colortex0 copy in MetalIris once gbuffers write the scene.
Then stage 3 (shadow pass) and stage 4 (compute / image load-store / SSBOs via native Metal compute).

Iteration loop: `cd ../MineMetal && ./mm.sh iris` (builds MineMetal + this fork, runs on Metal); logs in
`MineMetal/.reference/run.log` and `iris-build.log`.

### Status (2026-10-05, later)

Stage 2 works: BSL's gbuffer programs draw terrain (Sodium), sky, clouds, entities, items, particles and the hand on
Metal at ~90 fps on an M2 (MineMetal override pipelines + GL emulation). Reversed Z stays native (Iris' shader depth
transforms handle it, as on Vulkan). Key fixes on the way: unique uniform-block/sampler bindings before SPIRV-Cross
(aliasing broke entity transforms), GL-style persistent UBO binding points, int/uint attribute signedness, default
values for missing vertex attributes. Debug switches (MineMetal): -Dminemetal.debugSolid / debugNoDepth /
debugEntityFormat / debugVertices = <pipeline substring>, -Dminemetal.traceFrame=true.

Next: stage 3 – the shadow pass (ShadowRenderer on Metal: shadow programs, shadow render targets, depth flip for the
shadow projection), then stage 4 (compute, image load/store, SSBOs).
