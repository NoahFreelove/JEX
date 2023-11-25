package org.JEX.Core.IO.Resources;

import org.JEX.Logs.Exceptions.IOExceptions.FileReadException;
import org.JEX.Logs.Log;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.regex.*;

public class ModelLoader11 extends ModelLoader10 {
    protected String deliminators = "\\/|,|(,\\h)|\\h"; //Equal to: / or , or ", " or " "
    // Original regex:
    // (-?(\d{1,7}|(\d{0,7}\.\d{1,7})))(\/|,|(,\h)|\h)(-?(\d{1,7}|(\d{0,7}\.\d{1,7})))(\/|,|(,\h)|\h)(-?(\d{1,7}|(\d{0,7}\.\d{1,7})))
    protected String pattern3f =
            "((-)?(\\d{1,7}|(\\d{0,7}\\.\\d{1,7})))(/|,|(,\\s)|\\s)" +
            "((-)?(\\d{1,7}|(\\d{0,7}\\.\\d{1,7})))(/|,|(,\\s)|\\s)" +
            "((-)?((\\d{0,7}\\.\\d{1,7})|\\d{1,7}))";

    protected String pattern2f = "((-)?(\\d{1,7}|(\\d{0,7}\\.\\d{1,7})))(/|,|(,\\s)|\\s)" +
            "((-)?((\\d{0,7}\\.\\d{1,7})|\\d{1,7}))";

    protected Pattern f3 = Pattern.compile(pattern3f);
    protected Pattern f2 = Pattern.compile(pattern2f);


    public ModelLoader11(){
        compilePatterns();
    }

    protected void compilePatterns(){
        /*pattern3f = String.format
                ("(-?(\\d{1,7}|(\\d{0,7}\\.\\d{1,7})))(%s)" +
                        "(-?(\\d{1,7}|(\\d{0,7}\\.\\d{1,7})))(%s)" +
                        "(-?(\\d{1,7}|(\\d{0,7}\\.\\d{1,7})))", deliminators, deliminators);

        pattern2f = String.format
                ("(-)?(\\d{1,7}|(\\d{0,7}\\.\\d{1,7}))(%s)" +
                        "(-)?(\\d{1,7}|(\\d{0,7}\\.\\d{1,7}))", deliminators);*/
        f3 = Pattern.compile(pattern3f);
        f2 = Pattern.compile(pattern2f);
    }

    protected void addVals(float[] vals, int lineNum) {
        switch (mode){
            case 1 -> {
                if(vals.length < 3){
                    Log.error(new FileReadException("Error on line " + lineNum + 1 + " when loading model. Expected 3 values" +
                            "found only " + vals.length + ". (Loading normals)"));
                    normals.add(new Vector3f());
                }
                else
                    normals.add(new Vector3f(vals[0], vals[1], vals[2]));
            }
            case 2 -> {
                if(vals.length < 2) {
                    Log.error(new FileReadException("Error on line " + (lineNum + 1) + " when loading model. Expected 2 values" +
                            "found only " + vals.length + ". (Loading uvs)"));
                    uvs.add(new Vector2f());
                }
                else{
                    uvs.add(new Vector2f(vals[0],vals[1]));
                }
            }
            case 0 -> {
                if(vals.length < 3) {
                    Log.error(new FileReadException("Error on line " + (lineNum + 1) + " when loading model. Expected 3 values" +
                            "found only " + vals.length + ". (Loading vertices)"));
                    verts.add(new Vector3f());
                }
                else
                    verts.add(new Vector3f(vals[0],vals[1],vals[2]));
            }
        }
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
            float[] vals = getVals(line, lineNum);
            if (vals.length<2){
                continue;
            }
            addVals(vals, lineNum);
        }
        produceModel();
        return producedModel;
    }

    private float[] getVals(String line, int lineNum) {
        if (line.replace(" ","").startsWith("//")) // Ignore comments
            return new float[0];
        if(line.isBlank())
            return new float[0];
        line = line.split("//")[0];

        //Log.print("Line: " + mode + " : " + line);

        float[] out = new float[0];

        switch (mode){
            case 0, 1 -> {
                line = line.trim().strip();
                Matcher match = f3.matcher(line);
                if(match.matches()){
                    float val = Float.parseFloat(match.group(1));

                    float val2 = Float.parseFloat(match.group(7));

                    float val3 = Float.parseFloat(match.group(13).strip());

                    out = new float[]{val,val2,val3};
                }
            }
            case 2 -> {
                Matcher match = f2.matcher(line);
                if(match.matches()){
                    float val = Float.parseFloat(match.group(1));

                    float val2 = Float.parseFloat(match.group(7).strip());

                    out = new float[]{val,val2};
                }
            }
            default -> {}
        };
        return out;
    }

    @Override
    protected Model onLoad(String[] lines) {
        compilePatterns();
        return super.onLoad(lines);
    }
}
