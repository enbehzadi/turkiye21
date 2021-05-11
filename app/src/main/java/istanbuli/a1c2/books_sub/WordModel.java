package istanbuli.a1c2.books_sub;

/**
 * Created by anupamchugh on 10/12/15.
 */
public class WordModel {

    public String tr;
    public String fa;
    public String en;
    // Constructor.
    public WordModel(String tr, String fa,String en) {

        this.tr = tr;
        this.fa = fa;
        this.en = en;
    }

    public String get_Tr() {
        return tr;
    }

    public String get_Fa() {
        return fa;
    }

    public String get_En() {
        return en;
    }
}
