package android.ygo.views;

import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.ygo.action.Action;
import android.ygo.action.ActionDispatcher;
import android.ygo.core.*;
import android.ygo.op.MenuClick;
import android.ygo.utils.Configuration;
import android.ygo.utils.Utils;
import android.ygo.views.dueldisk.DuelDiskView;

public class PlayMenuProcessor {

    DuelDiskView view;

    public PlayMenuProcessor(DuelDiskView view) {
        this.view = view;
    }

    public boolean onMenuPrepare(Menu menu) {
        menu.clear();
        Duel duel = view.getDuel();
        SelectableItem item = duel.getCurrentSelectItem();
        Item container = duel.getCurrentSelectItemContainer();

        if (duel.isDuelDisk()) {
            if (container instanceof Field) {
                if (item instanceof OverRay || item instanceof Card) {
                    Card card;
                    if (item instanceof OverRay) {
                        card = ((OverRay) item).topCard();
                    } else {
                        card = (Card) item;
                    }
                    if (!card.isToken()) {
                        menu.add(Const.MENU_GROUP_FIELD_CARD, Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK, 0, "回卡组底");
                        menu.add(Const.MENU_GROUP_FIELD_CARD, Const.MENU_CARD_CLOSE_REMOVE, 0, "里侧除外");
                    }
                } else if (item instanceof CardList) {
                    CardList cardList = (CardList) item;
                    if (cardList.getName().equals(CardList.DECK)) {
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_SHUFFLE, 0, "卡组洗切");
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_CLOSE_REMOVE_TOP, 0, "卡组顶端里侧除外");
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_REVERSE, 0, "卡组翻转");
                    } else if (cardList.getName().equals(CardList.TEMPORARY)) {
                        menu.add(Const.MENU_GROUP_DECK, Const.MENU_DECK_SHUFFLE, 0, "洗切");
                    }
                }
            } else if (container instanceof HandCards) {
                if (item instanceof Card) {
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_CARD_BACK_TO_BOTTOM_OF_DECK, 0, "回卡组底");
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_CARD_CLOSE_REMOVE, 0, "里侧除外");
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_SHOW_HAND, 0, "展示手牌");
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_HIDE_HAND, 0, "覆盖手牌");
                    menu.add(Const.MENU_GROUP_HAND_CARD, Const.MENU_SHUFFLE_HAND, 0, "切洗手牌");
                }
            } else {
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_RESTART, 0, "重新开始");
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_CHANGE_DECK, 0, "更换卡组");
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_SIDE, 0, "换副卡组");
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_CARD_SEARCH, 0, "快速卡查");
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_DECK_BUILDER, 0, "组卡器");
                SubMenu toggleMenu = menu.addSubMenu(Const.MENU_GROUP_MAIN, Const.MENU_TOGGLE, 0, "开关设置");
                toggleMenu.add(Const.MENU_GROUP_MAIN, Const.MENU_GRAVITY_TOGGLE, 0, toggleMenuTxt("重力感应", Configuration.PROPERTY_GRAVITY_ENABLE));
                toggleMenu.add(Const.MENU_GROUP_MAIN, Const.MENU_AUTO_SHUFFLE_TOGGLE, 0, toggleMenuTxt("卡组自动切洗", Configuration.PROPERTY_AUTO_SHUFFLE_ENABLE));
                toggleMenu.add(Const.MENU_GROUP_MAIN, Const.MENU_FPS_TOGGLE, 0, toggleMenuTxt("显示FPS", Configuration.PROPERTY_FPS_ENABLE));
                if (Utils.getSDK() >= 10) {
                    menu.add(Const.MENU_GROUP_MAIN, Const.MENU_MIRROR_DISPLAY, 0, "镜像显示");
                }
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_FEEDBACK, 0, "问题反馈");
                menu.add(Const.MENU_GROUP_MAIN, Const.MENU_EXIT, 0, "退出");
            }
        }
        return true;
    }

    private String toggleMenuTxt(String txt, String toggle) {
        boolean toggleTxt = Configuration.configProperties(toggle);
        return txt + "(" + (toggleTxt ? "开" : "关") + ")";
    }

    public boolean onMenuClick(MenuItem menuItem) {
        Duel duel = view.getDuel();
        MenuClick menuClick = new MenuClick(duel, menuItem);
        Action action = ActionDispatcher.dispatch(menuClick);
        action.execute();
        view.updateActionTime();
        return true;
    }
}
