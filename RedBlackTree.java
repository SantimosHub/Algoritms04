
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
    class Node {
        int value;
        boolean isRed;
        Node left;
        Node right;
        Node(int value) {
            this.value = value;
            this.isRed = true;
        }
    }
    Node root;
    boolean push(int value) {
        if (root == null) {
            root = new Node(value);
            root.isRed = false;
            return true;
        } else {
            Node parent = null;
            Node node = root;
            while (node != null) {
                if (node.value == value) {
                    return false;
                }
                parent = node;
                if (node.value < value) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            }
            Node newNode = new Node(value);
            if (parent.value < value) {
                parent.right = newNode;
            } else {
                parent.left = newNode;
            }
            balanceAfterInsert(newNode, parent);
            return true;
        }
    }
    void balanceAfterInsert(Node node, Node parent) {
        while (parent != null && parent.isRed) {
            Node grandParent = findGrandParent(node, parent);
            Node uncle = findUncle(node, parent, grandParent);
            if (uncle != null && uncle.isRed) {
                parent.isRed = false;
                uncle.isRed = false;
                grandParent.isRed = true;
                node = grandParent;
                parent = findParent(node);
            } else {
                if (parent == grandParent.left) {
                    if (node == parent.right) {

                        rotateLeft(parent);

                        node = parent;
                        parent = findParent(node);
                    }
                    rotateRight(grandParent);
                    grandParent.isRed = true;
                    parent.isRed = false;
                } else {
                    if (node == parent.left) {

                        rotateRight(parent);

                        node = parent;
                        parent = findParent(node);
                    }
                    rotateLeft(grandParent);
                    grandParent.isRed = true;
                    parent.isRed = false;
                }
            }
        }
        root.isRed = false;
    }

    Node findParent(Node node) {
        if (node == null || node == root) {
            return null;
        }
        Node parent = root;
        while (parent != null) {
            if (parent.left == node || parent.right == node) {
                return parent;
            }
            if (parent.value < node.value) {
                parent = parent.right;
            } else {
                parent = parent.left;
            }
        }
        return null;
    }
    Node findGrandParent(Node node, Node parent) {
        Node grandParent = null;
        if (parent != null) {
            grandParent = findParent(parent);
        }
        return grandParent;
    }
    Node findUncle(Node node, Node parent, Node grandParent) {
        if (grandParent != null) {
            if (grandParent.left == parent) {
                return grandParent.right;
            } else {
                return grandParent.left;
            }
        }
        return null;
    }
    void rotateLeft(Node node) {
        if (node != null) {
            Node rightChild = node.right;
            if (rightChild != null) {
                node.right = rightChild.left;
                rightChild.left = node;
                if (node == root) {
                    root = rightChild;
                }
            }
        }
    }
    void rotateRight(Node node) {
        if (node != null) {
            Node leftChild = node.left;
            if (leftChild != null) {
                node.left = leftChild.right;
                leftChild.right = node;
                if (node == root) {
                    root = leftChild;
                }
            }
        }
    }
    boolean find(int value) {
        Node node = root;
        while (node != null) {
            if (node.value == value) {
                return true;
            }
            if (node.value < value) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return false;
    }
}