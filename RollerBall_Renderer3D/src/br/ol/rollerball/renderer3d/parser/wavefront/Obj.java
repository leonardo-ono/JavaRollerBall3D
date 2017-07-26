package br.ol.rollerball.renderer3d.parser.wavefront;

import br.ol.rollerball.renderer3d.core.Material;
import br.ol.rollerball.renderer3d.parser.wavefront.WavefrontParser.Face;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leonardo
 */
public class Obj {
    
    public List<Face> faces = new ArrayList<Face>();
    public Material material;
    
}
