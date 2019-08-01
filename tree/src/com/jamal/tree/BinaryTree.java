package com.jamal.tree;

public class BinaryTree {

    // 先序遍历，递归实现 先打印本身，再打印左节点，在打印右节点
    public static void preOrder(TreeNode root) {

        if (root == null) {
            return;
        }
        System.out.print(root.data + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    // 中序遍历 先打印左节点，本身，右节点
    public static void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.data + " ");
        inOrder(root.right);
    }

    // 中序遍历 先打印左节点，本身，右节点
    public static void postOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.data + " ");
    }
}
