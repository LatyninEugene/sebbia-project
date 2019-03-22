package model;

import java.util.Map;
import java.util.TreeMap;

public class Canvas {
    private String name;
    private Map<String, String[]> blocks;
    private Map<String, int[]> bloksPos;
    private int x;
    private int y;


    public Canvas() {
        name = "";
        blocks = new TreeMap<>();
        bloksPos = new TreeMap<>();
        x = 0;
        y = 0;
    }

    public Canvas(String name, Map<String, String[]> blocks, Map<String, int[]> bloksPos, int x, int y) {
        this.name = name;
        this.blocks = blocks;
        this.bloksPos =bloksPos;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String[]> getBloks() {
        return blocks;
    }

    public void setBloks(Map<String, String[]> blocks) {
        this.blocks = blocks;
    }
    public Map<String, int[]> getBloksPos() {
        return bloksPos;
    }

    public void setBloksPos(Map<String, int[]> bloksPos) {
        this.bloksPos = bloksPos;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "model.Canvas{" +
                "name='" + name + '\'' +
                ", blocks=" + blocks +
                '}';
    }
}
