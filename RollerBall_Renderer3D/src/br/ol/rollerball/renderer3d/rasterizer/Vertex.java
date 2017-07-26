package br.ol.rollerball.renderer3d.rasterizer;

import br.ol.rollerball.math.Vec2;
import br.ol.rollerball.math.Vec4;

/**
 *
 * @author leo
 */
public class Vertex implements Comparable<Vertex> {

    public Vec4 p = new Vec4();
    public Vec4 normal = new Vec4();
    public Vec2 st = new Vec2();
    
    public double[] extraDatas; // valores nao interpolaveis
    public double[] vars; // valores interpolaveis
    
    public Vertex(Vertex v) {
        this(v.extraDatas.length, v.vars.length);
    }

    public Vertex(int extraDatasSize, int varsSize) {
        extraDatas = new double[extraDatasSize];
        vars = new double[varsSize];
    }

    // 1=x, 2=y, 3=z near, 4=zfar
    public double getValueByPlane(int plane) {
        switch (plane) {
            case 1: return p.x; // x plane
            case 2: return p.y; // y plane
            case 3:             // z near
            case 4: return p.z; // z far
            default: return 0;
        }
    }

    public void setLerp(Vertex a, Vertex b, double porc) {
        p.setLerp(a.p, b.p, porc);
        normal.setLerp(a.normal, b.normal, porc);
        st.setLerp(a.st, b.st, porc);
        for (int i=0; i<extraDatas.length; i++) {
            extraDatas[i] = a.extraDatas[i] + porc * (b.extraDatas[i] - a.extraDatas[i]);
        }
    }
    
    public void multiply(double s) {
        p.multiply(s);
        normal.multiply(s);
        st.multiply(s);
    }
    
    @Override
    public int compareTo(Vertex t) {
        return (int) (100000 * (p.y - t.p.y));
    }
    
}
