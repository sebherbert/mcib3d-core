/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcib3d.spatial.descriptors;

import mcib3d.geom.Objects3DPopulation;
import mcib3d.utils.ArrayUtil;

/**
 *
 * @author thomasb
 */
public class G_Border implements SpatialDescriptor {

    public G_Border() {
    }

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public ArrayUtil compute(Objects3DPopulation pop) {
        return pop.distancesAllClosestBorder();
    }

    @Override
    public String getName() {
        return "G_border";
    }

   

}
