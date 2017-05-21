/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

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
public class spawn_location_enemy extends JButton{
    int my_x_location;
    int my_y_location;
    int my_width=60;
    int my_height=60;
    int my_order;
    String my_name;
    boolean isselected=false;
    spawn_location_enemy this_spawn=this;
    Map_constructor my_parent;
    spawn_location_enemy(Map_constructor parent,int order,int x_location,int y_location,String name)
    {
        my_parent=parent;
        my_name=name;
        my_order=order;
        System.out.println(my_order);
        my_x_location=x_location;
        my_y_location=y_location;
        this.setBackground(new Color(0,0,200));
        this.setBounds(my_x_location, my_parent.my_height-my_y_location-my_height, my_width, my_height);
        this.setText(name);
        
        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    //handle double click event.
                    my_parent.my_parent.iseditting=true;
                    my_parent.my_parent.editting_order=my_order;
                    my_parent.my_parent.jTextField11.setText(String.valueOf(my_x_location));
                    my_parent.my_parent.jTextField12.setText(String.valueOf(my_y_location));
                    my_parent.my_parent.jDialog4.setBounds(0, 0, 190, 200);
                    my_parent.my_parent.jDialog4.setVisible(true);
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
                    my_parent.resizing_spawn_enemy(my_order);
                }
                if(e.getKeyCode()==KeyEvent.VK_A && isselected==true)
                {
                    //System.out.println("left");
                    my_x_location=my_x_location-1;
                    this_spawn.setBounds(my_x_location, my_parent.my_height-my_y_location-my_height, my_width, my_height);
                    this_spawn.repaint();
                    my_parent.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_D && isselected==true)
                {
                    my_x_location=my_x_location+1;
                    this_spawn.setBounds(my_x_location, my_parent.my_height-my_y_location-my_height, my_width, my_height);
                    this_spawn.repaint();
                    my_parent.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_S && isselected==true)
                {
                    my_y_location=my_y_location-1;
                    this_spawn.setBounds(my_x_location, my_parent.my_height-my_y_location-my_height, my_width, my_height);
                    this_spawn.repaint();
                    my_parent.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_W && isselected==true)
                {
                    my_y_location=my_y_location+1;
                    this_spawn.setBounds(my_x_location, my_parent.my_height-my_y_location-my_height, my_width, my_height);
                    this_spawn.repaint();
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
            this.setBackground(new Color(200,80,80));
        }else{
            this.setBackground(new Color(220,120,120));
        }
        
    }
    
}
