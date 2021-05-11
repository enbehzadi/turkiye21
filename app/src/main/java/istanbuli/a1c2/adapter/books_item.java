package istanbuli.a1c2.adapter;

public class books_item {

    String birdListName;
    int birdListImage;

    public books_item(String birdName,int birdImage)
    {
        this.birdListImage=birdImage;
        this.birdListName=birdName;
    }
    public String getName()
    {
        return birdListName;
    }
    public int getImage()
    {
        return birdListImage;
    }
}

