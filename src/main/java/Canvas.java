import java.util.Map;

public class Canvas {
    private String name;
    private Map<String, String[]> bloks;
    private Map<String, int[]> bloksPos;
    private int x;
    private int y;


    public Canvas() {
        name = "";
        bloks = null;
        bloksPos = null;
        x = 0;
        y = 0;
    }

    public Canvas(String name, Map<String, String[]> bloks, Map<String, int[]> bloksPos, int x, int y) {
        this.name = name;
        this.bloks = bloks;
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
        return bloks;
    }

    public void setBloks(Map<String, String[]> bloks) {
        this.bloks = bloks;
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
        return "Canvas{" +
                "name='" + name + '\'' +
                ", bloks=" + bloks +
                '}';
    }
}
