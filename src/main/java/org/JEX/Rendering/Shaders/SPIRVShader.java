package org.JEX.Rendering.Shaders;

import org.JEX.Logs.Exceptions.JEXception;
import org.JEX.Logs.Log;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.shaderc.Shaderc;
import org.lwjgl.vulkan.VK;
import org.lwjgl.vulkan.VkShaderModuleCreateInfo;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.vulkan.VK10.*;

public non-sealed class SPIRVShader extends ShaderBase {

    long shaderModule = -1L;

    public SPIRVShader(ShaderType type) {
        super(type);
    }

    private ByteBuffer shaderc_compile(){
        String source = shaderSourceString;
        ByteBuffer buffer = BufferUtils.createByteBuffer(0);
        buffer.put(new byte[0]);
        buffer.flip();
        return buffer;
    }

    @Override
    void compile() {
        ByteBuffer buff = shaderc_compile();

        VkShaderModuleCreateInfo moduleCreateInfo = VkShaderModuleCreateInfo.calloc()
                .sType$Default()
                .pCode(buff);
        LongBuffer pShaderModule = memAllocLong(1);
        int err = vkCreateShaderModule(device, moduleCreateInfo, null, pShaderModule);

        memFree(pShaderModule);
        if (err != VK_SUCCESS) {
            Log.error(new JEXception("Failed to compile vulkan shader"));
        }
        else{
            shaderModule = pShaderModule.get(0);
        }
    }

    @Override
    protected void destroy() {

    }

    @Override
    public void enableShader() {

    }
}
