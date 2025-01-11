package ru.otus.java.homeworks.hw11;


import java.util.List;


public class ExampleTree implements SearchTree<Integer> {

    List<Integer> array;
    Tree tree = new Tree();

    public ExampleTree(List<Integer> array) {
        this.array = array;
    }

    @Override
    public Integer find(Integer element) {
        Node foundNode = tree.findNodeByValue(element);
        if (foundNode == null) {
            return null;
        }
        return foundNode.getValue();
    }

    @Override
    public List<Integer> getSortedList() {
        boolean needSort;
        do {
            needSort = false;
            for (int i = 0; i < array.size() - 1; i++) {
                if (T.get(i) > array.get(i + 1)) {
                    int temp = array.get(i);
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);
                    needSort = true;
                }
            }
        } while (needSort);
        return array;
    }

    public void makeTree() {
        for (int i : array) {
            tree.insertNode(i);
        }
    }
}
