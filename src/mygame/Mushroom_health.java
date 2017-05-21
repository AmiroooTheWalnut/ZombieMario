/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

/**
 *
 * @author Amir72c(Amirooo)
 */
public class Mushroom_health {
    float size=30;
    Geometry mushroom_health;
    int bounding_volumn_width=Math.round(size);
    int bounding_volumn_height=Math.round(size);
    String my_texture;
    Map my_parent;
    int my_x_location;
    int my_y_location;
    AssetManager my_assetManager;
    Vector3f real_location;
    int margin=10;
    Material temp_mat;
    int my_order;
    boolean is_alive=true;
    Mushroom_health(Map parent,String texture,int x_location,int y_location,int order)
    {
        my_parent=parent;
        my_texture=texture;
        my_x_location=x_location;
        my_y_location=y_location;
        my_assetManager=my_parent.my_renderer.main_assetManager;
        Quad m_t = new Quad(size+margin,size+margin);
        mushroom_health = new Geometry("Mario", m_t);
        
        mushroom_health.setLocalTranslation(x_location, y_location, 0.1f);
        real_location = mushroom_health.getLocalTranslation().clone();
        real_location.x=real_location.x-margin;
        real_location.y=real_location.y-margin;
        
        mushroom_health.setQueueBucket(RenderQueue.Bucket.Translucent); 
        temp_mat = new Material( my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        temp_mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Texture temp_tex_right = my_assetManager.loadTexture(my_texture);
        temp_mat.setTexture("ColorMap", temp_tex_right);
        mushroom_health.setMaterial(temp_mat);
    }
    
    public void assign_tile()
    {
        if(is_alive)
        {
            for(int i=0;i<bounding_volumn_width;i++)
            {
                for(int j=0;j<bounding_volumn_height;j++)
                {
                    my_parent.map_array[Math.round(mushroom_health.getLocalTranslation().x)+i][Math.round(mushroom_health.getLocalTranslation().y)+j][0]='H';
                    my_parent.map_array[Math.round(mushroom_health.getLocalTranslation().x)+i][Math.round(mushroom_health.getLocalTranslation().y)+j][1]=(byte)my_order;
                }
            }
        }
        
        //my_parent.map_array[][]
    }
    
}
