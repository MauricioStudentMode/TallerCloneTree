/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Modelo.Solution;
import Modelo.TreeNode;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class CrearCasos {

    public static void main(String[] args) {
        Random random = new Random();
        LinkedList<TreeNode> treeList = new LinkedList<>();
        LinkedList<TreeNode> targetNodes = new LinkedList<>();
        LinkedList<StringBuilder> input = new LinkedList<>();
        LinkedList<StringBuilder> output = new LinkedList<>();
        boolean sello = true;

        for (int i = 0; i < 25; i++) {
            TreeNode root = null;
            int n = random.nextInt(100);
            StringBuilder caso = new StringBuilder("[");
            
            for (int j = 1; j < n; j++) {
                int value = j;
                root = insert(root, value, caso);
            }
            
            treeList.add(root);

            TreeNode target = getTargetNode(root, sello ? 1 : n, sello ? n : n * 2);
            targetNodes.add(target);

            caso.append("]\n")
                .append(target != null ? target.val : "Nodo objetivo no encontrado");
            input.add(caso);

            sello = !sello;
        }

        guardarListaEnArchivo(input, "input");

        Solution solution = new Solution();

        for (int i = 0; i < treeList.size(); i++) {
            TreeNode original = treeList.get(i);
            TreeNode cloned = original;
            TreeNode target = targetNodes.get(i);

            TreeNode result = solution.getTargetCopy(original, cloned, target);

            StringBuilder val = new StringBuilder(result != null ? result.val + "" : "Nodo objetivo no encontrado");
            output.add(val);
        }

        guardarListaEnArchivo(output, "output");
    }

    public static TreeNode insert(TreeNode root, int value, StringBuilder caso) {
        if (root == null) {
            caso.append(value).append(" ");
            return new TreeNode(value);
        }

        if (value < root.val) {
            root.left = insert(root.left, value, caso);
        } else if (value > root.val) {
            root.right = insert(root.right, value, caso);
        }

        return root;
    }

    public static TreeNode getTargetNode(TreeNode root, int origin, int bound) {
        int targetValue = new Random().nextInt(origin, bound);

        if (root == null) {
            return null;
        }

        return findTargetNode(root, targetValue);
    }

    private static TreeNode findTargetNode(TreeNode node, int targetValue) {
        if (node == null) {
            return null;
        }

        if (node.val == targetValue) {
            return node;
        }

        TreeNode leftResult = findTargetNode(node.left, targetValue);
        if (leftResult != null) {
            return leftResult;
        }

        return findTargetNode(node.right, targetValue);
    }

    public static void guardarListaEnArchivo(LinkedList<StringBuilder> lista, String fileName) {
        try {
            File outputFile = new File(fileName + ".txt");
            FileWriter fileWriter = new FileWriter(outputFile);

            for (StringBuilder cadena : lista) {
                fileWriter.write(cadena + "\n");
            }

            fileWriter.close();
            System.out.println("La lista se ha guardado correctamente en el archivo " + fileName + ".txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

