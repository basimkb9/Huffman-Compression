/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.huffamancompression;

/**
 *
 * @author Basim Ahmed Siddiqui
 */
    public class Node implements Comparable<Node>
    {
        public char character;
        public int frequency;
        Node right;
        Node left;
        
        public Node(char character, int frequency, Node left, Node right)
        {
            this.character = character;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }
        
        boolean isLeaf()
        {
            return this.left == null && this.right == null;
        }

        @Override
        public int compareTo(Node o) {
            
            int frequencyCoparision = Integer.compare(this.frequency, o.character);
            if(frequencyCoparision != 0)
            {
                return frequencyCoparision;
            }
            return Integer.compare(o.frequency, o.character);
        }
    }