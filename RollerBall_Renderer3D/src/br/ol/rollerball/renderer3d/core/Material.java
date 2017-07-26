package br.ol.rollerball.renderer3d.core;

import br.ol.rollerball.math.Vec4;

/**
 *
 * @author leonardo
 */
public class Material {
    
    public String name;
    public double ns;
    public Vec4 ka = new Vec4();
    public Vec4 kd = new Vec4();
    public Vec4 ks = new Vec4();
    public double ni;
    public double d;
    public double illum;
    public Image map_kd;
    public Image map_ka;

    public Material(String name) {
        this.name = name;
    }

}
