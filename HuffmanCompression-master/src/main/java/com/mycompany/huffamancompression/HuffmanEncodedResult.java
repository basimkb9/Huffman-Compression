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
public class HuffmanEncodedResult {
    
    String encodedData;
    Node root;
        
    public HuffmanEncodedResult(String encodedData, Node root)
    {
        this.encodedData = encodedData;
        this.root = root;
    }
        
    public Node getRoot()
    {
        return this.root;
    }
        
    public String getEncodedData()
    {
        return this.encodedData;
    }
}
