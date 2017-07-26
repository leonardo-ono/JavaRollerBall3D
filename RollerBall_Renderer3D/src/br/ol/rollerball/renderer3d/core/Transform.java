package br.ol.rollerball.renderer3d.core;

import br.ol.rollerball.math.Mat4;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leonardo
 */
public class Transform {

    public int matrixIndex = -1;
    public List<Mat4> matrices = new ArrayList<Mat4>();
    
    public Transform() {
        init();
    }
    
    private void init() {
        push();
    }
    
    public Mat4 getMatrix() {
        return matrices.get(matrixIndex);
    }
    
    public void push() {
        if ((matrixIndex + 1) >= matrices.size()) {
            Mat4 matrix = new Mat4();
            matrix.setIdentity();
            matrices.add(matrix);
        }
        matrixIndex++;
    }
    
    public void pop() {
        matrixIndex--;
        if (matrixIndex < 0) {
            throw new RuntimeException("Matrix stack is empty !");
        }
    }
    
    protected Mat4 tmp = new Mat4();

    public void setIdentity() {
        getMatrix().setIdentity();
    }
    
    public void setPerspectiveProjection(double fov, double screenWidth) {
        getMatrix().setPerspectiveProjection(fov, screenWidth);
    }
    
    public void scale(double sx, double sy, double sz) {
        tmp.setScale(sx, sy, sz);
        getMatrix().multiply(tmp);
    }
    
    public void translate(double dx, double dy, double dz) {
        tmp.setTranslation(dx, dy, dz);
        getMatrix().multiply(tmp);
    }

    public void rotateX(double angle) {
        tmp.setRotationX(angle);
        getMatrix().multiply(tmp);
    }
    
    public void rotateY(double angle) {
        tmp.setRotationY(angle);
        getMatrix().multiply(tmp);
    }
    
    public void rotateZ(double angle) {
        tmp.setRotationZ(angle);
        getMatrix().multiply(tmp);
    }

}
