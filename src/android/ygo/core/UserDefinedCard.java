package android.ygo.core;

import android.graphics.Bitmap;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;

public class UserDefinedCard extends Card {
    private String fileName;
    public UserDefinedCard(String name) {
        super("0", name, "");
        fileName = Configuration.userDefinedCardImgPath() + name + Configuration.cardImageSuffix();
    }

    @Override
    public Bitmap bmp(int width, int height) {
        Bitmap bmp = Utils.readBitmapScaleByHeight(fileName, height);
        if(bmp == null) {
            bmp = super.bmp(width, height);
        }
        return bmp;
    }

    @Override
    public UserDefinedCard clone() {
        UserDefinedCard userDefinedCard = new UserDefinedCard(name);
        userDefinedCard.fileName = fileName;
        return userDefinedCard;
    }
}
