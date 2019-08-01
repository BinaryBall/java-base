package com.jamal.tree;


public class TreeTest {

    public static TreeNode buildTree() {

        // 创建测试用的二叉树
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        TreeNode t5 = new TreeNode(5);
        TreeNode t6 = new TreeNode(6);
        TreeNode t7 = new TreeNode(7);
        TreeNode t8 = new TreeNode(8);

        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t4.right = t7;
        t3.left = t5;
        t3.right = t6;
        t6.left = t8;

        return t1;
    }


    public static void main(String[] args) {
        TreeNode treeNode = buildTree();
        // 预计结果: 1 2 4 7 3 5 6 8
        BinaryTree.preOrder(treeNode);
        System.out.println();
        // 预计结果:4 7 2 1 5 3 8 6
        BinaryTree.inOrder(treeNode);
        System.out.println();
        // 预计结果: 7 4 2 5 8 6 3 1
        BinaryTree.postOrder(treeNode);
        System.out.println();
        int[] a = { 62, 88, 58, 47, 35, 73, 51, 99, 37, 93, 36, 39, 42, 62 };
        BinarySearchTree binarySortTree = new BinarySearchTree();
        for (int i = 0; i < a.length; i++) {
            binarySortTree.insert(a[i]);
        }
        System.out.println(binarySortTree.findMax().data);
        System.out.println(binarySortTree.find(62));
        binarySortTree.InOrderTraverse(binarySortTree.tree);
    }
}
