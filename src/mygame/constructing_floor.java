/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author Amir72c(Amirooo)
 */

public class constructing_floor extends JButton{
    
    Vector3f [] vertices;
    Vector2f[] texCoord;
    int [] indexes;
    String my_texture;
    
    int my_start_x;
    int my_start_y;
    int my_width;
    int my_height;
    int my_order;
    Map_constructor my_parent;
    float my_tile_x;
    float my_tile_y;
    boolean isselected;
    constructing_floor this_cnstructiong_floor=this;
    
    constructing_floor(Map_constructor parent,int order,int start_x,int start_y,int width,int height,String texture,float tile_x,float tile_y)
    {
        my_start_x=start_x;
        my_start_y=start_y;
        my_width=width;
        my_height=height;
        my_texture=texture;
        my_tile_x=tile_x;
        my_tile_y=tile_y;
        my_order=order;
        my_parent=parent;
        
        this.setBackground(new Color(100,100,100));
        this.setBounds(my_start_x, my_parent.my_height-my_start_y-my_height, my_width, my_height);
        this.setText(my_texture);
        vertices=new Vector3f[1*4];
        texCoord = new Vector2f[1*8];
        indexes=new int[1*6];
        int m=0;

                    vertices[4*m] = new Vector3f(my_start_x,my_start_y,0);
                    vertices[4*m+1] = new Vector3f(my_start_x+my_width,my_start_y,0);
                    vertices[4*m+2] = new Vector3f(my_start_x,my_start_y+my_height,0);
                    vertices[4*m+3] = new Vector3f(my_start_x+my_width,my_start_y+my_height,0);
                    
                    texCoord[4*m] = new Vector2f(0,0);
                    texCoord[4*m+1] = new Vector2f((float)my_width/(float)100+my_tile_x,0);
                    texCoord[4*m+2] = new Vector2f(0,(float)my_width/(float)100+my_tile_y);
                    texCoord[4*m+3] = new Vector2f((float)my_width/(float)100+my_tile_x,(float)my_height/(float)100+my_tile_y);
                    
                    indexes[6*m]=4*m+1;
                    indexes[6*m+1]=4*m+2;
                    indexes[6*m+2]=4*m;
                    indexes[6*m+3]=4*m+1;
                    indexes[6*m+4]=4*m+3;
                    indexes[6*m+5]=4*m+2;
            this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    //handle double click event.
                    my_parent.my_parent.iseditting=true;
                    my_parent.my_parent.editting_order=my_order;
                    my_parent.my_parent.jTextField3.setText(String.valueOf(my_start_x));
                    my_parent.my_parent.jTextField4.setText(String.valueOf(my_start_y));
                    my_parent.my_parent.jTextField5.setText(String.valueOf(my_width));
                    my_parent.my_parent.jTextField6.setText(String.valueOf(my_height));
                    my_parent.my_parent.jTextField7.setText(String.valueOf(my_tile_x));
                    my_parent.my_parent.jTextField8.setText(String.valueOf(my_tile_y));
                    my_parent.my_parent.jDialog2.setBounds(0, 0, 290, 390);
                    my_parent.my_parent.jDialog2.setVisible(true);
                }
            }

            public void mousePressed(MouseEvent e) {
                
            }

            public void mouseReleased(MouseEvent e) {
                
            }

            public void mouseEntered(MouseEvent e) {
                
            }

            public void mouseExited(MouseEvent e) {
                
            }
            });
            
            this.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                isselected=true;
            }

            public void focusLost(FocusEvent e) {
                isselected=false;
            }
        });
            
            this.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                
            }
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar()==KeyEvent.VK_DELETE && isselected==true)
                {
                    my_parent.resizing_floors(my_order);
                }
                if(e.getKeyCode()==KeyEvent.VK_A && isselected==true)
                {
                    //System.out.println("left");
                    my_start_x=my_start_x-1;
                    this_cnstructiong_floor.setBounds(my_start_x, my_parent.my_height-my_start_y-my_height, my_width, my_height);
                    this_cnstructiong_floor.repaint();
                    my_parent.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_D && isselected==true)
                {
                    my_start_x=my_start_x+1;
                    this_cnstructiong_floor.setBounds(my_start_x, my_parent.my_height-my_start_y-my_height, my_width, my_height);
                    this_cnstructiong_floor.repaint();
                    my_parent.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_S && isselected==true)
                {
                    my_start_y=my_start_y-1;
                    this_cnstructiong_floor.setBounds(my_start_x, my_parent.my_height-my_start_y-my_height, my_width, my_height);
                    this_cnstructiong_floor.repaint();
                    my_parent.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_W && isselected==true)
                {
                    my_start_y=my_start_y+1;
                    this_cnstructiong_floor.setBounds(my_start_x, my_parent.my_height-my_start_y-my_height, my_width, my_height);
                    this_cnstructiong_floor.repaint();
                    my_parent.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_0 && isselected==true)
                {
                    my_height=my_height+1;
                    this_cnstructiong_floor.setBounds(my_start_x, my_parent.my_height-my_start_y-my_height, my_width, my_height);
                    this_cnstructiong_floor.repaint();
                    my_parent.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_9 && isselected==true)
                {
                    my_height=my_height-1;
                    this_cnstructiong_floor.setBounds(my_start_x, my_parent.my_height-my_start_y-my_height, my_width, my_height);
                    this_cnstructiong_floor.repaint();
                    my_parent.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_EQUALS && isselected==true)
                {
                    my_width=my_width+1;
                    this_cnstructiong_floor.setBounds(my_start_x, my_parent.my_height-my_start_y-my_height, my_width, my_height);
                    this_cnstructiong_floor.repaint();
                    my_parent.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_MINUS && isselected==true)
                {
                    my_width=my_width-1;
                    this_cnstructiong_floor.setBounds(my_start_x, my_parent.my_height-my_start_y-my_height, my_width, my_height);
                    this_cnstructiong_floor.repaint();
                    my_parent.repaint();
                }
            }
            public void keyReleased(KeyEvent e) {
                
            }
        });
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(isselected==false)
        {
            this.setBackground(new Color(100,100,100));
        }else{
            this.setBackground(new Color(200,200,200));
        }
        
    }
    
}
