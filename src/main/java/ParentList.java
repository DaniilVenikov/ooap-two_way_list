public abstract class ParentList<T> {

    private Node<T> head;
    private Node<T> tail;
    private Node<T> cursor;

    private int headStatus;
    private int tailStatus;
    private int rightStatus;
    private int putRightStatus;
    private int putLeftStatus;
    private int removeStatus;
    private int addToEmptyStatus;
    private int replaceStatus;
    private int findStatus;
    private int getStatus;

    public static int HEAD_NIL = 0;
    public static int HEAD_OK = 1;
    public static int HEAD_ERR = 2;

    public static int TAIL_NIL = 0;
    public static int TAIL_OK = 1;
    public static int TAIL_ERR = 2;

    public static int RIGHT_NIL = 0;
    public static int RIGHT_OK = 1;
    public static int RIGHT_ERR = 2;

    public static int PUT_RIGHT_NIL = 0;
    public static int PUT_RIGHT_OK = 1;
    public static int PUT_RIGHT_ERR = 2;

    public static int PUT_LEFT_NIL = 0;
    public static int PUT_LEFT_OK = 1;
    public static int PUT_LEFT_ERR = 2;

    public static int REMOVE_NIL = 0;
    public static int REMOVE_OK = 1;
    public static int REMOVE_ERR = 2;

    public static int ADD_TO_EMPTY_OK = 1;
    public static int ADD_TO_EMPTY_ERR = 2;

    public static int REPLACE_NIL = 0;
    public static int REPLACE_OK = 1;
    public static int REPLACE_ERR = 2;

    public static int FIND_NIL = 0;
    public static int FIND_OK = 1;
    public static int FIND_ERR = 2;

    public static int GET_NIL = 0;
    public static int GET_OK = 1;
    public static int GET_ERR = 2;

    //команды

    //предусловие: список не пустой
    //постусловие: курсор указывает на первый узел
    public void head() {
        if (head != null) {
            cursor = head;
            headStatus = HEAD_OK;
        } else {
            headStatus = HEAD_ERR;
        }
    }

    //предусловие: список не пустой
    //постусловие: курсор указывает на последний узел
    public void tail() {
        if (tail != null) {
            cursor = tail;
            tailStatus = TAIL_OK;
        } else {
            tailStatus = TAIL_ERR;
        }
    }

    //предусловие: есть елемент справа
    //постусловие: курсор указывает на элемент справа от предыдущего
    public void right() {
        if (isValue()) {
            cursor = cursor.next;
            rightStatus = RIGHT_OK;
        } else {
            rightStatus = RIGHT_ERR;
        }
    }

    //предусловие: список не пустой
    //постусловие: новый элемент вставлен следом за текущим узлом
    public void putRight(T value) {
        if(isValue()) {
            Node<T> newNode = new Node<>(value);
            newNode.prev = cursor;
            newNode.next = cursor.next;

            if (cursor.next != null) {
                cursor.next.prev = newNode;
            } else {
                tail = newNode;
            }
            cursor.next = newNode;
            putRightStatus = PUT_RIGHT_OK;
        } else {
            putRightStatus = PUT_RIGHT_ERR;
        }
    }

    //предусловие: список не пустой
    //постусловие: новый элемент вставлен перед текущим узлом
    public void putLeft(T value) {
        if(isValue()) {
            Node<T> newNode = new Node<>(value);
            newNode.next = cursor;
            newNode.prev = cursor.prev;
            if (cursor.prev != null) {
                cursor.prev.next = newNode;
            } else {
                head = newNode;
            }
            cursor.prev = newNode;
            putLeftStatus = PUT_LEFT_OK;
        } else {
            putLeftStatus = PUT_LEFT_ERR;
        }
    }

    //предусловие: список не пустой
    //постусловие:курсор смещается к правому соседу, если он есть,в противном случае курсор смещается к левому соседу,
    //если он есть
    public void remove() {
        if (!isValue()) {
            removeStatus = REMOVE_ERR;
            return;
        }

        if (cursor.next != null) {
            cursor.next.prev = cursor.prev;
        } else {
            tail = cursor.prev;
        }

        if (cursor.prev != null) {
            cursor.prev.next = cursor.next;
        } else {
            head = cursor.next;
        }

        cursor = (cursor.next != null) ? cursor.next : cursor.prev;
        removeStatus = REMOVE_OK;
    }

    //постусловие: из списка удаляются все значения
    public void clear() {
        this.head = null;
        this.tail = null;
        this.cursor = null;

        headStatus = HEAD_NIL;
        tailStatus = TAIL_NIL;
        rightStatus = RIGHT_NIL;
        putRightStatus = PUT_RIGHT_NIL;
        putLeftStatus = PUT_LEFT_NIL;
        removeStatus = REMOVE_NIL;
        replaceStatus = REPLACE_NIL;
        findStatus = FIND_NIL;
        getStatus = GET_NIL;

    }


    //предусловие: список пустой
    //постусловие: в список добавлен первый элемент
    public void addToEmpty(T value) {
        if (head == null) {
            Node<T> newNode = new Node<>(value);
            head = newNode;
            tail = newNode;
            cursor = newNode;
            addToEmptyStatus = ADD_TO_EMPTY_OK;
        } else {
            addToEmptyStatus = ADD_TO_EMPTY_ERR;
        }
    }

    //постусловие: добавлен новый узел в конец списка
    public void addTail(T value) {
        if (tail != null) {
            Node<T> newNode = new Node<>(value);
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        } else {
            addToEmpty(value);
        }
    }

    //предусловие: список не пустой
    //постусловие: значение текущего узла заменено на заданное
    public void replace(T value) {
        if (isValue()) {
            cursor.value = value;
            replaceStatus = REPLACE_OK;
        } else {
            replaceStatus = REPLACE_ERR;
        }
    }

    //постусловие: курсор установлен на следующий узел с искомым значением, если такой узел найден
    public void find(T value) {
        Node<T> current = cursor;
        while (current != null) {
            if (current.value.equals(value)) {
                cursor = current;
                findStatus = FIND_OK;
                return;
            }
            current = current.next;
        }
        findStatus = FIND_ERR;
    }

    //постусловие: из списка удаляться все узлы с заданным значением
    public void removeAll(T value) {
        if (head == null) {
            return;
        }

        Node<T> node = head;
        while (node != null) {
            if (node.value.equals(value)) {
                if (node == head) {
                    head = node.next;
                    if (head != null) {
                        head.prev = null;
                    }
                } else {
                    node.prev.next = node.next;
                }

                if (node == tail) {
                    tail = node.prev;
                    if (tail != null) {
                        tail.next = null;
                    }
                } else {
                    node.next.prev = node.prev;
                }

                if (node == cursor) {
                    cursor = node.next != null ? node.next : node.prev;
                }
            }
            node = node.next;
        }
    }

    public void setCursor(Node<T> node) {
        cursor = node;
    }


    //запросы

    //предусловие: список не пустой
    public T get() {
        if (cursor != null) {
            getStatus = GET_OK;
            return cursor.value;
        } else {
            getStatus = GET_ERR;
            return null;
        }
    }

    public Node<T> getCursor() {
        return  cursor;
    }


    public int size() {
        int result = 0;
        Node<T> current = head;
        while (current != null) {
            result++;
            current = current.next;
        }
        return result;
    }

    public boolean isHead() {
        return cursor == head;
    }
    public boolean isTail() {
        return cursor == tail;
    }
    public boolean isValue() {
        return cursor != null;
    }

    public int getHeadStatus() {
        return headStatus;
    }
    public int getTailStatus() {
        return tailStatus;
    }
    public int getRightStatus() {
        return rightStatus;
    }
    public int getPutRightStatus() {
        return putRightStatus;
    }
    public int getPutLeftStatus() {
        return putLeftStatus;
    }
    public int getRemoveStatus() {
        return removeStatus;
    }
    public int getAddToEmptyStatus() {
        return addToEmptyStatus;
    }
    public int getReplaceStatus() {
        return replaceStatus;
    }
    public int getFindStatus() {
        return findStatus;
    }
    public int getGetStatus() {
        return getStatus;
    }
}

class Node<T>
{
    public T value;
    public Node<T> next;
    public Node<T> prev;

    public Node(T _value)
    {
        value = _value;
        next = null;
        prev = null;
    }
}
