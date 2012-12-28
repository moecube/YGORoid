package android.ygo.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.ygo.utils.Utils;

public class Card implements Item {
    public static final String DEFAULT_CARD_PROTECTOR = "defaultProtector";

    String id;
    String name;
    String desc;
    CardType type;
    String cardProtector;
    boolean set = false;
    boolean positive = true;

    public Card(String id, CardType type, boolean positive, boolean set) {
        this.id = id;
        this.type = type;
        this.positive = positive;
        this.set = set;
        cardProtector = DEFAULT_CARD_PROTECTOR;
    }

    @Override
    public Bitmap toBitmap() {
        int height = Utils.cardHeight();
        int width = height;
        Bitmap cardBmp = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cardBmp);
        Paint paint = new Paint();
        if (set) {
            Bitmap protector = Utils.readBitmapScaleByHeight(cardProtector + ".png", height);
            int drawX = (width - protector.getWidth()) / 2;
            canvas.drawBitmap(protector, drawX, 0, paint);
            protector.recycle();
        } else {
            if(Configuration.isTotalCardPic()) {
                Bitmap cardPic = Utils.readBitmapScaleByHeight(id + ".png", height / 2);
                int drawX = (width - cardPic.getWidth()) / 2;
                canvas.drawBitmap(cardPic, drawX, 0, paint);
                cardPic.recycle();
            } else {
                Bitmap cardBackground = Utils.readBitmapScaleByHeight(type.toString() + ".png", height);
                int drawX = (width - cardBackground.getWidth()) / 2;
                canvas.drawBitmap(cardBackground, drawX, 0, paint);
                cardBackground.recycle();
                Bitmap cardPic = Utils.readBitmapScaleByHeight(id + ".png", height / 2);
                int cardPicX = (width - cardPic.getWidth()) / 2;
                int cardPicY = (int) (cardBackground.getHeight() / 4.63);
                canvas.drawBitmap(cardPic, cardPicX, cardPicY, paint);
                cardPic.recycle();
            }
        }
        if (!positive) {
            cardBmp = Utils.rotate(cardBmp, 90);
        }
        return cardBmp;
    }
}
