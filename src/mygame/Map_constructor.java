/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

/**
 *
 * @author Amir72c(Amirooo)
 */
public class Map_constructor extends JPanel{
    int my_width;
    int my_height;
    Map_making my_parent;
    constructing_floor floors[];
    spawn_location mario_location;
    spawn_location_enemy enemy_location[];
    int num_floor=0;
    int num_enemy=0;
    String my_background_tex;
    int popup_x;
    int popup_y;
    Map_constructor this_map_constructor=this;
    Map_constructor(Map_making parent,int width,int height,String background_tex)
    {
        my_parent=parent;
        
        my_width=width;
        my_height=height;
        my_background_tex=background_tex;
        this.setLayout(null);
        this.setBackground(Color.red);
        this.setBounds(0, 0, width, height);
        floors=new constructing_floor[100];
        enemy_location=new spawn_location_enemy[100];
        this.addMouseListener(new MouseAdapter() {
            
        @Override
        public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

        @Override
    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            my_parent.jPopupMenu1.show(e.getComponent(),
                       e.getX(), e.getY());
            popup_x=e.getX();
            popup_y=my_height-e.getY();
        }
    }
        
        });
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                JComponent component = (JComponent)evt.getSource();
                component.setToolTipText("X="+evt.getX()+"|"+"Y="+String.valueOf(this_map_constructor.my_height-evt.getY()));
                ToolTipManager.sharedInstance().mouseMoved(evt);
            }
        });
    }
    
    public void resizing_floors(int deleting_index){
        this.remove(floors[deleting_index]);
        constructing_floor temp_constructing_floor[]=new constructing_floor[100];
        int m = 0;
        for(int i=0;i<deleting_index;i++)
        {
            temp_constructing_floor[m]=floors[i];
            m=m+1;
        }
        for(int i=deleting_index+1;i<num_floor;i++)
        {
            floors[i].my_order=floors[i].my_order-1;
            temp_constructing_floor[m]=floors[i];
            m=m+1;
        }
        num_floor=num_floor-1;
        floors=temp_constructing_floor;
        this.repaint();
    }
    
    public void resizing_spawn_enemy(int deleting_index){
        this.remove(enemy_location[deleting_index]);
        spawn_location_enemy temp_enemy_location[]=new spawn_location_enemy[100];
        int m = 0;
        for(int i=0;i<deleting_index;i++)
        {
            temp_enemy_location[m]=enemy_location[i];
            m=m+1;
        }
        for(int i=deleting_index+1;i<num_enemy;i++)
        {
            enemy_location[i].my_order=enemy_location[i].my_order-1;
            temp_enemy_location[m]=enemy_location[i];
            m=m+1;
        }
        num_enemy=num_enemy-1;
        enemy_location=temp_enemy_location;
        this.repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
    }
}
