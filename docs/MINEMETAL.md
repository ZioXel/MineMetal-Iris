# MineMetal-Iris

Fork of [Iris](https://github.com/IrisShaders/Iris) that runs shader packs on **MineMetal**, a native Apple Metal
backend for Minecraft 26.x (https://github.com/ZioXel/MineMetal). On OpenGL it behaves exactly like upstream Iris.

Licensed LGPL-3.0 like upstream Iris.

## How the fork is structured (keep upstream merges cheap)

* `upstream` remote = IrisShaders/Iris, our work lives on branch `metal`.
* Iris' own files are changed mostly **in import lines**: `org.lwjgl.opengl.GLxxC` and (outside mixins)
  `com.mojang.renderpearl.backend.opengl.GlStateManager` are imported from `net.irisshaders.iris.metal.gl` instead.
* The facade classes in `net.irisshaders.iris.metal.gl` extend the originals, so every member is inherited and the
  fork behaves identically on OpenGL. Functions Iris calls are re-declared there and routed to MineMetal's
  `GLDispatch` / `MetalGL` when `MetalGlBridge.isMetal()`.
* Every other Metal-specific change is guarded by `MetalGlBridge.isMetal()`; Metal-only code lives in
  `net.irisshaders.iris.metal.MetalIris`, which is never loaded on OpenGL.
* After merging a new upstream release, run `tools/swap-imports.py` so new upstream files use the facades too.

## Building

`./metal.sh build` builds the Fabric jar and installs it into `../MineMetal/run/mods-stage0`.
Usual loop: `cd ../MineMetal && ./mm.sh iris` (builds MineMetal + this fork, runs on Metal). Logs:
`MineMetal/.reference/run.log`, `iris-build.log`, `.reference/logs/run-*.log`.

## Status (2026-10-06)

| Stage | Scope | |
|---|---|---|
| 0 | Feasibility: BSL shaders translate GLSL → SPIR-V → MSL | ✅ |
| 1 | Post-processing: render targets, deferred/composite/final passes, uniforms | ✅ |
| 2 | G-buffers: terrain (Sodium), entities, particles, sky, hand through pack programs | ✅ |
| 3 | Shadow pass and shadow composites | ✅ |
| 4 | Compute shaders, image load/store, SSBOs, per-buffer blending, >16 samplers | ✅ |

Tested on an M2 MacBook Air: BSL 10.1.8, Complementary Reimagined r5.9.3, Complementary Unbound r5.9.3 (all
profiles, including colored lighting / voxel light propagation), MakeUp Ultra Fast 9.5g, Sildur's Vibrant 2.02.

## How shader packs run on Metal

* **GL emulation.** All GL calls go through the facades to MineMetal's `MetalGL`, an emulation of the GL subset Iris
  uses. Programs are compiled GLSL → SPIR-V (shaderc, relaxed Vulkan rules so loose uniforms form an implicit
  block) → MSL (SPIRV-Cross). Iris uses the bind-based (non-DSA) code paths.
* **Post-processing** (deferred, composite, final, shadowcomp, center depth) draws straight through the emulation
  (`MetalIris.drawFullscreenPass`) instead of Iris' "custom pass on a renderpearl RenderPass" trick.
* **World and shadow shaders.** `MixinShaderManager_Overrides` builds a MineMetal `GlOverridePipeline` per Iris
  program. Inside Minecraft's Metal render passes it switches the encoder to the pack's framebuffer (or the shadow
  map), binds resources through the same GL binding points Iris uses on OpenGL, and switches back for vanilla draws.
  While shadows render, the depth compare is mirrored (the shadow map uses forward depth, like Iris on GL), culling
  is off and vanilla draws are skipped.
* **Reversed Z** stays native: Iris' shader transforms (`DepthTransformer`) handle it, as on Vulkan.
* **Stage 4.** Compute programs become Metal compute pipelines; `glDispatchCompute(Indirect)` runs them in a compute
  encoder. Storage images are bound by GL image unit (textures get shader-write usage the first time they are bound
  as images), SSBOs by GL binding point; MSL 3.1 is used for these programs (native texture atomics).
* **Limits.** Metal has 16 sampler states per stage: programs with more samplers share slots between samplers with
  identical state (compiled per sharing pattern). 32 texture units, 8 image units, 9 SSBOs.

## Behaviour differences on Metal

* `MC_OS_MAC` is **not** defined on Metal (packs use it to avoid Apple's OpenGL 4.1 limits, which do not apply
  here); `MINEMETAL` is defined instead. `-Dminemetal.reportMacOS=true` restores `MC_OS_MAC`.
* GLSL below `#version 430` gets `GL_ARB_shading_language_420pack`, `GL_ARB_shader_storage_buffer_object`,
  `GL_ARB_shader_image_load_store` and `GL_ARB_explicit_uniform_location` enabled (GL drivers accept those
  features there; glslang is strict).
* A colortex bound for image load/store from inside a world pass is made writable at the end of the frame; such
  writes are missing for the first frame after a pack loads.
* PBR normal/specular maps use a nearest, mipmapped sampler (no anisotropy).

## Debugging (MineMetal flags, `-PmmArgs="..."`)

`-Dminemetal.gpuProfile=true` (per-phase GPU/CPU times), `-Dminemetal.glSelfTest=true`,
`-Dminemetal.dumpShaders=true` (MSL of rejected programs), `-Dminemetal.debugSolid` / `debugNoDepth` /
`debugEntityFormat` / `debugVertices` = <pipeline name substring>, `-Dminemetal.traceFrame=true`,
`-Dminemetal.noCoalesce=true`.

## Next

Performance at full Retina resolution (heavy packs are GPU-bound there), then wider pack testing
(Photon, Rethinking Voxels, Solas, …).
