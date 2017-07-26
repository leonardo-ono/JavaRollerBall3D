package br.ol.rollerball.renderer3d.core;

import br.ol.rollerball.math.Mat4;
import br.ol.rollerball.math.Vec4;
import br.ol.rollerball.renderer3d.buffer.ColorBuffer;
import br.ol.rollerball.renderer3d.buffer.DepthBuffer;
import br.ol.rollerball.renderer3d.rasterizer.TriangleRasterizer;
import br.ol.rollerball.renderer3d.rasterizer.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leonardo
 */
public class Renderer {
    
    private ColorBuffer colorBuffer;
    
    private DepthBuffer depthBuffer;
    private boolean depthBufferEnabled = true;
    
    private Shader shader;
    
    private boolean backfaceCullingEnabled = true;
    
    private int vertexIndex = 0;
    private TriangleRasterizer triangleRasterizer = new TriangleRasterizer();
    
    private List<Image> textures = new ArrayList<Image>();
    private Material material;

    public static enum MatrixMode { MODEL, VIEW, PROJECTION };
    private Mat4 mvp = new Mat4();
    private Transform modelTransform = new Transform();
    private Transform viewTranform = new Transform();
    private Transform projectionTranform = new Transform();
    private Transform currentTransform;
    private MatrixMode matrixMode;
    
    private List<Light> lights = new ArrayList<Light>();
    
    // frustrum clipping
    private ViewFrustrumClipper polygonClipper = new ViewFrustrumClipper();
    private double clipX;
    private double clipY;
    private double clipZfar = -Double.MAX_VALUE;
    private double clipZNear = -1.0;
    
    public Renderer(int width, int height) {
        colorBuffer = new ColorBuffer(width, height);
        depthBuffer = new DepthBuffer(width, height);
        
        currentTransform = projectionTranform;
        matrixMode = MatrixMode.PROJECTION;
        
        clipX = width / 2 - 0.5;
        clipY = height / 2 - 0.5;
    }

    // color buffer
    
    public ColorBuffer getColorBuffer() {
        return colorBuffer;
    }

    public void setColorBuffer(ColorBuffer colorBuffer) {
        this.colorBuffer = colorBuffer;
    }

    // depth buffer

    public DepthBuffer getDepthBuffer() {
        return depthBuffer;
    }

    public void setDepthBuffer(DepthBuffer depthBuffer) {
        this.depthBuffer = depthBuffer;
    }

    public boolean isDepthBufferEnabled() {
        return depthBufferEnabled;
    }

    public void setDepthBufferEnabled(boolean depthBufferEnabled) {
        this.depthBufferEnabled = depthBufferEnabled;
    }
    
    // backface culling

    public boolean isBackfaceCullingEnabled() {
        return backfaceCullingEnabled;
    }

    public void setBackfaceCullingEnabled(boolean backfaceCullingEnabled) {
        this.backfaceCullingEnabled = backfaceCullingEnabled;
    }

    // use after perspective division
    public boolean isBackFaced(Vertex v1, Vertex v2, Vertex v3) {
        double x1 = v2.p.x - v1.p.x;
        double y1 = v2.p.y - v1.p.y;
        double x2 = v3.p.x - v1.p.x;
        double y2 = v3.p.y - v1.p.y;
        double z = x1 * y2 - x2 * y1;
        return z < 0;
    }
    
    // frustrum clipping

    public double getClipX() {
        return clipX;
    }

    public void setClipX(double clipX) {
        this.clipX = clipX;
    }

    public double getClipY() {
        return clipY;
    }

    public void setClipY(double clipY) {
        this.clipY = clipY;
    }

    public double getClipZfar() {
        return clipZfar;
    }

    public void setClipZfar(double clipZfar) {
        this.clipZfar = clipZfar;
    }

    public double getClipZNear() {
        return clipZNear;
    }

    public void setClipZNear(double clipZNear) {
        this.clipZNear = clipZNear;
    }
    
    // shader
    
    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    // Material
    
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
    
    // Textures

    public List<Image> getTextures() {
        return textures;
    }

    public void addTexture(Image texture) {
        textures.add(texture);
    }
    
    // Light
    
    public List<Light> getLights() {
        return lights;
    }

    public void addLight(Light light) {
        lights.add(light);
    }
    
    // Transformation / Matrix
    
    public Transform getModelTransform() {
        return modelTransform;
    }

    public void setModelTransform(Transform modelTransform) {
        this.modelTransform = modelTransform;
    }

    public Transform getViewTranform() {
        return viewTranform;
    }

    public void setViewTranform(Transform viewTranform) {
        this.viewTranform = viewTranform;
    }

    public Transform getProjectionTranform() {
        return projectionTranform;
    }

    public void setProjectionTranform(Transform projectionTranform) {
        this.projectionTranform = projectionTranform;
    }
    
    public Mat4 getMvp() {
        return mvp;
    }
    
