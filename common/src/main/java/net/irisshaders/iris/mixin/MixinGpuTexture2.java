package net.irisshaders.iris.mixin;

import com.mojang.renderpearl.api.textures.GpuTexture;
import com.mojang.renderpearl.backend.common.BaseGpuTexture;
import net.irisshaders.iris.mixinterface.GpuTextureInterface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BaseGpuTexture.class)
public abstract class MixinGpuTexture2 implements GpuTextureInterface {
	@Override
	public int iris$getGlId() {
		if (net.irisshaders.iris.metal.gl.MetalGlBridge.isMetal()) {
			return net.irisshaders.iris.metal.MetalIris.glId((com.mojang.renderpearl.api.textures.GpuTexture) (Object) this);
		}
		throw new AssertionError("Why.");
	}

	@Override
	public void iris$markMipmapNonLinear() {
		if (net.irisshaders.iris.metal.gl.MetalGlBridge.isMetal()) {
			return;
		}
		throw new AssertionError("Why.");
	}
}
