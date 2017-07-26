package br.ol.rollerball.renderer3d.rasterizer;

import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.core.Shader;
import java.util.Arrays;

/**
 *
 * @author leo
 */
public class TriangleRasterizer {

    private Edge e1 = new Edge();
    private Edge e2 = new Edge();
    private Scan scan = new Scan();
    
    private Vertex[] vertices = new Vertex[3];
    
    public TriangleRasterizer() {
    }

    public void draw(Renderer renderer, Vertex v1, Vertex v2, Vertex v3) {
        this.vertices[0] = v1;
        this.vertices[1] = v2;
        this.vertices[2] = v3;
        Arrays.sort(vertices);
        
        Shader shader = renderer.getShader();
        scan.setVars(shader.varsScan, shader.dVarsScan);
        drawTop(renderer);
        drawBottom(renderer);
    }
    
    private void drawTop(Renderer renderer) {
        Shader shader = renderer.getShader();
        e1.set(vertices[0], vertices[1], shader.varsE1, shader.dVarsE1);
        e2.set(vertices[0], vertices[2], shader.varsE2, shader.dVarsE2);
        scan.setTop(e1, e2);
        scan.drawTop(renderer);
    }

    private void drawBottom(Renderer renderer) {
        Shader shader = renderer.getShader();
        e1.set(vertices[2], vertices[1], shader.varsE1, shader.dVarsE1);
        e2.set(vertices[2], vertices[0], shader.varsE2, shader.dVarsE2);
        scan.setBottom(e1, e2);
        scan.drawBottom(renderer);
    }
    
}
