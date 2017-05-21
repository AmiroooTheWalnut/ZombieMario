/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;


import java.awt.Point;


/**
 *
 * @author Amir72c(Amirooo)
 */
public class Floor {
    Vector3f [] vertices;
    Vector2f[] texCoord;
    short [] indexes;
    String my_texture;
    Map my_map_parent;
    int my_width;
    int my_height;
    Point my_position;
    Floor(Map parent,Point position,int width,int height,String texture,float tile_x,float tile_y){
        my_map_parent=parent;
        my_texture=texture;
        my_width=width;
        my_height=height;
        my_position=position;
        vertices=new Vector3f[1*4];
        texCoord = new Vector2f[1*8];
        indexes=new short[1*6];
        renew_floor();
        int m=0;

                    vertices[4*m] = new Vector3f(position.x,position.y,0.02f);
                    vertices[4*m+1] = new Vector3f(position.x+width,position.y,0.02f);
                    vertices[4*m+2] = new Vector3f(position.x,position.y+height,0.02f);
                    vertices[4*m+3] = new Vector3f(position.x+width,position.y+height,0.02f);
                    
                    texCoord[4*m] = new Vector2f(0,0);
                    texCoord[4*m+1] = new Vector2f((float)width/(float)100,0);
                    texCoord[4*m+2] = new Vector2f(0,(float)height/(float)100);
                    texCoord[4*m+3] = new Vector2f((float)width/(float)100,(float)height/(float)100);
                    
                    indexes[6*m]=(short)(4*m+1);
                    indexes[6*m+1]=(short)(4*m+2);
                    indexes[6*m+2]=(short)(4*m);
                    indexes[6*m+3]=(short)(4*m+1);
                    indexes[6*m+4]=(short)(4*m+3);
                    indexes[6*m+5]=(short)(4*m+2);
                    
        //System.out.println(num_quad);
    }
    public void renew_floor()
    {
        for(int i=0;i<my_width;i++)
        {
            for(int j=0;j<my_height;j++)
            {
                my_map_parent.map_array[my_position.x+i][my_position.y+j][0]='F';
            }
        }
    }
}
