package org.JEX.Core.IO.Resources;

import org.JEX.Logs.Exceptions.IOExceptions.FileReadException;
import org.JEX.Logs.Exceptions.IOExceptions.ParseException;
import org.JEX.Logs.Log;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

public class ModelLoader10 extends ModelLoader {
    ArrayList<Vector3f> verts = new ArrayList<>();
    ArrayList<Vector3f> normals = new ArrayList<>();
    ArrayList<Vector2f> uvs = new ArrayList<>();

    protected Model producedModel = new Model();

    protected int mode = 0;

    protected boolean checkData(String line){
        if(line.startsWith("name:"))
            return true;
        return false;
    }

    protected boolean checkMode(String line){
        switch (line.strip()) {
            case "verts" -> {
                mode = 0;
                return true;
            }
            case "normals" -> {
                mode = 1;
                return true;
            }
            case "uvs" -> {
                mode = 2;
                return true;
            }
            case "end"->{
                mode = -1;
                return true;
            }
        };
        return false;
    }

    protected void act(String line, int lineNum){
        String[] vals = line.split("/");
        switch (mode){
            case 1 -> {
                if(vals.length < 3){
                    Log.error(new FileReadException("Error on line " + lineNum + 1 + " when loading model. Expected 3 values" +
                            "found only " + vals.length + ". (Loading normals)"));
                    normals.add(new Vector3f());
                }
                try{
                    normals.add(new Vector3f(Float.parseFloat(vals[0]), Float.parseFloat(vals[1]), Float.parseFloat(vals[2])));
                }
                catch (NumberFormatException e){
                    Log.error(new ParseException("Error on line " + (lineNum + 1) + " when loading model. Expected 3 values" +
                            "found only " + vals.length + ". (Loading normals)", "Setting to 0,0,0"));
                }
            }
            case 2 -> {
                if(vals.length < 2) {
                    Log.error(new FileReadException("Error on line " + (lineNum + 1) + " when loading model. Expected 2 values" +
                            "found only " + vals.length + ". (Loading uvs)"));
                    uvs.add(new Vector2f());
                }
                try {
                    uvs.add(new Vector2f(Float.parseFloat(vals[0]), Float.parseFloat(vals[1])));
                } catch (NumberFormatException e) {
                    Log.error(new ParseException("Error on line " + (lineNum + 1) + " when loading model. Expected 3 values" +
                            "found only " + vals.length + ". (Loading normals)", "Setting to 0,0"));
                }
            }
            case 0 -> {
                if(vals.length < 3) {
                    Log.error(new FileReadException("Error on line " + (lineNum + 1) + " when loading model. Expected 3 values" +
                            "found only " + vals.length + ". (Loading vertices)"));
                    verts.add(new Vector3f());
                }
                try {
                    verts.add(new Vector3f(Float.parseFloat(vals[0]), Float.parseFloat(vals[1]), Float.parseFloat(vals[2])));
                } catch (NumberFormatException e) {
                    Log.error(new ParseException("Error on line " + (lineNum + 1) + " when loading model. Expected 3 values" +
                            "found only " + vals.length + ". (Loading normals)", "Setting to 0,0,0"));
                }
            }
        }
    }

    protected void produceModel(){
        Vector3f[] vertsArr = new Vector3f[verts.size()];
        Vector3f[] normalsArr = new Vector3f[normals.size()];
        Vector2f[] uvsArr = new Vector2f[uvs.size()];

        verts.toArray(vertsArr);
        normals.toArray(normalsArr);
        uvs.toArray(uvsArr);
        producedModel = new Model(vertsArr, normalsArr, uvsArr);
    }

    @Override
    protected Model loadModelAbstract(String[] data) {
        verts.clear();
        normals.clear();
        uvs.clear();
        int lineNum = 0;
        for (String line : data) {
            lineNum++;
            if(checkData(line))
                continue;
            if(checkMode(line))
                continue;
            act(line, lineNum);
        }
        produceModel();
        return producedModel;
    }
}
