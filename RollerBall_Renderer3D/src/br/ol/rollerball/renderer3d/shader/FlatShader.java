package br.ol.rollerball.renderer3d.shader;

import br.ol.rollerball.math.Vec4;
import br.ol.rollerball.renderer3d.core.Light;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.core.Shader;
import br.ol.rollerball.renderer3d.rasterizer.Vertex;

/**
 *
 * @author leonardo
 */
public class FlatShader extends Shader {
    
    public static double minIntensity = 0.5;
    private Vec4 vertexLightDirection = new Vec4();
    private int[] flatColor = new int[4];
    
    public FlatShader() {
        super(0, 0, 2);
        
    }
    
    public int[] getColor() {
        return flatColor;
    }
    
    public void setColor(int a, int r, int g, int b) {
        flatColor[0] = a;
        flatColor[1] = r;
        flatColor[2] = g;
        flatColor[3] = b;
    }
    
    @Override
    public void processVertex(Renderer renderer, Vertex vertex) {
        // renderer.doVertexMVPTransformation(vertex);
        
        // perspective correct texture mapping
        double zInv = 1 / vertex.p.z;
        vertex.vars[0] = zInv;
        
        // simple light
        Light light = renderer.getLights().get(0);
        vertexLightDirection.set(light.position);
        // renderer.getMvp().multiply(vertexLightDirection);
        
        vertexLightDirection.sub(vertex.p);
        double p = vertex.normal.getRelativeCosBetween(vertexLightDirection);
        if (p < minIntensity) {
            p = minIntensity;
        }
        else if (p > 1) {
            p = 1;
        }
        //p = 1;
        vertex.vars[1] = p;
    }
    
    @Override
    public void processPixel(Renderer renderer, int xMin, int xMax, int x, int y, double[] vars) {
        double depth = vars[0];
        double p = vars[1];
        
        color[0] = 255;
        color[1] = (int) (flatColor[1] * p);
        color[2] = (int) (flatColor[2] * p);
        color[3] = (int) (flatColor[3] * p);
        
        renderer.setPixel(x, y, color, depth);
    }

}
