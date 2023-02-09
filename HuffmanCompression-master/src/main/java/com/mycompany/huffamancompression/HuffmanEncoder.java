/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.huffamancompression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Basim Ahmed Siddiqui
 */
public class HuffmanEncoder {
    
    final static int ALPHABET_SIZE = 256;
    
    public HuffmanEncodedResult compress(String data)
    {
        int[] frequency = buildFrequencyTable(data);
        Node root = buildHuffmanTree(frequency);
        Map<Character,String> lookupTable = buildLookupTable(root);
        
        return new HuffmanEncodedResult(generateEncodedData(data, lookupTable), root);
    }
    
    public static String generateEncodedData(String data, Map<Character,String> lookupTable)
    {
        StringBuilder builder = new StringBuilder();
        
        for(char character : data.toCharArray())
        {
            builder.append(lookupTable.get(character));
        }
        return builder.toString();
    }
    
    public String decompress(HuffmanEncodedResult result)
    {
        StringBuilder resultBuilder = new StringBuilder();
        Node current = result.getRoot();
        int i = 0;
        while(i < result.getEncodedData().length())
        {
            while(!current.isLeaf())
            {
                char bit = result.getEncodedData().charAt(i);
                if(bit == '1')
                {
                    current = current.right;
                }
                else if(bit == '0')
                {
                    current = current.left;
                }
                else
                {
                    throw new IllegalArgumentException("Invalid bit in message" + bit);
                }
                i++;
            }
            resultBuilder.append(current.character);
            current = result.getRoot();
        }
        
        return resultBuilder.toString();
    }
    
    //Counts charachter's frequency and return an array. 
    static int[] buildFrequencyTable(String data)
    {
        int[] frequency = new int[ALPHABET_SIZE];
        for(char character : data.toCharArray())
        {
            frequency[character]++;
        }   
        return frequency;
    }
    
    static Node buildHuffmanTree(int[] frequency)
    {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        
        for(char i = 0; i < ALPHABET_SIZE; i++)
        {
            if(frequency[i] > 0)
            {
                priorityQueue.add(new Node(i,frequency[i],null,null));
            }
        }
        
        if(priorityQueue.size() == 1)
        {
            priorityQueue.add(new Node('\0',1,null,null));
        }
        
        while(priorityQueue.size() > 1)
        {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();
            Node parent = new Node('\0', left.frequency + right.frequency,left,right);
            priorityQueue.add(parent);
        }
        
        return priorityQueue.poll();
    }
    
    static void buildLookupTableImpl(Node node, String s, Map<Character, String> lookupTable) {
        
        if(!node.isLeaf())
        {
            buildLookupTableImpl(node.left, s + '0',lookupTable);
            buildLookupTableImpl(node.right, s + '1',lookupTable);
        }
        else
        {
            lookupTable.put(node.character, s);
        }
    }
    static Map<Character, String> buildLookupTable(Node root)
    {
        Map<Character, String> lookupTable = new HashMap<>();
        
        buildLookupTableImpl(root, "", lookupTable);
        
        return lookupTable;
    }
    
    public static void main(String args[])
    {
        String data = "La La Land";
        
        HuffmanEncoder encoder = new HuffmanEncoder();
        HuffmanEncodedResult result = encoder.compress(data);
        System.out.println("Original Data: " + data);
        System.out.println("Encoded Data: " + result.encodedData);
        System.out.println("Decoded Data: " + encoder.decompress(result));
    }
}
