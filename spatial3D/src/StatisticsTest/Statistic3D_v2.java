package StatisticsTest;


import ij.ImagePlus;
import ij.Prefs;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.plugin.filter.PlugInFilter;
import static ij.plugin.filter.PlugInFilter.DOES_ALL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nhuhoa
 */
public class Statistic3D_v2 implements PlugIn 
{    
    int tx = 128, ty = 128, tz = 128;
    int nbClusters = 2;
    /**
     *
     * @param arg
     */
    @Override
        public void run(String arg) 
        {
            if (!Dialogue()) 
            {
                return;
            }
            Cluster3D s = new Cluster3D(tx, ty, tz);
            s.createCell();
            //s.randomAttractVesicles(nbClusters);
            s.randomElementsCluster(nbClusters);
        }
        
        private boolean Dialogue() {
            GenericDialog gd = new GenericDialog("Generate many clusters");
            gd.addNumericField("Number of clusters", nbClusters, 0);
            gd.addNumericField("tx", tx, 0);
            gd.addNumericField("ty", ty, 0);
            gd.addNumericField("tz", tz, 0);
            gd.showDialog();
            nbClusters = (int) gd.getNextNumber();
            tx = (int) gd.getNextNumber();
            ty = (int) gd.getNextNumber();
            tz = (int) gd.getNextNumber();
            return (!gd.wasCanceled());
    }
}
