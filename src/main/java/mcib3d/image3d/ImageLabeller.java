package mcib3d.image3d;

import mcib3d.geom.Object3DVoxels;
import mcib3d.geom.Voxel3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * *
 * /**
 * Copyright (C) 2012 Jean Ollion
 * <p>
 * <p>
 * <p>
 * This file is part of tango
 * <p>
 * tango is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Jean Ollion
 */
public class ImageLabeller {

    protected HashMap<Integer, Spot3D> listSpots = null;
    int[][] labels;
    boolean debug = false;
    int minSize = 0;
    int maxsize = Integer.MAX_VALUE;
    // current mask
    protected ImageHandler currentMask = null;

    public ImageLabeller(boolean debug) {
        this.debug = debug;
    }

    public ImageLabeller() {
    }

    public ImageLabeller(int min, int max) {
        minSize = min;
        maxsize = max;
    }

    public long getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public long getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(int maxsize) {
        this.maxsize = maxsize;
    }

    private void labelSpots6(ImageHandler mask) {
        currentMask = mask;
        //label objects
        labels = new int[mask.sizeZ][mask.sizeXY];
        int sizeX = mask.sizeX;
        listSpots = new HashMap<Integer, Spot3D>();
        int currentLabel = (short) 1;
        Spot3D currentSpot;
        Vox3D v;
        int nextLabel;
        int xy;
        if (debug) {
            System.out.println("Labelling...");
        }
        for (int z = 0; z < mask.sizeZ; z++) {
            for (int y = 0; y < mask.sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    xy = x + y * sizeX;
                    if (mask.getPixel(xy, z) != 0) {
                        currentSpot = null;
                        v = new Vox3D(xy, z);
                        /*if (x<limX) {
                         nextLabel = labels[z][xy+1];
                         if (nextLabel!=0) {
                         if (currentSpot==null) {
                         currentSpot = listSpots.get(nextLabel);
                         currentSpot.addVox(v);
                         } else if (nextLabel!=currentSpot.label) {
                         currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                         currentSpot.addVox(v);
                         }
                         }
                         }
                         * 
                         */
                        if (x > 0) {
                            nextLabel = labels[z][xy - 1];
                            if (nextLabel != 0) {
                                if (currentSpot == null) {
                                    currentSpot = listSpots.get(nextLabel);
                                    currentSpot.addVox(v);
                                } else if (nextLabel != currentSpot.label) {
                                    currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                                    currentSpot.addVox(v);
                                }
                            }
                        }
                        /*
                         if (y<limY) {
                         nextLabel = labels[z][xy+sizeX];
                         if (nextLabel!=0) {
                         if (currentSpot==null) {
                         currentSpot = listSpots.get(nextLabel);
                         currentSpot.addVox(v);
                         } else if (nextLabel!=currentSpot.label) {
                         currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                         currentSpot.addVox(v);
                         }
                         }
                         }
                         * 
                         */
                        if (y > 0) {
                            nextLabel = labels[z][xy - sizeX];
                            if (nextLabel != 0) {
                                if (currentSpot == null) {
                                    currentSpot = listSpots.get(nextLabel);
                                    currentSpot.addVox(v);
                                } else if (nextLabel != currentSpot.label) {
                                    currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                                    currentSpot.addVox(v);
                                }
                            }
                        }

                        /*
                         if (z<limZ) {
                         nextLabel = labels[z+1][xy];
                         if (nextLabel!=0) {
                         if (currentSpot==null) {
                         currentSpot = listSpots.get(nextLabel);
                         currentSpot.addVox(v);
                         } else if (nextLabel!=currentSpot.label) {
                         currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                         currentSpot.addVox(v);
                         }
                         }
                         }
                         * 
                         */
                        if (z > 0) {
                            nextLabel = labels[z - 1][xy];
                            if (nextLabel != 0) {
                                if (currentSpot == null) {
                                    currentSpot = listSpots.get(nextLabel);
                                    currentSpot.addVox(v);
                                } else if (nextLabel != currentSpot.label) {
                                    currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                                    currentSpot.addVox(v);
                                }
                            }
                        }

                        if (currentSpot == null) {
                            listSpots.put(currentLabel, new Spot3D(currentLabel++, v));
                        }
                    }
                }
            }
        }
    }


    private void labelSpots26(ImageHandler mask) {
        currentMask = mask;
        //label objects
        labels = new int[mask.sizeZ][mask.sizeXY];
        int sizeX = mask.sizeX;
        listSpots = new HashMap<>();
        int currentLabel = 1;
        Spot3D currentSpot;
        Vox3D v;
        int nextLabel;
        int xy;
        if (debug) {
            System.out.println("Labelling...");
        }
        for (int z = 0; z < mask.sizeZ; z++) {
            for (int y = 0; y < mask.sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    xy = x + y * sizeX;
                    if (mask.getPixel(xy, z) != 0) {
                        currentSpot = null;
                        v = new Vox3D(xy, z);
                        for (int k = -1; k <= 1; k++) {
                            for (int j = -1; j <= 1; j++) {
                                for (int i = -1; i <= 1; i++) {
                                    if ((mask.contains(x + i, y + j, z + k)) && (i * i + j * j + k * k != 0) && ((i < 0) || (j < 0) || (k < 0))) {
                                        nextLabel = labels[z + k][xy + i + j * sizeX];
                                        if (nextLabel != 0) {
                                            if (currentSpot == null) {
                                                currentSpot = listSpots.get(nextLabel);
                                                currentSpot.addVox(v);
                                            } else if (nextLabel != currentSpot.label) {
                                                currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                                                currentSpot.addVox(v);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (currentSpot == null) {
                            listSpots.put(currentLabel, new Spot3D(currentLabel++, v));
                        }
                    }
                }
            }
        }
    }

    // NOT USED, IN TEST; avoid fusion when one object is too big
    private void labelSpotsCheckSize(ImageHandler mask) {
        currentMask = mask;
        //label objects
        labels = new int[mask.sizeZ][mask.sizeXY];
//        int limX = mask.sizeX - 1;
//        int limY = mask.sizeY - 1;
//        int limZ = mask.sizeZ - 1;
        int sizeX = mask.sizeX;
        listSpots = new HashMap<Integer, Spot3D>();
        int currentLabel = (short) 1;
        Spot3D currentSpot;
        Vox3D v;
        int nextLabel;
        int xy;
        if (debug) {
            System.out.println("Labelling...");
        }
        for (int z = 0; z < mask.sizeZ; z++) {
            for (int y = 0; y < mask.sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    xy = x + y * sizeX;
                    if (mask.getPixel(xy, z) != 0) {
                        currentSpot = null;
                        v = new Vox3D(xy, z);
                        /*if (x<limX) {
                         nextLabel = labels[z][xy+1];
                         if (nextLabel!=0) {
                         if (currentSpot==null) {
                         currentSpot = listSpots.get(nextLabel);
                         currentSpot.addVox(v);
                         } else if (nextLabel!=currentSpot.label) {
                         currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                         currentSpot.addVox(v);
                         }
                         }
                         }
                         * 
                         */
                        if (x > 0) {
                            nextLabel = labels[z][xy - 1];
                            if (nextLabel != 0) {
                                if (currentSpot == null) {
                                    currentSpot = listSpots.get(nextLabel);
                                    if (!currentSpot.tooBig) {
                                        currentSpot.addVox(v);
                                        if (currentSpot.getSize() > maxsize) {
                                            currentSpot.tooBig = true;
                                        }
                                    }
                                } else if (nextLabel != currentSpot.label) {
                                    if (!currentSpot.tooBig) {
                                        if (!listSpots.get(nextLabel).tooBig) {
                                            currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                                            currentSpot.addVox(v);
                                            if (currentSpot.getSize() > maxsize) {
                                                currentSpot.tooBig = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        /*
                         if (y<limY) {
                         nextLabel = labels[z][xy+sizeX];
                         if (nextLabel!=0) {
                         if (currentSpot==null) {
                         currentSpot = listSpots.get(nextLabel);
                         currentSpot.addVox(v);
                         } else if (nextLabel!=currentSpot.label) {
                         currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                         currentSpot.addVox(v);
                         }
                         }
                         }
                         * 
                         */
                        if (y > 0) {
                            nextLabel = labels[z][xy - sizeX];
                            if (nextLabel != 0) {
                                if (currentSpot == null) {
                                    currentSpot = listSpots.get(nextLabel);
                                    if (!currentSpot.tooBig) {
                                        currentSpot.addVox(v);
                                        if (currentSpot.getSize() > maxsize) {
                                            currentSpot.tooBig = true;
                                        }
                                    }
                                } else if (nextLabel != currentSpot.label) {
                                    if (!currentSpot.tooBig) {
                                        if (!listSpots.get(nextLabel).tooBig) {
                                            currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                                            currentSpot.addVox(v);
                                            if (currentSpot.getSize() > maxsize) {
                                                currentSpot.tooBig = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        /*
                         if (z<limZ) {
                         nextLabel = labels[z+1][xy];
                         if (nextLabel!=0) {
                         if (currentSpot==null) {
                         currentSpot = listSpots.get(nextLabel);
                         currentSpot.addVox(v);
                         } else if (nextLabel!=currentSpot.label) {
                         currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                         currentSpot.addVox(v);
                         }
                         }
                         }
                         * 
                         */
                        if (z > 0) {
                            nextLabel = labels[z - 1][xy];
                            if (nextLabel != 0) {
                                if (currentSpot == null) {
                                    currentSpot = listSpots.get(nextLabel);
                                    if (!currentSpot.tooBig) {
                                        currentSpot.addVox(v);
                                        if (currentSpot.getSize() > maxsize) {
                                            currentSpot.tooBig = true;
                                        }
                                    }
                                } else if (nextLabel != currentSpot.label) {
                                    if (!currentSpot.tooBig) {
                                        if (!listSpots.get(nextLabel).tooBig) {
                                            currentSpot = currentSpot.fusion(listSpots.get(nextLabel));
                                            currentSpot.addVox(v);
                                            if (currentSpot.getSize() > maxsize) {
                                                currentSpot.tooBig = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (currentSpot == null) {
                            listSpots.put(currentLabel, new Spot3D(currentLabel++, v));
                        }
                    }
                }
            }
        }
    }

    private void labelIndividualVoxel(ImageHandler mask) {
        currentMask = mask;
        labels = new int[mask.sizeZ][mask.sizeXY];
        int sizeX = mask.sizeX;
        listSpots = new HashMap<Integer, Spot3D>();
        int currentLabel = 1;
        Vox3D v;
        int xy;
        if (debug) {
            System.out.println("Labelling...");
        }
        for (int z = 0; z < mask.sizeZ; z++) {
            for (int y = 0; y < mask.sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    xy = x + y * sizeX;
                    if (mask.getPixel(xy, z) != 0) {
                        v = new Vox3D(xy, z);
                        listSpots.put(currentLabel, new Spot3D(currentLabel++, v));
                    }
                }
            }
        }
    }

    public ImageInt getLabels(ImageHandler mask, boolean connex6) {
        if ((listSpots == null) || (mask != currentMask)) {
            if (connex6) {
                this.labelSpots6(mask);
            } else {
                this.labelSpots26(mask);
            }
        }
        ImageShort res = new ImageShort(mask.getTitle() + "::segmented", mask.sizeX, mask.sizeY, mask.sizeZ);
        res.setScale(mask);
        short label = 1;
        for (Spot3D s : listSpots.values()) {
            LinkedList<Vox3D> a = s.voxels;
            // check size
            if ((a.size() >= minSize) && (a.size() <= maxsize)) {
                for (Vox3D vox : a) {
                    res.pixels[vox.z][vox.xy] = label;
                }
                label++;
            }
        }

        return res;
    }

    public ImageFloat getLabelsFloat(ImageHandler mask) {
        return getLabelsFloat(mask, false);
    }

    public ImageFloat getLabelsFloat(ImageHandler mask, boolean connex6) {
        if ((listSpots == null) || (mask != currentMask)) {
            if (connex6) {
                this.labelSpots6(mask);
            } else {
                this.labelSpots26(mask);
            }
        }
        ImageFloat res = new ImageFloat(mask.getTitle() + "::segmented", mask.sizeX, mask.sizeY, mask.sizeZ);
        int label = 1;
        for (Spot3D s : listSpots.values()) {
            LinkedList<Vox3D> a = s.voxels;
            // check size
            if ((a.size() >= minSize) && (a.size() <= maxsize)) {
                for (Vox3D vox : a) {
                    res.pixels[vox.z][vox.xy] = label;
                }
                label++;
            }
        }
        return res;
    }

    // classical default neighborhood for segmentation is 26
    public ImageInt getLabels(ImageHandler mask) {
        return getLabels(mask, false);
    }

    public int getNbObjectsTotal(ImageHandler mask, boolean connex6) {
        if ((listSpots == null) || (mask != currentMask)) {
            if (connex6) {
                this.labelSpots6(mask);
            } else {
                this.labelSpots26(mask);
            }
        }

        return listSpots.size();
    }

    public ImageInt getLabelsIndividualVoxels(ImageHandler mask) {
        if ((listSpots == null) || (mask != currentMask)) {
            labelIndividualVoxel(mask);
        }
        ImageShort res = new ImageShort(mask.getTitle() + "::segmented", mask.sizeX, mask.sizeY, mask.sizeZ);
        short label = 1;
        for (Spot3D s : listSpots.values()) {
            Vox3D vox = s.voxels.get(0);
            res.pixels[vox.z][vox.xy] = label;
            label++;
        }
        return res;
    }

    public ImageFloat getLabelsIndividualVoxelsFloat(ImageHandler mask) {
        if ((listSpots == null) || (mask != currentMask)) {
            labelIndividualVoxel(mask);
        }
        ImageFloat res = new ImageFloat(mask.getTitle() + "::segmented", mask.sizeX, mask.sizeY, mask.sizeZ);
        short label = 1;
        for (Spot3D s : listSpots.values()) {
            Vox3D vox = s.voxels.get(0);
            res.pixels[vox.z][vox.xy] = label;
            label++;
        }
        return res;
    }

    // classical default neighborhood for segmentation is 26
    public int getNbObjectsTotal(ImageHandler mask) {
        return getNbObjectsTotal(mask, false);
    }

    public int getNbObjectsinSizeRange(ImageHandler mask, boolean connex6) {
        // check if labelling needed
        if ((listSpots == null) || (mask != currentMask)) {
            if (connex6) {
                this.labelSpots6(mask);
            } else {
                this.labelSpots26(mask);
            }
        }
        // count nb objects
        int nbObj = 0;
        int sizeX = mask.sizeX;
        short label = 1;
        for (Spot3D s : listSpots.values()) {
            LinkedList<Vox3D> a = s.voxels;
            // check size
            if ((a.size() >= minSize) && (a.size() <= maxsize)) {
                nbObj++;
            }
        }

        return nbObj;
    }

    // classical default neighborhood for segmentation is 26
    public int getNbObjectsinSizeRange(ImageHandler mask) {
        return getNbObjectsinSizeRange(mask, false);
    }

    // classical default neighborhood for segmentation is 26
    public ArrayList<Object3DVoxels> getObjects(ImageHandler mask, boolean connex6) {
        if ((listSpots == null) || (mask != currentMask)) {
            if (connex6) {
                this.labelSpots6(mask);
            } else {
                this.labelSpots26(mask);
            }
        }
        ArrayList<Object3DVoxels> objects = new ArrayList<Object3DVoxels>();
        int sizeX = mask.sizeX;
        short label = 1;
        for (Spot3D s : listSpots.values()) {
            LinkedList<Vox3D> a = s.voxels;
            // check size
            if ((a.size() >= minSize) && (a.size() <= maxsize)) {
                LinkedList<Voxel3D> voxels3D = new LinkedList();
                for (Vox3D vox : a) {
                    voxels3D.add(new Voxel3D(vox.xy % sizeX, vox.xy / sizeX, vox.z, label));
                }
                LinkedList noDuplicate = new LinkedList(new HashSet(voxels3D));
                // set calibration
                Object3DVoxels object3DVoxels = new Object3DVoxels(noDuplicate);
                object3DVoxels.setCalibration(mask.getScaleXY(), mask.getScaleZ(), mask.getUnit());
                objects.add(object3DVoxels);
                label++;
            }
        }
        return objects;
    }


    // classical default neighborhood for segmentation is 26
    public ArrayList<Object3DVoxels> getObjects(ImageHandler mask) {
        return getObjects(mask, false);
    }


    protected class Spot3D {
        LinkedList<Vox3D> voxels;
        int label;
        boolean tooBig = false;

        public Spot3D(int label, Vox3D v) {
            this.label = label;
            this.voxels = new LinkedList();
            voxels.add(v);
            v.setLabel(label);
        }

        public void addVox(Vox3D vox) {
            voxels.add(vox);
            vox.setLabel(label);
        }

        public void setLabel(int label) {
            this.label = label;
            for (Vox3D v : voxels) {
                v.setLabel(label);
            }
        }

        public Spot3D fusion(Spot3D other) {
            if (other.label < label) {
                return other.fusion(this);
            }

            listSpots.remove(other.label);
            // FIXME pb if size >= integer max size
            voxels.addAll(other.voxels);

            other.setLabel(label);

            return this;
        }

        public long getSize() {
            return voxels.size();
        }
    }

    protected class Vox3D {

        public int xy, z;

        public Vox3D(int xy, int z) {
            this.xy = xy;
            this.z = z;
        }

        public void setLabel(int label) {
            labels[z][xy] = label;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Vox3D) {
                return xy == ((Vox3D) o).xy && z == ((Vox3D) o).z;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 47 * hash + this.xy;
            hash = 47 * hash + this.z;
            return hash;
        }
    }
}
