package br.ol.rollerball.game.infra;

import br.ol.rollerball.math.Vec2;
import br.ol.rollerball.math.Vec4;
import br.ol.rollerball.renderer3d.core.Renderer;
import br.ol.rollerball.renderer3d.core.Transform;
import br.ol.rollerball.renderer3d.parser.wavefront.Obj;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser;
import br.ol.rollerball.renderer3d.shader.GouraudShaderWithTexture;
import java.awt.Graphics2D;
import java.util.List;

/**
 * Entity abstract class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public abstract class Entity<T extends Scene> {
    
    public String name;
    public T scene;
    public boolean visible = false;
    public Transform transform = new Transform();
    public List<Obj> mesh;
    
    public Entity(String name, T scene) {
        this.name = name;
        this.scene = scene;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public void init() throws Exception {
        transform.setIdentity();
    }
    
    public void update(Renderer renderer) {
    }
    
    public void preDraw(Renderer renderer) {
        renderer.setBackfaceCullingEnabled(true);
        renderer.setShader(scene.display.gouraudShader);
        renderer.setMatrixMode(Renderer.MatrixMode.MODEL);
        renderer.setModelTransform(transform);
        GouraudShaderWithTexture.minIntensity = 0.5;
        GouraudShaderWithTexture.maxIntensity = 1.0;
        GouraudShaderWithTexture.scale = 1.0;
    }
    
    public void draw(Renderer renderer) {
        if (!visible || mesh == null) {
            return;
        }
        for (Obj obj : mesh) {
            renderer.setMaterial(obj.material);
            renderer.begin();
            for (WavefrontParser.Face face : obj.faces) {
                for (int f=0; f<3; f++) {
                    Vec4 v = face.vertex[f];
                    Vec4 n = face.normal[f];
                    Vec2 t = face.texture[f];
                    renderer.setTextureCoordinates(t.x, t.y);
                    renderer.setNormal(n.x, n.y, n.z);
                    renderer.setVertex(v.x, v.y, v.z);
                }
            }
            renderer.end();            
        }        
    }

    public void draw(Renderer renderer, Graphics2D g) {
    }
    
}
