package test;

import Model.ProcessorElements;
import api.AryphmeticTreeProvider;
import api.Processor;
import api.ProcessorExeption;
import api.Tree;
import impl.PipelineProcessor;
import impl.TreeAdapter;

public class ModelTest {

    public static void main(String[] args) {
        //Print tree
        String expression = "a+b*c+d+x*y*s+g/s/k+(z+4+k+5)";
       // String expression = "2*a + 2*b/c/d - 3 - 5*e";
        Tree tree = AryphmeticTreeProvider.provide(expression);
        System.out.println(tree.getRoot());
        System.out.println();


        ProcessorElements elements = new TreeAdapter().adadt(expression);
        Processor processor = null;
        {
            try {
                processor = new PipelineProcessor(3, elements);
            } catch (ProcessorExeption processorExeption) {

                System.out.println(processorExeption.getMessage());
            }
        }
        System.out.println(expression);
        System.out.println();
        processor.complete();
        ((PipelineProcessor) processor).printModel();
    }

}
