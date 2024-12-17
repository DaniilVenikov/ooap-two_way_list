public class TwoWayList<T> extends ParentList<T> {

    private int leftStatus;

    public static int LEFT_NIL = 0;
    public static int LEFT_OK = 1;
    public static int LEFT_ERR = 2;

    public TwoWayList() {
        super();
    }

    //команды

    //предусловие: есть елемент справа
    //постусловие: курсор указывает на элемент справа от предыдущего
    public void left() {
        if (isValue() && getCursor().prev != null) {
            setCursor(getCursor().prev);
            leftStatus = LEFT_OK;
        } else {
            leftStatus = LEFT_ERR;
        }
    }

    //запросы
    public int getLeftStatus() {
        return leftStatus;
    }

    public void clear() {
        super.clear();
        leftStatus = LEFT_NIL;
    }

}
