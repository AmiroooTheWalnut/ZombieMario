/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import java.util.concurrent.Callable;

/**
 *
 * @author Amir72c(Amirooo)
 */
public class Sprite {
    
    Main_renderer my_renderer_parent;
    int my_x_position;
    int my_y_position;
    float my_width;
    float my_height;
    Geometry my_sprite;
    String my_texture;
    AssetManager my_assetManager;
    
    Sprite(Main_renderer parent,int x_position,int y_position,float width,float height,String texture)
    {
        my_renderer_parent=parent;
        my_x_position=x_position;
        my_y_position=y_position;
        my_width=width;
        my_height=height;
        my_texture=texture;
        my_assetManager=my_renderer_parent.main_assetManager;
        Quad sprite_quad = new Quad(width,height);
        my_sprite = new Geometry("Sprite", sprite_quad);
        my_sprite.setQueueBucket(RenderQueue.Bucket.Opaque); 
        Material temp_mat = new Material( my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        temp_mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Texture temp_tex = my_assetManager.loadTexture(my_texture);
        temp_mat.setTexture("ColorMap", temp_tex);
        //my_sprite.setQueueBucket(RenderQueue.Bucket.Transparent);
        my_sprite.setMaterial(temp_mat);
        my_sprite.setLocalTranslation(my_x_position, my_y_position, -0.5f);
        my_renderer_parent.enqueue(new Callable() {
        public Object call() throws Exception {
                // call methods that modify the scene graph here
                my_renderer_parent.main_rootNode.attachChild(my_sprite);
                return null; 
            } 
        }); 

    }
}
