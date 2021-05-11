package istanbuli.a1c2.books_sub;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class SentenceModel {

    public String Titel_Image_fa;
    public String Titel_Image_en;
    public String Image_Name;
    public String Cat_Id;
    // Constructor.
    public SentenceModel(String imageName, String titelImage_fa, String titelImage_en, String Cat_Id) {

        this.Image_Name = imageName;
        this.Titel_Image_fa = titelImage_fa;
        this.Titel_Image_en = titelImage_en;
        this.Cat_Id = Cat_Id;
    }

    public String get_Image_Titel_fa() {
        return Titel_Image_fa;
    }
    public String get_Image_Titel_en() {
        return Titel_Image_en;
    }

    public String get_Image_Name() {
        return Image_Name;
    }

    public String get_Cat_Id() {
        return Cat_Id;
    }
}
