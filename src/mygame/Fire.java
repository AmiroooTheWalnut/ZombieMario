/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
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
 * @author Amir72c
 */
public class Fire {

    Fire this_fire = this;
    float size = 50;
    Main_renderer my_renderer_parent;
    float my_x_location;
    float my_y_location;
    float health = 100;
    Geometry fire_ball;
    AssetManager my_assetManager;
    Node my_rootNode;
    int bounding_volumn_width = Math.round(size * (health / 100));
    int bounding_volumn_height = Math.round(size * (health / 100));
    float v_horizontal;
    float v_vertical = 0;
    boolean left_block = false;
    boolean right_block = false;
    boolean down_block = false;
    boolean up_block = false;
    boolean is_alive = true;
    float max_speed = 4;
    Vector3f real_location;
    int margin = 10;
    int enemy_contact_number;
    boolean ismoving_right = true;
    Timer dying_timer;

    Fire(Main_renderer renderer_parent, float x_location, float y_location, boolean is_right_direction) {
        if (is_right_direction == true) {
            v_horizontal = 5;
        } else {
            v_horizontal = -5;
        }
        v_vertical=renderer_parent.mario_char.v_vertical;
        my_renderer_parent = renderer_parent;
        my_x_location = x_location;
        my_y_location = y_location;
        my_assetManager = my_renderer_parent.main_assetManager;
        my_rootNode = my_renderer_parent.main_rootNode;

        Quad e_t = new Quad(size + margin, size + margin);
        fire_ball = new Geometry("Enemy", e_t);
        //my_texture_left = texture_left;
        //my_texture_right = texture_right;
        fire_ball.setLocalTranslation(x_location, y_location, 0.1f);
        real_location = fire_ball.getLocalTranslation().clone();
        real_location.x = real_location.x - margin;
        real_location.y = real_location.y - margin;
        fire_ball.setQueueBucket(RenderQueue.Bucket.Translucent);
        Material temp_mat = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        temp_mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        Texture temp_tex_right = my_assetManager.loadTexture("Textures/fire1.png");
        temp_mat.setTexture("ColorMap", temp_tex_right);
        //temp_mat_left = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //temp_mat_left.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        //Texture temp_tex_left = my_assetManager.loadTexture(my_texture_left);
        //temp_mat_left.setTexture("ColorMap", temp_tex_left);
        fire_ball.setMaterial(temp_mat);
        my_rootNode.attachChild(fire_ball);

        dying_timer = new Timer(800, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                my_renderer_parent.enqueue(new Callable() {
                    public Object call() throws Exception {
                        // call methods that modify the scene graph here
                        my_rootNode.detachChildAt(my_rootNode.getChildIndex(fire_ball));
                        return null;
                    }
                });
                dying_timer.stop();
                my_renderer_parent.complete_refresh();
                my_renderer_parent.fire = null;
            }
        });

    }

    public void check_collision() {
        left_block = false;
        right_block = false;
        down_block = false;
        up_block = false;
        
        if (is_alive) {
            //left
            for (int i = 0; i < bounding_volumn_height; i++) {
                for (int j = 0; j < margin; j++) {
                    if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) - j][Math.round(real_location.y) + i][0] == 'F') {
                        left_block = true;
                        ismoving_right = true;
                        //enemy1.setMaterial(temp_mat_right);
                    }
                    if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) - j][Math.round(real_location.y) + i][0] == 'E') {
                        //left_mario_contact=true;
                    }
                }
            }
            //right
            for (int i = 0; i < bounding_volumn_height; i++) {
                for (int j = 0; j < margin; j++) {
                    if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + j + bounding_volumn_width][Math.round(real_location.y) + i][0] == 'F') {
                        right_block = true;
                        ismoving_right = false;
                        //enemy1.setMaterial(temp_mat_left);
                    }
                    if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + j + bounding_volumn_width][Math.round(real_location.y) + i][0] == 'E') {
                        //right_mario_contact=true;
                    }
                }
            }
            //down
            for (int i = 0; i < bounding_volumn_width; i++) {
                for (int j = 0; j < margin; j++) {
                    if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) - j][0] == 'F') {
                        down_block = true;

                        //has_jumped=false;
                    }
                    if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) - j][0] == 'E') {
                        //down_mario_contact=true;
                    }
                }
            }
            //up
            for (int i = 0; i < bounding_volumn_width; i++) {
                for (int j = 0; j < margin; j++) {
                    if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) + j + bounding_volumn_height][0] == 'F') {
                        up_block = true;
                    }
                    if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) + j + bounding_volumn_height][0] == 'E') {
                        //up_mario_contact=true;
                    }
                }
            }


        }
    }

    public void gravity_affect() {
        v_vertical = v_vertical - my_renderer_parent.running_map.gravity * 0.01f;
        //if (ismoving_right == true) {
        //    v_horizontal = v_horizontal + 0.1f;
        //} else {
        //    v_horizontal = v_horizontal - 0.1f;
        //}
        check_collision();
        if (left_block && v_horizontal < 0) {
            v_horizontal = -v_horizontal;
        }
        if (right_block && v_horizontal > 0) {
            v_horizontal = -v_horizontal;
        }
        if (up_block && v_vertical > 0) {
            v_vertical = 0;
        }
        if (down_block && v_vertical < 0) {
            v_vertical = -(v_vertical / 1.5f);
        }
        real_location.x = real_location.x + v_horizontal;
        real_location.y = real_location.y + v_vertical;
        real_location.z = 0.01f;
        fire_ball.setLocalTranslation(real_location.x - margin / 2, real_location.y - margin / 2, 0.01f);
        Quaternion temp = fire_ball.getLocalRotation().clone();
        //Quaternion temp1 = temp.fromAngles(angles)
        
        //fire_ball.setLocalRotation(new Quaternion().fromAngles(new float[]{0, 0, -my_renderer_parent.speak_timer}));
        //mario.setLocalTranslation(real_location.x+v_horizontal, real_location.y+v_vertical, 0.01f);
        //System.out.println("right_block:"+right_block);
        //System.out.println("left_block:"+left_block);
        //System.out.println("down_block:"+down_block);
        //System.out.println("up_block:"+up_block);
        //System.out.println(v_vertical);
        //if (Math.abs(v_horizontal) < 0.1) {
        //    v_horizontal = 0;
        //}
        //if (v_horizontal > 0) {
        //    v_horizontal = v_horizontal - 0.08f;
        //} else if (v_horizontal < 0) {
        //    v_horizontal = v_horizontal + 0.08f;
        //} else if (v_horizontal == 0) {
        //    v_horizontal = 0;
        //}

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
        health = health * 0.997f;
        resize_fire();
        //System.out.println(v_vertical);
    }

    public void assign_tile() {
        for (int i = 0; i < bounding_volumn_width; i++) {
            for (int j = 0; j < bounding_volumn_height; j++) {
                my_renderer_parent.running_map.map_array[Math.round(fire_ball.getLocalTranslation().x) + i][Math.round(fire_ball.getLocalTranslation().y) + j][0] = 'B';
                //my_renderer_parent.running_map.map_array[Math.round(fire_ball.getLocalTranslation().x)+i][Math.round(fire_ball.getLocalTranslation().y)+j][1]=(byte)my_order;
            }
        }

    }

    public void resize_fire() {
        fire_ball.setLocalScale(health / 100f);
        bounding_volumn_width = Math.round(size * (health / 100));
        bounding_volumn_height = Math.round(size * (health / 100));
        if (health < 50) {
            Material temp_mat_boom = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            temp_mat_boom.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            Texture temp_tex_boom = my_assetManager.loadTexture("Textures/boom.PNG");
            temp_mat_boom.setTexture("ColorMap", temp_tex_boom);
            temp_mat_boom.getAdditionalRenderState().setAlphaTest(true);
            fire_ball.setMaterial(temp_mat_boom);
            is_alive = false;
            dying_timer.start();
        }
    }
}
