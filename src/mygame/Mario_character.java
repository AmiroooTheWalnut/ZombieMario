/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
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
 * @author Amir72c(Amirooo)
 */
public class Mario_character {

    float health = 100;
    Geometry mario;
    Geometry mario_body;
    Geometry mario_head;
    Geometry mario_right_hand;
    Geometry mario_left_hand;
    Geometry mario_right_foot;
    Geometry mario_left_foot;
    int bounding_volumn_width = Math.round(health);
    int bounding_volumn_height = Math.round(health);
    String my_texture_left;
    String my_texture_right;
    Main_renderer my_renderer_parent;
    Map my_map_parent;
    float v_horizontal = 0;
    float v_vertical = 0;
    boolean is_alive = true;
    boolean left_block = false;
    boolean right_block = false;
    boolean down_block = false;
    boolean up_block = false;
    boolean has_jumped = false;
    boolean enemy_down = false;
    boolean enemy_up = false;
    boolean enemy_left = false;
    boolean enemy_right = false;
    boolean is_walking = false;
    boolean is_right = true;
    boolean near_die=false;
    boolean near_explode=false;
    float max_speed = 3;
    Vector3f real_location;
    Node my_rootNode;
    int margin = 10;
    int enemy_contact_number;
    boolean is_grow;
    float grow_rate;
    boolean is_dead = false;
    int health_index;
    int handmade_timer = 0;
    Timer grow_timer;
    AssetManager my_assetManager;
    Texture mario_tex_left;
    Texture mario_tex_right;
    Material mario_mat_left;
    Material mario_mat_right;
    Material mario_mat_body_right;
    Material mario_mat_head_right;
    Material mario_mat_right_foot_right;
    Material mario_mat_left_foot_right;
    Material mario_mat_right_hand_right;
    Material mario_mat_left_hand_right;
    Material mario_mat_body_left;
    Material mario_mat_head_left;
    Material mario_mat_right_foot_left;
    Material mario_mat_left_foot_left;
    Material mario_mat_right_hand_left;
    Material mario_mat_left_hand_left;
    Texture mario_tex_body_right;
    Texture mario_tex_head_right;
    Texture mario_tex_right_foot_right;
    Texture mario_tex_left_foot_right;
    Texture mario_tex_right_hand_right;
    Texture mario_tex_left_hand_right;
    Texture mario_tex_body_left;
    Texture mario_tex_head_left;
    Texture mario_tex_right_foot_left;
    Texture mario_tex_left_foot_left;
    Texture mario_tex_right_hand_left;
    Texture mario_tex_left_hand_left;
    float stamina_timer = 0;
    float walk_timer = 0;
    float stamina = 1;
    Quaternion roll = new Quaternion();
    Timer dying_timer;

