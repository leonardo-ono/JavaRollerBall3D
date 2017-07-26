package br.ol.rollerball.renderer3d.rasterizer;

/**
 *
 * @author leonardo
 */
public class Edge {

    public Vertex a;
    public Vertex b;

    public double x; // current x
    public double dx; // variacao x a cada y
    
    public double[] dVars; // variacao vars a cada y
    public double[] vars; // current vars
    
    public Edge() {
    }

    public void set(Vertex a, Vertex b, double[] vars, double[] dVars) {
        this.dVars = dVars;
        this.vars = vars;
        this.a = a;
        this.b = b;
        this.x = a.p.x;
        double den = ((int) b.p.y - (int) a.p.y);
        double dyInv = (den == 0) ? 0 : (1.0 / den);
        dx = (b.p.x - a.p.x) * dyInv;
        // interpolatable values
        for (int k=0; k<a.vars.length; k++) {
            vars[k] = a.vars[k];
            dVars[k] = dyInv * (b.vars[k] - a.vars[k]);
        }
    }
    
    public void nextYTop() {
        x += dx;
        for (int k=0; k<a.vars.length; k++) {
            vars[k] += dVars[k];
        }
    }

    public void nextYBottom() {
        x -= dx;
        for (int k=0; k<a.vars.length; k++) {
            vars[k] -= dVars[k];
        }
    }
    
}
