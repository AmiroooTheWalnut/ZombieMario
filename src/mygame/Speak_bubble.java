/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.LineWrapMode;
import com.jme3.font.Rectangle;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

/**
 *
 * @author Amir72c
 */
public class Speak_bubble {

    Geometry my_parent;
    float my_x_offset;
    float my_y_offset;
    String my_string;
    Node my_rootnode;
    BitmapText my_bitmaptext;
    float delay = 3;
    Vector3f[] vertices = new Vector3f[9];
    short[] indexes = new short[27];
    float chamfer = 2;
    Geometry background;
    float scale_margin = 1.1f;
    float width = 150;

    Speak_bubble(Geometry parent, float x_offset, float y_offset, String string, Node rootnode, AssetManager assetManager) {
        my_parent = parent;
        my_x_offset = x_offset;
        my_y_offset = y_offset;
        my_string = string;
        my_rootnode = rootnode;
        BoundingBox box = (BoundingBox) my_parent.getModelBound();
        Vector3f boxSize = new Vector3f();
        box.getExtent(boxSize);

        BitmapFont guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");

        my_bitmaptext = new BitmapText(guiFont, false);
        my_bitmaptext.setSize(guiFont.getCharSet().getRenderedSize());
        my_bitmaptext.setText(string);
        //helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
        my_bitmaptext.setLineWrapMode(LineWrapMode.Word);
        my_bitmaptext.setBox(new Rectangle(my_parent.getLocalTranslation().x + (boxSize.x * my_parent.getLocalScale().x) * 2.5f, my_parent.getLocalTranslation().y + (boxSize.y * my_parent.getLocalScale().y) * 2.5f, width, my_bitmaptext.getLineHeight()));

        Mesh background_mesh = new Mesh();
        //vertices[0] = new Vector3f(-2, -2, 1);
        vertices[0] = new Vector3f(0, chamfer, 0);
        vertices[1] = new Vector3f(0, my_bitmaptext.getHeight() - chamfer, 0);
        vertices[2] = new Vector3f(chamfer, my_bitmaptext.getHeight(), 0);
        vertices[3] = new Vector3f(width - chamfer, my_bitmaptext.getHeight(), 0);
        vertices[4] = new Vector3f(width, my_bitmaptext.getHeight() - chamfer, 0);
        vertices[5] = new Vector3f(width, chamfer, 0);
        vertices[6] = new Vector3f(width - chamfer, 0, 0);
        vertices[7] = new Vector3f(chamfer, 0, 0);
        vertices[8] = new Vector3f(50, my_bitmaptext.getHeight() / 2f, 0);

        indexes[0] = (short) (0);
        indexes[1] = (short) (8);
        indexes[2] = (short) (1);

        indexes[3] = (short) (7);
        indexes[4] = (short) (8);
        indexes[5] = (short) (0);

        indexes[6] = (short) (6);
        indexes[7] = (short) (8);
        indexes[8] = (short) (1);

        indexes[9] = (short) (1);
        indexes[10] = (short) (8);
        indexes[11] = (short) (2);

        indexes[12] = (short) (2);
        indexes[13] = (short) (8);
        indexes[14] = (short) (3);

        indexes[15] = (short) (3);
        indexes[16] = (short) (8);
        indexes[17] = (short) (4);

        indexes[18] = (short) (4);
        indexes[19] = (short) (8);
        indexes[20] = (short) (5);

        indexes[21] = (short) (5);
        indexes[22] = (short) (8);
        indexes[23] = (short) (6);

        indexes[24] = (short) (6);
        indexes[25] = (short) (8);
        indexes[26] = (short) (7);

        //indexes[21] = (short) (8);
        //indexes[22] = (short) (10);
        //indexes[23] = (short) (9);
        background_mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        background_mesh.setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createShortBuffer(indexes));
        //System.out.println(my_parent.getMesh().getBound().getVolume());
        background_mesh.updateBound();
        background = new Geometry("speak_background", background_mesh);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        background.setMaterial(mat);
        background.setLocalScale(scale_margin);
        my_rootnode.attachChild(background);
        background.setLocalTranslation(my_parent.getLocalTranslation().x + (boxSize.x * my_parent.getLocalScale().x) * 2.5f, (my_parent.getLocalTranslation().y + (boxSize.y * my_parent.getLocalScale().y) * 2.5f), 4);

        my_rootnode.attachChild(my_bitmaptext);
    }

    public void refresh_position() {
        BoundingBox box = (BoundingBox) my_parent.getModelBound();
        Vector3f boxSize = new Vector3f();
        box.getExtent(boxSize);
        my_bitmaptext.setBox(new Rectangle(my_parent.getLocalTranslation().x + (boxSize.x * my_parent.getLocalScale().x) * 2.5f, my_parent.getLocalTranslation().y + (boxSize.y * my_parent.getLocalScale().y) * 2.5f, width, my_bitmaptext.getHeight()));
        my_bitmaptext.setLocalTranslation(my_bitmaptext.getLocalTranslation().x, my_bitmaptext.getLocalTranslation().y, 2f);
        background.setLocalTranslation((my_parent.getLocalTranslation().x + (boxSize.x * my_parent.getLocalScale().x) * 2.5f) - (box.getCenter().x) / 4f, (my_parent.getLocalTranslation().y + (boxSize.y * my_parent.getLocalScale().y) * 2.5f) - (my_bitmaptext.getHeight() * scale_margin) + 4, 1);
    }
}
