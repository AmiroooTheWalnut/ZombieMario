/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 *
 * @author Amir72c(Amirooo)
 */
public class Map {

    int maximum_width[] = new int[1];
    int maximum_height[] = new int[1];
    byte map_array[][][];
    Main_renderer my_renderer;
    Floor floor[];
    Point[] mario_location;
    float gravity = 15;
    Geometry background;
    String[] my_background_tex;
    String my_path;
    enemy_monster[] all_enemy_monster;
    Mushroom_health[] healths;
    Sprite[] sprites;

    Map(Main_renderer renderer, String path) {
        my_renderer = renderer;
        my_path = path;
        my_background_tex = new String[1];
        mario_location = new Point[1];
        floor = new Floor[100];
        all_enemy_monster = new enemy_monster[100];
        sprites = new Sprite[100];
    }

    public void make_map_spec() {
        Quad background_size = new Quad(680, 500);
        background = new Geometry("Background", background_size);
        //my_background_tex="Backgrounds/background8.jpg";
        map_array = new byte[maximum_width[0]][maximum_height[0]][5];
        for (int i = 0; i < maximum_width[0]; i++) {
            for (int j = 0; j < maximum_height[0]; j++) {
                map_array[i][j][0] = '0';
            }
        }
    }

    public void make_map() {
        //my_background_tex="Backgrounds/background8.jpg";
        //mario_location=new Point(200,150);

        try {
            my_renderer.my_parent.main_shell.evaluate(new File(my_path));
            Quad background_size = new Quad(maximum_width[0] + 300, maximum_height[0] + 100);
            background = new Geometry("Background", background_size);
            //map_array[0][0]='A';
            //System.out.println((char)map_array[0][0]);
            //floor[0]=new Floor(this,new Point(0,0),1000,50,"Textures/archibrick01.jpg",0f,0f);
            //floor[1]=new Floor(this,new Point(0,60),20,500,"Textures/archibrick01.jpg",0f,0f);
            //floor[3]=new Floor(this,new Point(400,60),40,500,"Textures/archibrick01.jpg",0f,0f);
            //floor[3]=new Floor(this,new Point(400,60),40,500,"Textures/archibrick01.jpg",0f,0f);
        } catch (CompilationFailedException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        //map_array=new byte[maximum_width[0]][maximum_height[0]];
        //System.out.println(my_background_tex[0]);

        //all_enemy_monster[0]=new enemy_monster(my_renderer,this,"Characters/monster-left.png","Characters/monster-right.png",400,400,0);
        //all_enemy_monster[1]=new enemy_monster(my_renderer,this,"Characters/monster-left.png","Characters/monster-right.png",200,400,1);
        healths = new Mushroom_health[1];
        healths[0] = new Mushroom_health(this, "Items/Mushroom_health.png", 250, 600, 0);
        sprites[0] = new Sprite(my_renderer, 100, 100, 200, 200, "Textures/TEMPO.jpeg");
    }
}
