package ru.otus.java.homeworks.hw11;


import java.util.List;


public class ExampleTree implements SearchTree<Integer> {

    List<Integer> T;
    Tree tree = new Tree();

    public ExampleTree(List<Integer> array) {
        this.T = array;
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
            for (int i = 0; i < T.size() - 1; i++) {
                if (T.get(i) > T.get(i + 1)) {
                    int temp = T.get(i);
                    T.set(i, T.get(i + 1));
                    T.set(i + 1, temp);
                    needSort = true;
                }
            }
        } while (needSort);
        return T;
    }

    public void makeTree() {
        for (int i : T) {
            tree.insertNode(i);
        }
    }
}
