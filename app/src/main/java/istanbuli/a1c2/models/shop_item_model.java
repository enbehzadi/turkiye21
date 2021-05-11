package istanbuli.a1c2.models;

public class shop_item_model {

    public String Titel_Item;
    public String Description_Item;
    public String Price_Item;
    public String Image_Item;
    public String Id_Item;
    // Constructor.
    public shop_item_model(String Titel_Item, String Description_Item, String Image_Item,String Id_Item,String Price) {

        this.Titel_Item = Titel_Item;
        this.Description_Item = Description_Item;
        this.Image_Item = Image_Item;
        this.Id_Item = Id_Item;
        this.Price_Item = Price;

    }

    public String get_Titel_Item() {
        return Titel_Item;
    }

    public String get_Description_Item() {
        return Description_Item;
    }

    public String get_Price_Item() {
        return Price_Item;
    }

    public String get_Image_Item() {
        return Image_Item;
    }

    public String get_Image_Id_Item() {
        return Id_Item;
    }



    public void set_Titel_Item(String Titel_Item) {
       this.Titel_Item=Titel_Item;
    }

    public void set_Description_Item(String Description_Item) {
        this.Description_Item=Description_Item;
    }
    public void set_Image_Item(String Image_Item) {
        this.Image_Item=Image_Item;
    }
    public void set_Price_Item(String Price_Item) {
        this.Price_Item=Price_Item;
    }
    public void set_Image_Id_Item(String Id_Item) {
        this.Id_Item=Id_Item;
    }



}

