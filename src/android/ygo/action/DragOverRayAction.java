package android.ygo.action;

import android.ygo.core.Card;
import android.ygo.core.Field;
import android.ygo.core.OverRay;
import android.ygo.core.SelectableItem;
import android.ygo.op.StartDrag;

public class DragOverRayAction extends BaseAction {
    public DragOverRayAction(StartDrag startDrag) {
        super(startDrag);
    }

    @Override
    public void execute() {
        OverRay overRay = (OverRay) item;
        Field field = (Field) container;
        SelectableItem selectableItem;
        if (overRay.topCard().isSelect()) {
            Card overRayTopCard = overRay.removeTopCard();
            selectableItem = overRayTopCard;
            ((StartDrag) operation).setDragItem(overRayTopCard);
            overRay.adjust(field);
        } else {
            selectableItem = overRay;
            field.removeItem();
            ((StartDrag) operation).setDragItem(overRay);
        }
        duel.select(selectableItem, container);
    }
}
