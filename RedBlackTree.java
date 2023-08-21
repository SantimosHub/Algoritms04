
public class RedBlackTree {
    public static void main(String[] args) {
        RBTree tree = new RBTree();
        tree.push(5);
        tree.push(3);
        tree.push(7);
        tree.push(2);
        tree.push(4);
        tree.push(6);
        tree.push(8);
        System.out.println(tree.find(8));
        System.out.println(tree.find(10));
    }
}
class RBTree {
    private Node root;

    public enum Color {
        RED, BLACK
    }

    private class Node {
        int value;
        Color color;
        Node leftChild;
        Node rightChild;
    }

    private boolean isRed(Node node) {
        return node != null && node.color == Color.RED;
    }

    private Node leftTurn(Node node) {
        Node newRoot = node.rightChild;
        node.rightChild = newRoot.leftChild;
        newRoot.leftChild = node;
        newRoot.color = node.color;
        node.color = Color.RED;
        return newRoot;
    }

    private Node rightTurn(Node node) {
        Node newRoot = node.leftChild;
        node.leftChild = newRoot.rightChild;
        newRoot.rightChild = node;
        newRoot.color = node.color;
        node.color = Color.RED;
        return newRoot;
    }

    private void colorReplacement(Node node) {
        node.color = Color.RED;
        node.leftChild.color = Color.BLACK;
        node.rightChild.color = Color.BLACK;
    }

    private Node push(Node node, int value) {
        if (node == null) {
            Node newNode = new Node();
            newNode.value = value;
            newNode.color = Color.RED;
            return newNode;
        }

        if (value < node.value) {
            node.leftChild = push(node.leftChild, value);
        } else if (value > node.value) {
            node.rightChild = push(node.rightChild, value);
        } else {
            return node; // Элемент уже существует
        }

        if (isRed(node.rightChild) && !isRed(node.leftChild)) {
            node = leftTurn(node);
        }
        if (isRed(node.leftChild) && isRed(node.leftChild.leftChild)) {
            node = rightTurn(node);
        }
        if (isRed(node.leftChild) && isRed(node.rightChild)) {
            colorReplacement(node);
        }

        return node;
    }

    public void push(int value) {
        root = push(root, value);
        root.color = Color.BLACK;
    }

    boolean find(int value) {
        Node node = root;
        while (node != null) {
            if (node.value == value) {
                return true;
            }
            if (node.value < value) {
                node = node.rightChild;
            } else {
                node = node.leftChild;
            }
        }
        return false;
    }
}