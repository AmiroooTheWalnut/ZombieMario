package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.font.LineWrapMode;
import com.jme3.font.Rectangle;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.MatParam;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.shader.VarType;
import com.jme3.system.AppSettings;
import com.jme3.texture.*;
import com.jme3.ui.Picture;
import com.jme3.util.BufferUtils;
import java.awt.Point;
import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * test
 *
 * @author Amir72c(Amirooo)
 */
public class Main_renderer extends SimpleApplication {

    Main_menu my_parent;
    Main_renderer this_app = this;
    String my_load_file_path;
    AssetManager main_assetManager;
    Node main_rootNode;

    Main_renderer(Main_menu parent, String load_file_path) {
        my_load_file_path = load_file_path;
        my_parent = parent;

    }
    /**
     * Use ActionListener to respond to pressed/released inputs (key presses,
     * mouse clicks)
     */
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean pressed, float tpf) {
            //System.out.println(name + " = " + pressed);
            if ("Exit".equals(name)) {
                my_parent.setVisible(true);
                this_app.stop();
            }
        }
    };
    float camera_distance = 800;
    Geometry mario;
    Geometry floors[];
    Material floors_mat[];
    Map running_map;
    Mario_character mario_char;
    boolean isloading = true;
    float grow_timer = 0;
    float floor_refresh_timer = 0;
    Mesh floor_mesh[];
    Material mario_mat_left;
    Material mario_mat_right;
    boolean isspec = false;
    Map_constructor test_map;
    Picture health_pic;
    BitmapText health_text;
    BitmapText alarm_die_text;
    BitmapText alarm_explode_text;
    Speak_bubble speak;
    float speak_timer = 0;
    Fire fire;

    @Override
    public void simpleInitApp() {

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        health_text = new BitmapText(guiFont, false);
        alarm_die_text = new BitmapText(guiFont, false);
        alarm_explode_text = new BitmapText(guiFont, false);
        set_assetManager(assetManager, rootNode);
        if (isspec == false) {
            //Geometry start_button=new Geometry("Start",new Quad(300,100));
            //Material Start_mat=new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            //Start_mat.setTexture("ColorMap", assetManager.loadTexture("Textures/zm-right.png"));
            //start_button.setMaterial(Start_mat);
            //rootNode.attachChild(start_button);

            running_map = new Map(this, my_load_file_path);
            running_map.make_map();
            Material background_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            Texture background_tex = assetManager.loadTexture(running_map.my_background_tex[0]);
            background_mat.setTexture("ColorMap", background_tex);
            running_map.background.setMaterial(background_mat);
            running_map.background.setLocalTranslation(0, 0, -1f);
            rootNode.attachChild(running_map.background);
            mario_char = new Mario_character(this, running_map, "Characters/zm-left.png", "Characters/zm-right.png", running_map.mario_location[0].x, running_map.mario_location[0].y);
            mario = mario_char.mario;
            mario.setLocalTranslation(running_map.mario_location[0].x, running_map.mario_location[0].y, 0.1f);
            mario_mat_left = mario_char.mario_mat_left;
            mario_mat_right = mario_char.mario_mat_right;


            rootNode.attachChild(mario_char.mario_right_foot);
            rootNode.attachChild(mario_char.mario_left_foot);
            rootNode.attachChild(mario_char.mario_right_hand);
            rootNode.attachChild(mario_char.mario_left_hand);
            rootNode.attachChild(mario_char.mario_body);
            rootNode.attachChild(mario_char.mario_head);

            //mario.setMaterial(mario_mat_left);
            //rootNode.attachChild(mario);

            for (int i = 0; i < running_map.all_enemy_monster.length; i++) {
                if (running_map.all_enemy_monster[i] != null) {
                    running_map.all_enemy_monster[i].enemy1.setLocalTranslation(running_map.all_enemy_monster[i].my_x_location, running_map.all_enemy_monster[i].my_y_location, 0.02f);
                    rootNode.attachChild(running_map.all_enemy_monster[i].enemy1);
                }
            }
            for (int i = 0; i < running_map.healths.length; i++) {
                if (running_map.healths[i] != null) {
                    running_map.healths[i].mushroom_health.setLocalTranslation(running_map.healths[i].my_x_location, running_map.healths[i].my_y_location, 0.02f);
                    rootNode.attachChild(running_map.healths[i].mushroom_health);
                }
            }

            /*
             Quad m = new Quad(20,20);
             //Geometry geom = new Geometry("Box", b);
             Geometry map = new Geometry("Map", m);
             Material mat = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
             Texture cube1Tex = assetManager.loadTexture("Textures/map.jpg");
             mat.setTexture("ColorMap", cube1Tex);
             map.setMaterial(mat);
             rootNode.attachChild(map);
             */
            int mesh_counts = 0;
            for (int i = 0; i < running_map.floor.length; i++) {
                if (running_map.floor[i] != null) {
                    mesh_counts = mesh_counts + 1;
                }
            }
            floor_mesh = new Mesh[mesh_counts];
            floors = new Geometry[mesh_counts];
            floors_mat = new Material[mesh_counts];
            /*
             Vector3f [] vertices = new Vector3f[8];
             vertices[0] = new Vector3f(0,0,0);
             vertices[1] = new Vector3f(3,0,0);
             vertices[2] = new Vector3f(0,3,0);
             vertices[3] = new Vector3f(3,3,0);
             vertices[4] = new Vector3f(5,5,0);
             vertices[5] = new Vector3f(7,5,0);
             vertices[6] = new Vector3f(5,7,0);
             vertices[7] = new Vector3f(7,7,0);

             Vector2f[] texCoord = new Vector2f[8];
             texCoord[0] = new Vector2f(0,0);
             texCoord[1] = new Vector2f(0.5f,0);
             texCoord[2] = new Vector2f(0,0.5f);
             texCoord[3] = new Vector2f(0.5f,0.5f);
             texCoord[4] = new Vector2f(0,0);
             texCoord[5] = new Vector2f(1,0);
             texCoord[6] = new Vector2f(0,1);
             texCoord[7] = new Vector2f(1,1);
        
             int [] indexes = { 1,2,0, 1,3,2, 5,6,4, 5,7,6 };
             */
            for (int i = 0; i < mesh_counts; i++) {
                floor_mesh[i] = new Mesh();
                floor_mesh[i].setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(running_map.floor[i].vertices));
                floor_mesh[i].setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(running_map.floor[i].texCoord));
                floor_mesh[i].setBuffer(Type.Index, 3, BufferUtils.createShortBuffer(running_map.floor[i].indexes));
                floor_mesh[i].updateBound();
                floors[i] = new Geometry("Floor" + i, floor_mesh[i]);
                floors_mat[i] = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                Texture temp_tex = assetManager.loadTexture(running_map.floor[i].my_texture);
                temp_tex.setWrap(Texture.WrapMode.Repeat);
                floors_mat[i].setTexture("ColorMap", temp_tex);
                floors[i].setMaterial(floors_mat[i]);
                rootNode.attachChild(floors[i]);
            }

            inputManager.addMapping("Drag", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
            inputManager.addListener(analogListener, "Drag");

            flyCam.setMoveSpeed(200);
            cam.setLocation(new Vector3f(4, 4, camera_distance));
            inputManager.setCursorVisible(false);
            flyCam.setEnabled(false);
            /**
             * Map one or more inputs to an action
             */
            inputManager.addMapping("Move left", new KeyTrigger(KeyInput.KEY_LEFT));
            inputManager.addMapping("Move right", new KeyTrigger(KeyInput.KEY_RIGHT));
            inputManager.addMapping("Move down", new KeyTrigger(KeyInput.KEY_DOWN));
            inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_UP));
            inputManager.addMapping("Fire", new KeyTrigger(KeyInput.KEY_SPACE));
            inputManager.addMapping("Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
            /**
             * Add an action to one or more listeners
             */
            inputManager.addListener(actionListener, "Exit");
            inputManager.addListener(analogListener, "Move left");
            inputManager.addListener(analogListener, "Move right");
            inputManager.addListener(analogListener, "Move down");
            inputManager.addListener(analogListener, "Jump");
            inputManager.addListener(analogListener, "Fire");
            isloading = false;
        } else {
            //System.out.println("test");
            running_map = new Map(this, "");
            running_map.maximum_width[0] = test_map.my_width;
            running_map.maximum_height[0] = test_map.my_height;
            running_map.make_map_spec();
            for (int i = 0; i < test_map.num_floor; i++) {
                running_map.floor[i] = new Floor(running_map, new Point(test_map.floors[i].my_start_x, test_map.floors[i].my_start_y), test_map.floors[i].my_width, test_map.floors[i].my_height, test_map.floors[i].my_texture, test_map.floors[i].my_tile_x, test_map.floors[i].my_tile_y);
            }

            Material background_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            Texture background_tex = assetManager.loadTexture("Backgrounds/" + test_map.my_background_tex);
            background_mat.setTexture("ColorMap", background_tex);
            running_map.background.setMaterial(background_mat);
            running_map.background.setLocalTranslation(0, 0, -1f);
            rootNode.attachChild(running_map.background);
            int mesh_counts = 0;
            for (int i = 0; i < running_map.floor.length; i++) {
                if (running_map.floor[i] != null) {
                    mesh_counts = mesh_counts + 1;
                }
            }
            //System.out.println("added");
            floor_mesh = new Mesh[mesh_counts];
            floors = new Geometry[mesh_counts];
            floors_mat = new Material[mesh_counts];
            for (int i = 0; i < mesh_counts; i++) {
                floor_mesh[i] = new Mesh();
                floor_mesh[i].setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(running_map.floor[i].vertices));
                floor_mesh[i].setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(running_map.floor[i].texCoord));
                floor_mesh[i].setBuffer(Type.Index, 3, BufferUtils.createShortBuffer(running_map.floor[i].indexes));
                floor_mesh[i].updateBound();
                floors[i] = new Geometry("Floor" + i, floor_mesh[i]);
                floors_mat[i] = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                Texture temp_tex = assetManager.loadTexture(running_map.floor[i].my_texture);
                temp_tex.setWrap(Texture.WrapMode.Repeat);
                floors_mat[i].setTexture("ColorMap", temp_tex);
                floors[i].setMaterial(floors_mat[i]);
                rootNode.attachChild(floors[i]);
            }
            cam.setLocation(new Vector3f(100, 100, camera_distance));
            flyCam.setMoveSpeed(400);
            cam.setFrustumFar(100000);
        }
        /*
         NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
         assetManager, inputManager, audioRenderer, guiViewPort);
         Nifty nifty = niftyDisplay.getNifty();
         guiViewPort.addProcessor(niftyDisplay);
         flyCam.setDragToRotate(true);
    
    
         nifty.loadStyleFile("nifty-default-styles.xml");
         nifty.loadControlFile("nifty-default-controls.xml");
 
         // <screen>
         nifty.addScreen("Screen_ID", new ScreenBuilder("Hello Nifty Screen"){{
         controller(new DefaultScreenController()); // Screen properties       
 
         // <layer>
         layer(new LayerBuilder("Layer_ID") {{
         childLayoutVertical(); // layer properties, add more...
 
         // <panel>
         panel(new PanelBuilder("Panel_ID") {{
         childLayoutCenter(); // panel properties, add more...               
 
         // GUI elements
         control(new ButtonBuilder("Button_ID", "Hello Nifty"){{
         alignCenter();
         valignCenter();
         height("5%");
         width("15%");
         }});
 
         //.. add more GUI elements here              
 
         }});
         // </panel>
         }});
         // </layer>
         }}.build(nifty));
         // </screen>
 
         nifty.gotoScreen("Screen_ID"); // start the screen
         */
        //guiNode.attachChild(mario);
        //rootNode.attachChild(guiNode);

        /**
         * Write text on the screen (HUD)
         */
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Health: !!!");
        //helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
        helloText.setLineWrapMode(LineWrapMode.Word);
        helloText.setBox(new Rectangle(settings.getWidth() / 2, 100, 100, helloText.getLineHeight()));
        guiNode.attachChild(helloText);

        health_pic = new Picture("HUD Picture");
        health_pic.setImage(assetManager, "Textures/live.png", true);
        health_pic.setWidth(settings.getWidth() / 5.5f);
        health_pic.setHeight(settings.getHeight() / 3.5f);

        health_pic.setPosition(settings.getWidth() / 25, 0);
        guiNode.attachChild(health_pic);

        //guiNode.attachChild(running_map.sprites[0].my_sprite);
    }
    /**
     * Use AnalogListener to respond to continuous inputs (key presses, mouse
     * clicks)
     */
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            //System.out.println(name + " = " + value);
            //System.out.println("X:"+mario.getLocalTranslation().x);
            //System.out.println("Y:"+mario.getLocalTranslation().y);
            if (mario_char != null) {
                if (mario_char.is_alive == true) {
                    if ("Move left".equals(name)) {
                        mario_char.stamina = mario_char.stamina + 0.002f;
                        if (mario_char.has_jumped == false) {
                            mario_char.is_walking = true;
                        }

                        Vector3f v = mario.getLocalTranslation();
                        //System.out.println(name);
                        Vector3f temp_v = v.clone();
                        temp_v.z = camera_distance;
                        float camera_x = temp_v.x + 2.5f;
                        float camera_y = temp_v.y + 2.5f;
                        temp_v.x = camera_x;
                        temp_v.y = camera_y;
                        cam.setLocation(temp_v);
                        if (mario_char.v_horizontal < -mario_char.max_speed) {
                            mario_char.v_horizontal = -mario_char.max_speed;
                        } else {
                            mario_char.v_horizontal = mario_char.v_horizontal - 0.5f;
                        }
                        //mario.setMaterial(mario_mat_left);

                        mario_char.mario_body.setMaterial(mario_char.mario_mat_body_left);
                        mario_char.mario_head.setMaterial(mario_char.mario_mat_head_left);
                        mario_char.mario_right_foot.setMaterial(mario_char.mario_mat_right_foot_left);
                        mario_char.mario_left_foot.setMaterial(mario_char.mario_mat_left_foot_left);
                        mario_char.mario_right_hand.setMaterial(mario_char.mario_mat_right_hand_left);
                        mario_char.mario_left_hand.setMaterial(mario_char.mario_mat_left_hand_left);
                        mario_char.is_right = false;

                    } else if ("Move right".equals(name)) {
                        mario_char.stamina = mario_char.stamina + 0.002f;
                        if (mario_char.has_jumped == false) {
                            mario_char.is_walking = true;
                        }
                        Vector3f v = mario.getLocalTranslation();
                        //System.out.println(name);
                        Vector3f temp_v = v.clone();
                        temp_v.z = camera_distance;
                        float camera_x = temp_v.x + 2.5f;
                        float camera_y = temp_v.y + 2.5f;
                        temp_v.x = camera_x;
                        temp_v.y = camera_y;
                        cam.setLocation(temp_v);
                        if (mario_char.v_horizontal > mario_char.max_speed) {
                            mario_char.v_horizontal = mario_char.max_speed;
                        } else {
                            mario_char.v_horizontal = mario_char.v_horizontal + 0.5f;
                        }

                        //mario.setMaterial(mario_mat_right);
                        mario_char.mario_body.setMaterial(mario_char.mario_mat_body_right);
                        mario_char.mario_head.setMaterial(mario_char.mario_mat_head_right);
                        mario_char.mario_right_foot.setMaterial(mario_char.mario_mat_right_foot_right);
                        mario_char.mario_left_foot.setMaterial(mario_char.mario_mat_left_foot_right);
                        mario_char.mario_right_hand.setMaterial(mario_char.mario_mat_right_hand_right);
                        mario_char.mario_left_hand.setMaterial(mario_char.mario_mat_left_hand_right);
                        mario_char.is_right = true;

                    } else if ("Move down".equals(name)) {
                        Vector3f v = mario.getLocalTranslation();
                        //System.out.println(name);
                        Vector3f temp_v = v.clone();
                        temp_v.z = camera_distance;
                        float camera_x = temp_v.x + 0.5f;
                        float camera_y = temp_v.y + 0.5f;
                        temp_v.x = camera_x;
                        temp_v.y = camera_y;
                        cam.setLocation(temp_v);
                        mario.setLocalTranslation(v.x, v.y - value * 100, 0.1f);

                    } else if ("Jump".equals(name)) {
                        mario_char.stamina = mario_char.stamina + 0.002f;
                        Vector3f v = mario.getLocalTranslation();
                        //System.out.println(name);
                        Vector3f temp_v = v.clone();
                        temp_v.z = camera_distance;
                        float camera_x = temp_v.x + 2.5f;
                        float camera_y = temp_v.y + 2.5f;
                        temp_v.x = camera_x;
                        temp_v.y = camera_y;
                        cam.setLocation(temp_v);
                        if (mario_char.has_jumped == false) {
                            mario_char.v_vertical = 8f;
                            mario_char.has_jumped = true;
                        }

                    }/*
                     else if ("Drag".equals(name)) {
                     CollisionResults results = new CollisionResults();
                     // Convert screen click to 3d position
                     Vector2f click2d = inputManager.getCursorPosition();
                     Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                     Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
                     // Aim the ray from the clicked spot forwards.
                     Ray ray = new Ray(click3d, dir);
                     // Collect intersections between ray and all nodes in results list.
                     rootNode.collideWith(ray, results);
                     // (Print the results so we see what is going on:)
                     for (int i = 0; i < results.size(); i++) {
                     // (For each “hit”, we know distance, impact point, geometry.)
                     float dist = results.getCollision(i).getDistance();
                     Vector3f pt = results.getCollision(i).getContactPoint();
                     String target = results.getCollision(i).getGeometry().getName();
                     //System.out.println("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
                     }
                     // Use the results -- we rotate the selected geometry.
                     if (results.size() > 0) {
                     // The closest result is the target that the player picked:
                     Geometry target = results.getClosestCollision().getGeometry();

                     // Here comes the action:
                     if (target.getName().equals("Mario")) {
                     //System.out.println("hit");
                     //target.setLocalTranslation(inputManager.getCursorPosition().x,inputManager.getCursorPosition().y,0.01f);
                     //target.rotate(0, -intensity, 0);
                     } else if (target.getName().equals("Blue Box")) {
                     target.rotate(0, value, 0);
                     }
                     }
                        
                     }*/ else if ("Fire".equals(name)) {
                        if (speak == null) {
                            speak = new Speak_bubble(mario_char.mario_head, 10f, 10f, "Hello, I am Amirooo and I'm a walnut!", rootNode, assetManager);
                            if (fire == null && mario_char.health>60) {
                                fire = new Fire(this_app, mario_char.mario_left_hand.getLocalTranslation().x, mario_char.mario_left_hand.getLocalTranslation().y, mario_char.is_right);
                                mario_char.health=mario_char.health-10f;
                            }

                        }

                    }
                }
            }


        }
    };

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        /*
         if(isloading==true)
         {
         mario_char.assign_tile();
         }else{
            
         }
         */

        //System.out.println(tpf);


        if (isspec == false) {
            if (mario_char != null) {
                if (mario_char.is_alive == true) {
                    health_pic.getMaterial().setParam("Color", VarType.Vector4, new Vector4f(mario_char.health / 100f, 1, 1, 1));
                    Vector3f v = mario.getLocalTranslation();
                    //System.out.println(name);
                    Vector3f temp_v = v.clone();
                    temp_v.z = camera_distance;
                    float camera_x = temp_v.x + 0.5f;
                    float camera_y = temp_v.y + 0.5f;
                    temp_v.x = camera_x;
                    temp_v.y = camera_y;
                    cam.setLocation(temp_v);
                    running_map.background.setLocalTranslation(temp_v.x - 420 - mario.getLocalTranslation().x/2, temp_v.y - 310- mario.getLocalTranslation().y/2, -1f);
                    if (mario_char.is_grow == true) {
                        //System.out.println(mario_char.health);
                        health_text.setSize(guiFont.getCharSet().getRenderedSize());
                        health_text.setColor(ColorRGBA.Red);
                        health_text.setText("Health!");
                        health_text.setLocalTranslation(cam.getWidth() / 2, cam.getHeight() / 10, 0);
                        guiNode.attachChild(health_text);
                        mario_char.health = mario_char.health + mario_char.grow_rate;
                        mario_char.bounding_volumn_width = Math.round(mario_char.health);
                        mario_char.bounding_volumn_height = Math.round(mario_char.health);
                        //mario_char.mario.setLocalScale(mario_char.health/100, mario_char.health/100, mario_char.health/100);
                        mario_char.resize_mario();

                        grow_timer = grow_timer + tpf;
                        if (grow_timer > 5) {
                            mario_char.is_grow = false;
                            guiNode.detachChild(health_text);
                            //guiNode.detachAllChildren();
                        }
                    }
                    if (mario_char.near_die == true) {
                        alarm_die_text.setSize(guiFont.getCharSet().getRenderedSize());
                        alarm_die_text.setColor(ColorRGBA.Red);
                        alarm_die_text.setText("Warning you are close to die!");
                        alarm_die_text.setLocalTranslation(cam.getWidth() / 2, cam.getHeight() / 10, 0);
                        guiNode.attachChild(alarm_die_text);
                    } else {
                        guiNode.detachChild(alarm_die_text);
                    }
                    if (mario_char.near_explode == true) {
                        alarm_explode_text.setSize(guiFont.getCharSet().getRenderedSize());
                        alarm_explode_text.setColor(ColorRGBA.Red);
                        alarm_explode_text.setText("Warning you are close to explode!");
                        alarm_explode_text.setLocalTranslation(cam.getWidth() / 2, cam.getHeight() / 10, 0);
                        guiNode.attachChild(alarm_explode_text);
                    } else {
                        guiNode.detachChild(alarm_explode_text);
                    }
                }
                mario_char.gravity_affect();
                int sample_start_x = (int) mario_char.real_location.x - 200;
                if (sample_start_x < 0) {
                    sample_start_x = 0;
                }
                int sample_end_x = (int) mario_char.real_location.x + 200;
                if (sample_end_x > running_map.maximum_width[0]) {
                    sample_end_x = running_map.maximum_width[0];
                }

                int sample_start_y = (int) mario_char.real_location.y - 200;
                if (sample_start_y < 0) {
                    sample_start_y = 0;
                }
                int sample_end_y = (int) mario_char.real_location.y + 200;
                if (sample_end_y > running_map.maximum_height[0]) {
                    sample_end_y = running_map.maximum_height[0];
                }
                for (int i = sample_start_x; i < sample_end_x; i++) {
                    for (int j = sample_start_y; j < sample_end_y; j++) {
                        if (running_map.map_array[i][j][0] != 'F') {
                            running_map.map_array[i][j][0] = '0';
                        }
                    }
                }
                mario_char.assign_tile();
                for (int i = 0; i < running_map.all_enemy_monster.length; i++) {
                    if (running_map.all_enemy_monster[i] != null) {
                        running_map.all_enemy_monster[i].gravity_affect();
                    }
                }
                for (int i = 0; i < running_map.healths.length; i++) {
                    if (running_map.healths[i] != null) {
                        running_map.healths[i].assign_tile();
                    }
                }
                if (fire != null) {
                    if (fire.is_alive == true) {
                        fire.assign_tile();
                    }
                    fire.gravity_affect();
                }
                floor_refresh_timer = floor_refresh_timer + tpf;
                if (floor_refresh_timer > 10) {
                    for (int i = 0; i < running_map.floor.length; i++) {
                        if (running_map.floor[i] != null) {
                            running_map.floor[i].renew_floor();
                        }
                    }
                    floor_refresh_timer = 0;
                }
                mario_char.assign_parts(tpf);

            } else {
                guiNode.detachAllChildren();
                guiNode.attachChild(health_pic);
                guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
                BitmapText helloText = new BitmapText(guiFont, false);
                helloText.setSize(guiFont.getCharSet().getRenderedSize());
                helloText.setColor(ColorRGBA.Red);
                helloText.setText("MARIO is dead!");
                helloText.setLocalTranslation(cam.getWidth() / 2, cam.getHeight() / 10, 0);
                guiNode.attachChild(helloText);
            }

            if (speak != null) {
                speak_timer = speak_timer + tpf;
                speak.refresh_position();
                if (speak_timer > speak.delay) {
                    rootNode.detachChild(speak.my_bitmaptext);
                    rootNode.detachChild(speak.background);
                    speak_timer = 0;
                    speak = null;
                }
            }


        } else {
            running_map.background.setLocalScale(cam.getLocation().z / 500f);
            running_map.background.setLocalTranslation(cam.getLocation().x - cam.getLocation().z / 1.5f, cam.getLocation().y - cam.getLocation().z / 2f, -1f);
        }

    }

    public final void set_assetManager(AssetManager assetManager, Node rootNode) {
        main_assetManager = assetManager;
        main_rootNode = rootNode;
    }

    public void complete_refresh() {
        for (int i = 0; i < running_map.maximum_width[0]; i++) {
            for (int j = 0; j < running_map.maximum_height[0]; j++) {
                if (running_map.map_array[i][j][0] != 'F') {
                    running_map.map_array[i][j][0] = '0';
                }
            }
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
