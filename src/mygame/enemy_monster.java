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
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;
import javax.swing.Timer;

/**
 *
 * @author Amir72c(Amirooo)
 */
public class enemy_monster {

    float size = 50;
    float health = 80;
    Geometry enemy1;
    int bounding_volumn_width = Math.round(size);
    int bounding_volumn_height = Math.round(size);
    String my_texture_left;
    String my_texture_right;
    Map my_parent;
    Main_renderer my_renderer_parent;
    float v_horizontal = 0;
    float v_vertical = 0;
    int my_x_location;
    int my_y_location;
    AssetManager my_assetManager;
    Node my_rootNode;
    boolean left_block = false;
    boolean right_block = false;
    boolean down_block = false;
    boolean up_block = false;
    boolean has_jumped = false;
    boolean left_mario_contact = false;
    boolean right_mario_contact = false;
    boolean down_mario_contact = false;
    boolean up_mario_contact = false;
    boolean is_alive = true;
    float max_speed = 10f;
    Vector3f real_location;
    int margin = 10;
    int my_order;
    boolean ismoving_right = true;
    Material temp_mat_right;
    Material temp_mat_left;
    Material temp_mat_boom;
    Timer dying_timer;

    enemy_monster(Main_renderer renderer_parent, Map parent, String texture_left, String texture_right, int x_location, int y_location, int order) {
        my_renderer_parent = renderer_parent;
        my_parent = parent;
        my_x_location = x_location;
        my_y_location = y_location;
        my_order = order;
        my_assetManager = my_parent.my_renderer.main_assetManager;
        my_rootNode = my_renderer_parent.main_rootNode;
        Quad e_t = new Quad(size + margin, size + margin);
        enemy1 = new Geometry("Enemy", e_t);
        my_texture_left = texture_left;
        my_texture_right = texture_right;
        enemy1.setLocalTranslation(x_location, y_location, 0.1f);
        real_location = enemy1.getLocalTranslation().clone();
        real_location.x = real_location.x - margin;
        real_location.y = real_location.y - margin;
        enemy1.setQueueBucket(RenderQueue.Bucket.Translucent);
        temp_mat_right = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        temp_mat_right.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Texture temp_tex_right = my_assetManager.loadTexture(my_texture_right);
        temp_mat_right.setTexture("ColorMap", temp_tex_right);
        temp_mat_left = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        temp_mat_left.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Texture temp_tex_left = my_assetManager.loadTexture(my_texture_left);
        temp_mat_left.setTexture("ColorMap", temp_tex_left);
        enemy1.setMaterial(temp_mat_right);

        temp_mat_boom = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        temp_mat_boom.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Texture temp_tex_boom = my_assetManager.loadTexture("Textures/boom.PNG");
        temp_mat_boom.setTexture("ColorMap", temp_tex_boom);

        dying_timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                my_renderer_parent.enqueue(new Callable() {
                    public Object call() throws Exception {
                        // call methods that modify the scene graph here
                        my_rootNode.detachChildAt(my_rootNode.getChildIndex(enemy1));
                        return null;
                    }
                });
                dying_timer.stop();
                my_parent.all_enemy_monster[my_order] = null;
            }
        });
    }

    public void check_collision() {
        left_block = false;
        right_block = false;
        down_block = false;
        up_block = false;
        left_mario_contact = false;
        right_mario_contact = false;
        down_mario_contact = false;
        up_mario_contact = false;
        if (is_alive) {
            //left
            for (int i = 0; i < bounding_volumn_height; i++) {
                for (int j = 0; j < margin; j++) {
                    if (my_parent.map_array[Math.round(real_location.x) - j][Math.round(real_location.y) + i][0] == 'F') {
                        left_block = true;
                        ismoving_right = true;
                        enemy1.setMaterial(temp_mat_right);
                    }
                    if (my_parent.map_array[Math.round(real_location.x) - j][Math.round(real_location.y) + i][0] == 'M') {
                        left_mario_contact = true;
                    }
                    if (my_parent.map_array[Math.round(real_location.x) - j][Math.round(real_location.y) + i][0] == 'B') {
                        health = health - 0.1f;
                    }
                }
            }
            //right
            for (int i = 0; i < bounding_volumn_height; i++) {
                for (int j = 0; j < margin; j++) {
                    if (my_parent.map_array[Math.round(real_location.x) + j + bounding_volumn_width][Math.round(real_location.y) + i][0] == 'F') {
                        right_block = true;
                        ismoving_right = false;
                        enemy1.setMaterial(temp_mat_left);
                    }
                    if (my_parent.map_array[Math.round(real_location.x) + j + bounding_volumn_width][Math.round(real_location.y) + i][0] == 'M') {
                        right_mario_contact = true;
                    }
                    if (my_parent.map_array[Math.round(real_location.x) + j + bounding_volumn_width][Math.round(real_location.y) + i][0] == 'B') {
                        health = health - 0.1f;
                    }
                }
            }
            //down
            for (int i = 0; i < bounding_volumn_width; i++) {
                for (int j = 0; j < margin; j++) {
                    if (my_parent.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) - j][0] == 'F') {
                        down_block = true;
                        has_jumped = false;
                    }
                    if (my_parent.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) - j][0] == 'M') {
                        down_mario_contact = true;
                    }
                    if (my_parent.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) - j][0] == 'B') {
                        health = health - 0.1f;
                    }
                }
            }
            //up
            for (int i = 0; i < bounding_volumn_width; i++) {
                for (int j = 0; j < margin; j++) {
                    if (my_parent.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) + j + bounding_volumn_height][0] == 'F') {
                        up_block = true;
                    }
                    if (my_parent.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) + j + bounding_volumn_height][0] == 'M') {
                        up_mario_contact = true;
                    }
                    if (my_parent.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) + j + bounding_volumn_height][0] == 'B') {
                        health = health - 0.1f;

                    }
                }
            }

            if ((up_mario_contact == true && left_mario_contact == false && right_mario_contact == false && down_mario_contact == false) && my_renderer_parent.mario_char.v_vertical < 0) {
                health = health - my_renderer_parent.mario_char.health;
                my_renderer_parent.mario_char.v_vertical = my_renderer_parent.mario_char.v_vertical + 10f;
                if (health < 0) {
                    enemy1.setMaterial(temp_mat_boom);
                    is_alive = false;
                    dying_timer.start();
                    
                    my_renderer_parent.grow_timer = 0;
                    my_renderer_parent.mario_char.grow_rate = 0.05f;
                    my_renderer_parent.mario_char.is_grow = true;
                    return;
                }
            }
            if (health < 0) {
                enemy1.setMaterial(temp_mat_boom);
                is_alive = false;
                dying_timer.start();
            }
        }
    }

    public void gravity_affect() {
        v_vertical = v_vertical - my_parent.gravity * 0.01f;
        if (ismoving_right == true) {
            v_horizontal = v_horizontal + 0.1f;
        } else {
            v_horizontal = v_horizontal - 0.1f;
        }
        check_collision();
        if (left_block && v_horizontal < 0) {
            v_horizontal = 0;
        }
        if (right_block && v_horizontal > 0) {
            v_horizontal = 0;
        }
        if (up_block && v_vertical > 0) {
            v_vertical = 0;
        }
        if (down_block && v_vertical < 0) {
            v_vertical = 0;
        }
        real_location.x = real_location.x + v_horizontal;
        real_location.y = real_location.y + v_vertical;
        real_location.z = 0.01f;
        enemy1.setLocalTranslation(real_location.x - margin / 2, real_location.y - margin / 2, 0.01f);
        //mario.setLocalTranslation(real_location.x+v_horizontal, real_location.y+v_vertical, 0.01f);
        //System.out.println("right_block:"+right_block);
        //System.out.println("left_block:"+left_block);
        //System.out.println("down_block:"+down_block);
        //System.out.println("up_block:"+up_block);
        //System.out.println(v_vertical);
        if (Math.abs(v_horizontal) < 0.1) {
            v_horizontal = 0;
        }
        if (v_horizontal > 0) {
            v_horizontal = v_horizontal - 0.08f;
        } else if (v_horizontal < 0) {
            v_horizontal = v_horizontal + 0.08f;
        } else if (v_horizontal == 0) {
            v_horizontal = 0;
        }

        if (v_vertical > 10) {
            v_vertical = 10;
        } else if (v_vertical < -10) {
            v_vertical = -10;
        }
        if (v_horizontal > max_speed) {
            v_horizontal = max_speed;
        } else if (v_horizontal < -max_speed) {
            v_horizontal = -max_speed;
        }
        if (is_alive) {
            assign_tile();
        } else {
            //enemy1.setLocalScale(1.1f, 1.1f, 1.1f);
        }

        //System.out.println(v_vertical);
    }

    public void assign_tile() {
        for (int i = 0; i < bounding_volumn_width; i++) {
            for (int j = 0; j < bounding_volumn_height; j++) {
                //System.out.println(my_parent.map_array[Math.round(enemy1.getLocalTranslation().x)+i][Math.round(enemy1.getLocalTranslation().y)+j].length);
                my_parent.map_array[Math.round(enemy1.getLocalTranslation().x) + i][Math.round(enemy1.getLocalTranslation().y) + j][0] = 'E';
                my_parent.map_array[Math.round(enemy1.getLocalTranslation().x) + i][Math.round(enemy1.getLocalTranslation().y) + j][1] = (byte) my_order;
            }
        }

    }
    //my_parent.map_array[][]
}