    Mario_character(Main_renderer renderer_parent, Map map_parent, String texture_left, String texture_right, int x_location, int y_location) {
        float threshold = 0.9f;
        my_renderer_parent = renderer_parent;
        my_map_parent = map_parent;
        my_rootNode = my_renderer_parent.main_rootNode;
        my_assetManager = my_renderer_parent.main_assetManager;
        Quad m_t = new Quad(health + margin, health + margin);
        mario = new Geometry("Mario", m_t);
        my_texture_left = texture_left;
        my_texture_right = texture_right;
        /*
         Material mario_mat = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
         mario_mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
         Texture mario_tex = assetManager.loadTexture("Textures/Smile.png");
         mario_mat.setTexture("ColorMap", mario_tex);
         mario.setMaterial(mario_mat);
         */
        mario.setLocalTranslation(x_location, y_location, 0.1f);
        real_location = mario.getLocalTranslation().clone();
        real_location.x = real_location.x - margin;
        real_location.y = real_location.y - margin;
        mario.setQueueBucket(RenderQueue.Bucket.Translucent);
        mario_mat_right = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_right.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_right = my_assetManager.loadTexture(my_texture_right);
        mario_mat_right.setTexture("ColorMap", mario_tex_right);
        mario_mat_left = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_left.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_left = my_assetManager.loadTexture(my_texture_left);
        mario_mat_left.setTexture("ColorMap", mario_tex_left);

        mario_mat_body_right = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_body_right.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_body_right = my_assetManager.loadTexture("Characters/zm_body_right.png");
        mario_mat_body_right.setTexture("ColorMap", mario_tex_body_right);
        mario_mat_body_right.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_body_right.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_head_right = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_head_right.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_head_right = my_assetManager.loadTexture("Characters/zm_head_right.png");
        mario_mat_head_right.setTexture("ColorMap", mario_tex_head_right);

        mario_mat_head_right.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_head_right.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_right_foot_right = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_right_foot_right.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_right_foot_right = my_assetManager.loadTexture("Characters/zm_right_foot_right.png");
        mario_mat_right_foot_right.setTexture("ColorMap", mario_tex_right_foot_right);

        mario_mat_right_foot_right.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_right_foot_right.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_left_foot_right = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_left_foot_right.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_left_foot_right = my_assetManager.loadTexture("Characters/zm_left_foot_right.png");
        mario_mat_left_foot_right.setTexture("ColorMap", mario_tex_left_foot_right);

        mario_mat_left_foot_right.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_left_foot_right.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_right_hand_right = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_right_hand_right.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_right_hand_right = my_assetManager.loadTexture("Characters/zm_right_hand_right.png");
        mario_mat_right_hand_right.setTexture("ColorMap", mario_tex_right_hand_right);

        mario_mat_right_hand_right.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_right_hand_right.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_left_hand_right = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_left_hand_right.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_left_hand_right = my_assetManager.loadTexture("Characters/zm_left_hand_right.png");
        mario_mat_left_hand_right.setTexture("ColorMap", mario_tex_left_hand_right);

        mario_mat_left_hand_right.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_left_hand_right.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_body_left = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_body_left.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_body_left = my_assetManager.loadTexture("Characters/zm_body_left.png");
        mario_mat_body_left.setTexture("ColorMap", mario_tex_body_left);

        mario_mat_body_left.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_body_left.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_head_left = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_head_left.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_head_left = my_assetManager.loadTexture("Characters/zm_head_left.png");
        mario_mat_head_left.setTexture("ColorMap", mario_tex_head_left);

        mario_mat_head_left.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_head_left.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_right_foot_left = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_right_foot_left.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_right_foot_left = my_assetManager.loadTexture("Characters/zm_right_foot_left.png");
        mario_mat_right_foot_left.setTexture("ColorMap", mario_tex_right_foot_left);

        mario_mat_right_foot_left.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_right_foot_left.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_left_foot_left = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_left_foot_left.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_left_foot_left = my_assetManager.loadTexture("Characters/zm_left_foot_left.png");
        mario_mat_left_foot_left.setTexture("ColorMap", mario_tex_left_foot_left);

        mario_mat_left_foot_left.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_left_foot_left.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_right_hand_left = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_right_hand_left.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_right_hand_left = my_assetManager.loadTexture("Characters/zm_right_hand_left.png");
        mario_mat_right_hand_left.setTexture("ColorMap", mario_tex_right_hand_left);

        mario_mat_right_hand_left.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_right_hand_left.getAdditionalRenderState().setAlphaFallOff(threshold);

        mario_mat_left_hand_left = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mario_mat_left_hand_left.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        mario_tex_left_hand_left = my_assetManager.loadTexture("Characters/zm_left_hand_left.png");
        mario_mat_left_hand_left.setTexture("ColorMap", mario_tex_left_hand_left);

        mario_mat_left_hand_left.getAdditionalRenderState().setAlphaTest(true);
        mario_mat_left_hand_left.getAdditionalRenderState().setAlphaFallOff(threshold);

        Quad m_b = new Quad(health / 2 + margin, health / 2 + margin);
        mario_body = new Geometry("Mario_body", m_b);
        Quad m_h = new Quad(health / 1.3f + margin, health / 1.5f + margin);
        mario_head = new Geometry("Mario_head", m_h);
        Quad m_rh = new Quad(health / 3 + margin, health / 3 + margin);
        mario_right_hand = new Geometry("Mario_rh", m_rh);
        Quad m_lh = new Quad(health / 3 + margin, health / 3 + margin);
        mario_left_hand = new Geometry("Mario_lh", m_lh);
        Quad m_rf = new Quad(health / 3 + margin, health / 3 + margin);
        mario_right_foot = new Geometry("Mario_rf", m_rf);
        Quad m_lf = new Quad(health / 3 + margin, health / 3 + margin);
        mario_left_foot = new Geometry("Mario_rf", m_lf);

        mario_body.setQueueBucket(RenderQueue.Bucket.Transparent);
        mario_head.setQueueBucket(RenderQueue.Bucket.Transparent);
        mario_right_foot.setQueueBucket(RenderQueue.Bucket.Transparent);
        mario_left_foot.setQueueBucket(RenderQueue.Bucket.Transparent);
        mario_right_hand.setQueueBucket(RenderQueue.Bucket.Transparent);
        mario_left_hand.setQueueBucket(RenderQueue.Bucket.Transparent);

        mario_body.setMaterial(mario_mat_body_right);
        mario_head.setMaterial(mario_mat_head_right);
        mario_right_foot.setMaterial(mario_mat_right_foot_right);
        mario_left_foot.setMaterial(mario_mat_left_foot_right);
        mario_right_hand.setMaterial(mario_mat_right_hand_right);
        mario_left_hand.setMaterial(mario_mat_left_hand_right);

        dying_timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                my_renderer_parent.enqueue(new Callable() {
                    public Object call() throws Exception {
                        // call methods that modify the scene graph here
                        my_rootNode.detachChildAt(my_rootNode.getChildIndex(mario_body));
                        my_rootNode.detachChildAt(my_rootNode.getChildIndex(mario_head));
                        my_rootNode.detachChildAt(my_rootNode.getChildIndex(mario_right_hand));
                        my_rootNode.detachChildAt(my_rootNode.getChildIndex(mario_left_hand));
                        my_rootNode.detachChildAt(my_rootNode.getChildIndex(mario_right_foot));
                        my_rootNode.detachChildAt(my_rootNode.getChildIndex(mario_left_foot));
                        return null;
                    }
                });
                dying_timer.stop();
                my_renderer_parent.mario_char = null;
            }
        });
    }

    public void check_collision() {
        left_block = false;
        right_block = false;
        down_block = false;
        up_block = false;
        //enemy_down=false;
        //enemy_up=false;
        //enemy_left=false;
        //enemy_right=false;
        //left
        for (int i = 0; i < bounding_volumn_height; i++) {
            for (int j = 0; j < margin; j++) {
                if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) - j][Math.round(real_location.y) + i][0] == 'F') {
                    left_block = true;
                }
                if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) - j][Math.round(real_location.y) + i][0] == 'E') {
                    //enemy_left=true;
                    health = health - 0.001f;
                    resize_mario();
                }
                if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) - j][Math.round(real_location.y) + i][0] == 'H') {
                    health_index = (int) my_renderer_parent.running_map.map_array[Math.round(real_location.x) - j][Math.round(real_location.y) + i][1];
                    my_renderer_parent.enqueue(new Callable() {
                        public Object call() throws Exception {
                            // call methods that modify the scene graph here
                            my_rootNode.detachChild(my_renderer_parent.running_map.healths[health_index].mushroom_health);
                            return null;
                        }
                    });
                    //System.out.println(my_rootNode.getChildIndex(my_map_parent.healths[health_index].mushroom_health));
                    //my_rootNode.detachChildAt(my_rootNode.getChildIndex(my_map_parent.healths[health_index].mushroom_health));
                    my_map_parent.healths[health_index].is_alive = false;
                    my_renderer_parent.grow_timer = 0;
                    is_grow = true;
                }
            }
        }
        //right
        for (int i = 0; i < bounding_volumn_height; i++) {
            for (int j = 0; j < margin; j++) {
                if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + j + bounding_volumn_width][Math.round(real_location.y) + i][0] == 'F') {
                    right_block = true;
                }
                if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + j + bounding_volumn_width][Math.round(real_location.y) + i][0] == 'E') {
                    //enemy_right=true;
                    health = health - 0.001f;
                    resize_mario();
                }
                if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + j + bounding_volumn_width][Math.round(real_location.y) + i][0] == 'H') {
                    health_index = (int) my_renderer_parent.running_map.map_array[Math.round(real_location.x) + j + bounding_volumn_width][Math.round(real_location.y) + i][1];
                    my_renderer_parent.enqueue(new Callable() {
                        public Object call() throws Exception {
                            // call methods that modify the scene graph here
                            my_rootNode.detachChild(my_renderer_parent.running_map.healths[health_index].mushroom_health);
                            return null;
                        }
                    });
                    //System.out.println(my_rootNode.getChildIndex(my_map_parent.healths[health_index].mushroom_health));
                    //my_rootNode.detachChildAt(my_rootNode.getChildIndex(my_map_parent.healths[health_index].mushroom_health));
                    my_map_parent.healths[health_index].is_alive = false;
                    my_renderer_parent.grow_timer = 0;
                    is_grow = true;
                }
            }
        }
        //down
        for (int i = 0; i < bounding_volumn_width; i++) {
            for (int j = 0; j < margin; j++) {
                if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) - j][0] == 'F') {
                    down_block = true;
                    has_jumped = false;
                }
                if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) - j][0] == 'E') {
                    //health=health+0.001f;
                    //bounding_volumn_width=Math.round(health);
                    //bounding_volumn_height=Math.round(health);
                    //mario.setLocalScale(health/100, health/100, health/100);
                    //System.out.println(my_parent.running_map.map_array[Math.round(real_location.x)+i][Math.round(real_location.y)-j].substring(1));
                    //enemy_down=true;
                    //enemy_contact_number=Integer.parseInt(my_parent.running_map.map_array[Math.round(real_location.x)+i][Math.round(real_location.y)-j].substring(1));
                    //my_parent.running_map.all_enemy_monster[Integer.parseInt(my_parent.running_map.map_array[Math.round(real_location.x)+i][Math.round(real_location.y)-j].substring(1))]=null;
                }
                if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) - j][0] == 'H') {
                    health_index = (int) my_renderer_parent.running_map.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) - j][1];
                    my_renderer_parent.enqueue(new Callable() {
                        public Object call() throws Exception {
                            // call methods that modify the scene graph here
                            my_rootNode.detachChild(my_renderer_parent.running_map.healths[health_index].mushroom_health);
                            return null;
                        }
                    });
                    //System.out.println(my_rootNode.getChildIndex(my_map_parent.healths[health_index].mushroom_health));
                    //my_rootNode.detachChildAt(my_rootNode.getChildIndex(my_map_parent.healths[health_index].mushroom_health));
                    my_map_parent.healths[health_index].is_alive = false;
                    my_renderer_parent.grow_timer = 0;
                    grow_rate = 0.1f;
                    is_grow = true;
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
                    //enemy_up=true;
                    health = health - 0.001f;
                    resize_mario();
                }
                if (my_renderer_parent.running_map.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) + j + bounding_volumn_height][0] == 'H') {
                    health_index = (int) my_renderer_parent.running_map.map_array[Math.round(real_location.x) + i][Math.round(real_location.y) + j + bounding_volumn_height][1];
                    //System.out.println(my_rootNode.getChildIndex(my_map_parent.healths[health_index].mushroom_health));

                    my_renderer_parent.enqueue(new Callable() {
                        public Object call() throws Exception {
                            // call methods that modify the scene graph here
                            my_rootNode.detachChild(my_renderer_parent.running_map.healths[health_index].mushroom_health);
                            return null;
                        }
                    });
                    //my_rootNode.detachChildAt(my_rootNode.getChildIndex(my_map_parent.healths[health_index].mushroom_health));
                    my_map_parent.healths[health_index].is_alive = false;
                    my_renderer_parent.grow_timer = 0;
                    is_grow = true;
                    //System.out.println(my_renderer_parent.running_map.map_array[Math.round(real_location.x)+i][Math.round(real_location.y)+j+bounding_volumn_height]);
                }
            }
        }
        //if(enemy_down==true && enemy_left==false && enemy_right==false && enemy_up==false)
        //{
        //    my_parent.running_map.all_enemy_monster[enemy_contact_number]=null;
        //}

    }

    public void gravity_affect() {
        v_vertical = v_vertical - my_renderer_parent.running_map.gravity * 0.01f;
        if (is_alive == true) {
            check_collision();
        } else {
            left_block = false;
            right_block = false;
            down_block = false;
            up_block = false;
        }

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
        mario.setLocalTranslation(real_location.x - margin / 2, real_location.y - margin / 2, 0.1f);
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
        //System.out.println(v_vertical);
    }

    public void assign_tile() {
        if (is_alive == true) {
            for (int i = 0; i < bounding_volumn_width; i++) {
                for (int j = 0; j < bounding_volumn_height; j++) {
                    my_map_parent.map_array[Math.round(mario.getLocalTranslation().x) + i][Math.round(mario.getLocalTranslation().y) + j][0] = 'M';
                }
            }
            //my_parent.map_array[][]
        }
    }

    public void assign_parts(float tpf) {
        stamina_timer = stamina_timer + tpf;
        health=health-tpf*0.8f;
        resize_mario();
        walk_timer = walk_timer + tpf;
        if (is_right == true) {
            if (is_walking == true) {
                roll.fromAngleAxis((float) Math.sin(walk_timer * 5) / 10, new Vector3f(0, 0, 1));
                mario_right_foot.setLocalRotation(roll);
                roll.fromAngleAxis((float) Math.cos(walk_timer * 5) / 10, new Vector3f(0, 0, 1));
                mario_left_foot.setLocalRotation(roll);
                mario_right_foot.setLocalTranslation(mario.getLocalTranslation().x + (float) Math.sin(walk_timer * 5), mario.getLocalTranslation().y + (float) Math.cos(walk_timer * 5), mario.getLocalTranslation().z + 0.02f);
                mario_left_foot.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 2.3f + (float) Math.cos(walk_timer * 5), mario.getLocalTranslation().y - bounding_volumn_height / 50f + (float) Math.sin(walk_timer * 5), mario.getLocalTranslation().z + 0.2f);
                mario_right_hand.setLocalRotation(roll);
                mario_right_hand.setLocalTranslation(tpf, tpf, 0.2f);
                roll.fromAngleAxis((float) Math.sin(walk_timer * 5) / 10, new Vector3f(0, 0, 1));
                mario_left_hand.setLocalRotation(roll);
                mario_right_hand.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 5f + (float) (Math.cos(walk_timer * 5) * 5), mario.getLocalTranslation().y + bounding_volumn_height / 2.5f, mario.getLocalTranslation().z + 0.2f);
                mario_left_hand.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 1.53f + (float) (Math.sin(walk_timer * 5)), mario.getLocalTranslation().y + bounding_volumn_height / 2.5f, mario.getLocalTranslation().z - 0.02f);
            } else {
                mario_right_foot.setLocalTranslation(mario.getLocalTranslation().x, mario.getLocalTranslation().y, mario.getLocalTranslation().z + 0.02f);
                mario_left_foot.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 2.3f, mario.getLocalTranslation().y - 2, mario.getLocalTranslation().z + 0.2f);

                mario_right_hand.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 5f, mario.getLocalTranslation().y + bounding_volumn_height / 2.5f + (float) (Math.sin(stamina_timer * Math.pow(stamina, 3)) * stamina * 1.2), mario.getLocalTranslation().z + 0.2f);
                mario_left_hand.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 1.53f, mario.getLocalTranslation().y + bounding_volumn_height / 2.5f + (float) (Math.sin(stamina_timer * Math.pow(stamina, 3)) * stamina * 1.2), mario.getLocalTranslation().z - 0.02f);
            }
            is_walking = false;

            mario_body.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 7.1f, mario.getLocalTranslation().y + bounding_volumn_height / 4f + (float) (Math.sin(stamina_timer * Math.pow(stamina, 3)) * stamina * 1.2), mario.getLocalTranslation().z);

            mario_head.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 5f, mario.getLocalTranslation().y + bounding_volumn_height / 1.38f + (float) (Math.sin(stamina_timer * Math.pow(stamina, 3)) * stamina * 1.2), mario.getLocalTranslation().z + 0.02f);
        } else {
            if (is_walking == true) {
                roll.fromAngleAxis((float) Math.sin(walk_timer * 5) / 10, new Vector3f(0, 0, 1));
                mario_right_foot.setLocalRotation(roll);
                roll.fromAngleAxis((float) Math.cos(walk_timer * 5) / 10, new Vector3f(0, 0, 1));
                mario_left_foot.setLocalRotation(roll);
                mario_right_foot.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 1.56f + (float) Math.sin(walk_timer * 5), mario.getLocalTranslation().y + (float) Math.cos(walk_timer * 5), mario.getLocalTranslation().z + 0.2f);
                mario_left_foot.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 5.55f + (float) Math.cos(walk_timer * 5), mario.getLocalTranslation().y - bounding_volumn_height / 50f + (float) Math.sin(walk_timer * 5), mario.getLocalTranslation().z + 0.2f);
                mario_right_hand.setLocalRotation(roll);
                mario_right_hand.setLocalTranslation(tpf, tpf, 0.2f);
                roll.fromAngleAxis((float) Math.sin(walk_timer * 5) / 10, new Vector3f(0, 0, 1));
                mario_left_hand.setLocalRotation(roll);
                mario_right_hand.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 2.38f + (float) (Math.cos(walk_timer * 5) * 5), mario.getLocalTranslation().y + bounding_volumn_height / 2.5f, mario.getLocalTranslation().z + 0.2f);
                mario_left_hand.setLocalTranslation(mario.getLocalTranslation().x + (float) (Math.sin(walk_timer * 5)), mario.getLocalTranslation().y + bounding_volumn_height / 2.5f, mario.getLocalTranslation().z - 0.2f);
            } else {
                mario_right_foot.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 1.56f, mario.getLocalTranslation().y, mario.getLocalTranslation().z + 0.2f);
                mario_left_foot.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 5.55f, mario.getLocalTranslation().y - bounding_volumn_height / 50f, mario.getLocalTranslation().z + 0.2f);

                mario_right_hand.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 2.38f, mario.getLocalTranslation().y + bounding_volumn_height / 2.5f + (float) (Math.sin(stamina_timer * Math.pow(stamina, 3)) * stamina * 1.2), mario.getLocalTranslation().z + 0.2f);
                mario_left_hand.setLocalTranslation(mario.getLocalTranslation().x, mario.getLocalTranslation().y + bounding_volumn_width / 2.5f + (float) (Math.sin(stamina_timer * Math.pow(stamina, 3)) * stamina * 1.2), mario.getLocalTranslation().z - 0.2f);
            }
            is_walking = false;

            mario_body.setLocalTranslation(mario.getLocalTranslation().x + bounding_volumn_width / 3.03f, mario.getLocalTranslation().y + bounding_volumn_height / 4f + (float) (Math.sin(stamina_timer * Math.pow(stamina, 3)) * stamina * 1.2), mario.getLocalTranslation().z);

            mario_head.setLocalTranslation(mario.getLocalTranslation().x, mario.getLocalTranslation().y + bounding_volumn_width / 1.38f + (float) (Math.sin(stamina_timer * Math.pow(stamina, 3)) * stamina * 1.2), mario.getLocalTranslation().z + 0.02f);
        }

        if (stamina_timer > 2 * Math.PI / (Math.pow(stamina, 3))) {
            stamina_timer = 0;
        }
        if (walk_timer > 2 * Math.PI) {
            walk_timer = 0;
        }
        stamina = stamina - 0.001f;
        if (stamina > 2.1f) {
            stamina = 2.1f;
        }
        if (stamina < 1) {
            stamina = 1;
        }
    }

    public void resize_mario() {
        if (health < 40) {
            Material temp_mat_boom = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            temp_mat_boom.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            Texture temp_tex_boom = my_assetManager.loadTexture("Textures/boom.PNG");
            temp_mat_boom.setTexture("ColorMap", temp_tex_boom);
            temp_mat_boom.getAdditionalRenderState().setAlphaTest(true);
            mario_body.setMaterial(temp_mat_boom);
            mario_head.setMaterial(temp_mat_boom);
            mario_right_hand.setMaterial(temp_mat_boom);
            mario_left_hand.setMaterial(temp_mat_boom);
            mario_right_foot.setMaterial(temp_mat_boom);
            mario_left_foot.setMaterial(temp_mat_boom);
            is_alive = false;
            dying_timer.start();
        } else if (health > 120) {
            Material temp_mat_boom = new Material(my_assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            temp_mat_boom.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            Texture temp_tex_boom = my_assetManager.loadTexture("Textures/boom.PNG");
            temp_mat_boom.setTexture("ColorMap", temp_tex_boom);
            temp_mat_boom.getAdditionalRenderState().setAlphaTest(true);
            mario_body.setMaterial(temp_mat_boom);
            mario_head.setMaterial(temp_mat_boom);
            mario_right_hand.setMaterial(temp_mat_boom);
            mario_left_hand.setMaterial(temp_mat_boom);
            mario_right_foot.setMaterial(temp_mat_boom);
            mario_left_foot.setMaterial(temp_mat_boom);
            is_alive = false;
            dying_timer.start();
        }
        if(health<60)
        {
            near_die=true;
        }
        if(health>=60 && health<=100)
        {
            near_die=false;
            near_explode=false;
        }
        if(health>100)
        {
            near_explode=true;
        }
        bounding_volumn_width = Math.round(health);
        bounding_volumn_height = Math.round(health);
        mario.setLocalScale(health / 100, health / 100, health / 100);
        mario_body.setLocalScale(health / 100, health / 100, health / 100);
        mario_head.setLocalScale(health / 100, health / 100, health / 100);
        mario_right_hand.setLocalScale(health / 100, health / 100, health / 100);
        mario_left_hand.setLocalScale(health / 100, health / 100, health / 100);
        mario_right_foot.setLocalScale(health / 100, health / 100, health / 100);
        mario_left_foot.setLocalScale(health / 100, health / 100, health / 100);
    }
}
