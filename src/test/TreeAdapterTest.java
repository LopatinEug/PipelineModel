package test;

import Model.ProcessorElements;
import impl.TreeAdapter;

public class TreeAdapterTest {
    public static void main(String[] args) {
        ProcessorElements elements = new TreeAdapter().adadt("a+b-(c1-c2-c3-c4)+d-e-f+(g1+g2+g3+g4)-h");
        System.out.println();
    }

}
