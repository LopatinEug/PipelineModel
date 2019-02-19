package impl;

import Model.ProcessorElements;
import Model.Task;
import api.AryphmeticTreeProvider;
import api.Node;
import api.Tree;
import model.Operation;
import model.Sign;


public class TreeAdapter {

    ProcessorElements processorElements;

    public ProcessorElements adadt(String expression){
        Tree tree=AryphmeticTreeProvider.provide(expression);
        processorElements=new ProcessorElements();

        int length=0;
        Node current=tree.getRoot();

        fillElelments(current,length);


        return processorElements;
    }


    private void fillElelments(Node node,int length){
        length++;
        if(node.getLeft().getElement() instanceof Operation || node.getLeft().getElement() instanceof Sign){
            fillElelments(node.getLeft(),length);
        }
        if(node.getRight().getElement() instanceof Operation || node.getRight().getElement() instanceof Sign){
            fillElelments(node.getRight(),length);
        }

        length--;

        int leftP;
        String leftV;
        int rightP;
        String rightV;

        if(node.getLeft().getElement() instanceof Operation || node.getLeft().getElement() instanceof Sign){
            leftP=node.getLeft().getElement().getPosition();
            leftV="[ "+node.getLeft().getElement().getPosition()+" ]";
        }else{
            leftP=-1;
            leftV=node.getLeft().getElement().getValue();
        }

        if(node.getRight().getElement() instanceof Operation || node.getRight().getElement() instanceof Sign){
            rightP=node.getRight().getElement().getPosition();
            rightV="[ "+node.getRight().getElement().getPosition()+" ]";
        }else{
            rightP=-1;
            rightV=node.getRight().getElement().getValue();
        }

        int p=node.getElement().getPosition();
        String v=node.getElement().getValue();

        Task task = new Task(length,leftP,rightP,p,leftV,rightV,v);
        processorElements.add(task);
    }
}
