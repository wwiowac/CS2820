package production;

import java.awt.*;

/**
 * @author Jacob Guth
 */
public class Packer{

    private Master master;

    public Packer(Master master){
        this.master = master;

        //update cell types for visualizer
        for(int x=1; x<3; x++){
            for(int y=11; y<13; y++){
                master.floor.updateItemAt(new Point(x,y), Cell.Type.PACKER);
            }
        }
    }

}