    public MatrixMode getMatrixMode() {
        return matrixMode;
    }

    public void setMatrixMode(MatrixMode matrixMode) {
        this.matrixMode = matrixMode;
        switch (matrixMode) {
            case MODEL: currentTransform = modelTransform; break;
            case VIEW: currentTransform = viewTranform; break;
            case PROJECTION: currentTransform = projectionTranform; break;
        }
    }

    public void pushMatrix() {
        currentTransform.push();
    }
    
    public void popMatrix() {
        currentTransform.pop();
    }
    
    public Transform getCurrentTranform() {
        return currentTransform;
    }

    public void setIdentity() {
        currentTransform.setIdentity();
    }
    
    public void setPerspectiveProjection(double fov) {
        currentTransform.setPerspectiveProjection(fov, colorBuffer.getWidth());
    }
    
    public void scale(double sx, double sy, double sz) {
        currentTransform.scale(sx, sy, sz);
    }
    
    public void translate(double dx, double dy, double dz) {
        currentTransform.translate(dx, dy, dz);
    }

    public void rotateX(double angle) {
        currentTransform.rotateX(angle);
    }
    
    public void rotateY(double angle) {
        currentTransform.rotateY(angle);
    }
    
    public void rotateZ(double angle) {
        currentTransform.rotateZ(angle);
    }
    
    // rendering
    
    public void clearAllBuffers() {
        depthBuffer.clear();
        colorBuffer.clear();
    }

    public void begin() {
        vertexIndex = 0;
        mvp.set(projectionTranform.getMatrix());
        mvp.multiply(viewTranform.getMatrix());
        mvp.multiply(modelTransform.getMatrix());
    }

    public void setUniforms(double[] uniforms) {
        System.arraycopy(uniforms, 0, shader.uniforms, 0, uniforms.length);
    }

    public void setVertexExtraDatas(double[] vertexExtraDatas) {
        System.arraycopy(vertexExtraDatas, 0, shader.vertices[vertexIndex].extraDatas, 0, vertexExtraDatas.length);
    }

    public void setTextureCoordinates(double s, double t) {
        shader.vertices[vertexIndex].st.set(s, t);
    }

    public void setNormal(double x, double y, double z) {
        shader.vertices[vertexIndex].normal.set(x, y, z, 0);
    }
    
    public void setVertex(double x, double y, double z) {
        Vec4 p = shader.vertices[vertexIndex].p;
        p.set(x, y, z, 1);
        vertexIndex++;
        if (vertexIndex % 3 == 0) {
            render();
            vertexIndex = 0;
        }
    }

    private void render() {
        for (int i=0; i<shader.vertices.length; i++) {
            doVertexMVPTransformation(shader.vertices[i]);
        }
        
        // View Frustrum Culling Test
        Vertex v1 = shader.vertices[0];
        Vertex v2 = shader.vertices[1];
        Vertex v3 = shader.vertices[2];
        
        if (v1.p.z > -10 && v2.p.z > -10 && v3.p.z > -10) {
            return;
        }
        
        List<Vertex> clippedVertices = polygonClipper.clip(shader, shader.vertices, clipX, clipY, clipZNear, clipZfar);
        if (clippedVertices.isEmpty()) {
            return;
        }

        for (Vertex v : clippedVertices) {
            shader.processVertex(this, v);
            v.p.doPerspectiveDivision();
        }
        
        drawPolygon(clippedVertices);
    }
    
    private void drawPolygon(List<Vertex> vertices) {
        Vertex va = vertices.get(0);
        for (int i=1; i<=vertices.size() - 2; i++) {
            Vertex vb = vertices.get(i);
            Vertex vc = vertices.get((i + 1) % vertices.size());
            
            if (backfaceCullingEnabled) {
                if (!isBackFaced(va, vb, vc)) {
                    shader.preDraw(this, va, vb, vc);
                    triangleRasterizer.draw(this, va, vb, vc);
                    shader.postDraw(this, va, vb, vc);
                }
            }
            else {
                shader.preDraw(this, va, vb, vc);
                triangleRasterizer.draw(this, va, vb, vc);
                shader.postDraw(this, va, vb, vc);
            }
        }
    }
    
    
    public void end() {
        // unnecessary ?
    }
    
    // utils inside shaders
    
    public void doVertexMVPTransformation(Vertex vertex) {
        mvp.multiply(vertex.p);
        mvp.multiply(vertex.normal);
    }

    public void setPixel(int x, int y, int[] color) {
        colorBuffer.setPixel(x, y, color);
    }
 
    public void setPixel(int x, int y, int[] color, double depth) {
        if (depthBufferEnabled) {
            if (depth < depthBuffer.get(x, y)) {
                depthBuffer.set(x, y, depth);
                setPixel(x, y, color);
            }
        }
        else {
            setPixel(x, y, color);
        }
    }
    
}
