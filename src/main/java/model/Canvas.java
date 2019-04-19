package model;

import java.util.Map;
import java.util.TreeMap;

public class Canvas {
    private String typeName;
    private Map<String, String[]> blocks;
    private Map<String, int[]> blocksPosition;
    private int x;
    private int y;


    public Canvas() {
        typeName = "";
        blocks = new TreeMap<>();
        blocksPosition = new TreeMap<>();
        x = 0;
        y = 0;
    }

    public Canvas(String typeName, Map<String, String[]> blocks, Map<String, int[]> bloksPos, int x, int y) {
        this.typeName = typeName;
        this.blocks = blocks;
        this.blocksPosition =bloksPos;
        this.x = x;
        this.y = y;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Map<String, String[]> getBloks() {
        return blocks;
    }

    public void setBloks(Map<String, String[]> blocks) {
        this.blocks = blocks;
    }
    public Map<String, int[]> getBloksPos() {
        return blocksPosition;
    }

    public void setBloksPos(Map<String, int[]> bloksPos) {
        this.blocksPosition = bloksPos;
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
                "typeName='" + typeName + '\'' +
                ", blocks=" + blocks +
                '}';
    }
}
