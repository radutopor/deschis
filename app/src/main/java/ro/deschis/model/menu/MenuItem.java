package ro.deschis.model.menu;

import java.util.Map;

public class MenuItem {
    public String name;
    public int displayOrder;
    public String description;
    public String image;
    public float price;
    public Map<String, Boolean> extras;
    public Map<String, VariationStep> variationSteps;
}
