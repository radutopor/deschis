package ro.deschis.model.menu;

import java.io.Serializable;
import java.util.Map;

public class VariationStep implements Serializable {
    public String name;
    public int displayOrder;
    public Map<String, Variation> variations;
}
