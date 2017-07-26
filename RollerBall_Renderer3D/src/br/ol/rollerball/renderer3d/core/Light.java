package br.ol.rollerball.renderer3d.core;

import br.ol.rollerball.math.Vec4;

/**
 *
 * @author leo
 */
public class Light {
    
    public Vec4 position = new Vec4();
    public Vec4 ambient = new Vec4();
    public Vec4 diffuse = new Vec4();
    public Vec4 specular = new Vec4();
    public boolean enabled = true;

    public Light() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
}
