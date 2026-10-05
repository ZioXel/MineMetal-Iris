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
