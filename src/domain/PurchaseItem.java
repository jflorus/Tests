package domain;

/**
 * Created by jovana on 7/11/16.
 */
public class PurchaseItem {

    public String description;
    public String price;
    public String brand;
    public String UPC;

    public PurchaseItem (String [] item) {
        UPC = item[0];
        price = item[1];
        brand = item[2];
        description = item[3];

    }
}
