package br.ol.rollerball.renderer3d.shader;

import br.ol.rollerball.math.MathUtil;
import br.ol.rollerball.math.Vec4;
import br.ol.rollerball.renderer3d.core.Image;
import br.ol.rollerball.renderer3d.core.Light;
import br.ol.rollerball.renderer3d.core.Material;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.core.Shader;
import br.ol.rollerball.renderer3d.rasterizer.Vertex;

/**
 *
 * @author leonardo
 */
public class GouraudShaderWithTexture extends Shader {
    
    public static double scale = 1.0;
    public static double minIntensity = 0.5;
    public static double maxIntensity = 1.0;
    
    public GouraudShaderWithTexture() {
        super(0, 0, 6);
    }
    
    private Vec4 vertexLightDirection = new Vec4();

    @Override
    public void processVertex(Renderer renderer, Vertex vertex) {
        // renderer.doVertexMVPTransformation(vertex);
        
        // perspective correct texture mapping
        double zInv = 1 / vertex.p.z;
        vertex.vars[0] = zInv;
        vertex.vars[1] = vertex.st.x * zInv;
        vertex.vars[2] = vertex.st.y * zInv;
        
        // simple light
        Light light = renderer.getLights().get(0);
        vertexLightDirection.set(light.position);
        // renderer.getMvp().multiply(vertexLightDirection);
        
        vertexLightDirection.sub(vertex.p);
        double p = vertex.normal.getRelativeCosBetween(vertexLightDirection);
        if (p < minIntensity) {
            p = minIntensity;
        }
        if (p > maxIntensity) {
            p = maxIntensity;
        }
        //p = 1;
        vertex.vars[3] = p * light.diffuse.x;
        vertex.vars[4] = p * light.diffuse.y;
        vertex.vars[5] = p * light.diffuse.z;
    }
    
    @Override
    public void processPixel(Renderer renderer, int xMin, int xMax, int x, int y, double[] vars) {
        double depth = vars[0];
        double z = 1 / depth;
        double s = vars[1] * z;
        double t = vars[2] * z;

        s = s > 1 ? s - (int) s : s < 0 ? (int) s - s : MathUtil.clamp(s, 0, 1);
        t = t > 1 ? t - (int) t : t < 0 ? (int) t - t : MathUtil.clamp(t, 0, 1);

        double colorp1 = vars[3];
        double colorp2 = vars[4];
        double colorp3 = vars[5];
        
        Material material = renderer.getMaterial();
        Image texture = null;
        
        if (material != null) {
            texture = renderer.getMaterial().map_kd;
        }
        
        if (texture != null) {
            int tx = (int) (s * (texture.getWidth() - 1));
            int textureHeight = texture.getHeight() - 1;
            int ty = textureHeight - (int) (t * textureHeight);
            texture.getPixel(tx, ty, color);
        }
        else {
            color[0] = 0;
            color[1] = 0;
            color[2] = 0;
            color[3] = 0;
        }
        
        color[0] = 255;
        color[1] = (int) MathUtil.clamp(color[1] * colorp1 * scale, 0, 255);
        color[2] = (int) MathUtil.clamp(color[2] * colorp2 * scale, 0, 255);
        color[3] = (int) MathUtil.clamp(color[3] * colorp3 * scale, 0, 255);
        
        renderer.setPixel(x, y, color, depth);
    }

}
