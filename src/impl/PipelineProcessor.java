package impl;

import Model.ProcessorElements;
import Model.Task;
import api.Layer;
import api.Processor;
import api.ProcessorExeption;

import java.util.ArrayList;

public class PipelineProcessor implements Processor {
    private int number;
    private ArrayList<Layer> layers;
    private ArrayList<Integer> times;
    private ArrayList<StringBuilder> info;
    private int time;
    private ProcessorElements elements;
    private int count;
    private boolean finished;
    private int tauPlus=1;
    private int tauMul=2;
    private int tauDiv=3;
    private int maxTau;
    private int fullTime;


    private Layer first;
    private Layer last;

    public PipelineProcessor(int tact, ProcessorElements elements) throws ProcessorExeption {
        if (tact < 2) throw new ProcessorExeption("Wrong tact number");
        number = tact;
        this.elements = elements;
        layers = new ArrayList<>();
        times = new ArrayList<>();
        info = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Layer layer = new LayerImpl(i);
            if (i == 0) first = layer;
            if (i == number - 1) last = layer;
            layers.add(layer);
            times.add(0);
            info.add(new StringBuilder(i + " layer : "));
        }
        time = 0;
        finished = false;
        count = elements.getQueue().size() + elements.getWaiting().size();
        fullTime=0;
        for (int i = 0; i <elements.getQueue().size() ; i++) {
            if(elements.getQueue().get(i).getValue().equals("*")){
                fullTime+=tauMul*number;
            }else if(elements.getQueue().get(i).getValue().equals("/")){
                fullTime+=tauDiv*number;
            }else {
                fullTime+=tauPlus*number;
            }
        }

        for (Task t:elements.getWaiting()) {
            if(t.getValue().equals("*")){
                fullTime+=tauMul*number;
            }else if(t.getValue().equals("/")){
                fullTime+=tauDiv*number;
            }else {
                fullTime+=tauPlus*number;
            }
        }
        maxTau=Math.max(tauDiv,Math.max(tauMul,tauPlus));
    }

    @Override
    public void step() {

        Task taskin = null;
        int statin = 0;
      /*  Task taskout = null;
        int statout = 0;*/
        for (int i = number-1; i >= 0; i--) {
            LayerImpl current = (LayerImpl) layers.get(i);

            //out
            if (current.equals(last)) {

                LayerImpl past = (LayerImpl) layers.get(i - 1);
                if (past.getStatus() == -1) {
                    finished = true;
                    break;
                }
                if (current.getStatus() > 0) {
                    elements.setCompleted(current.out());
                }
            }


            //in
            if (current.equals(first)) {
                if (elements.isReady()) {
                    times.set(i, times.get(i) + 1);
                    Task task1 = elements.getNext();
                    current.setTask(task1);
                    current.in(task1.getPostion());
                } else if (elements.isFinished()) {
                    current.setTask(null);
                    current.in(-1);
                } else {
                    current.setTask(null);
                    current.in(0);
                }
            } else {

                LayerImpl past = (LayerImpl) layers.get(i - 1);

                if (past.getStatus() > 0) {
                    times.set(i, times.get(i) + 1);
                    current.in(past.getStatus());
                    current.setTask(past.out());
                } else {
                    current.in(past.getStatus());
                    current.setTask(null);
                }
            }
            info.get(i).append(current.getStatus() + "   ");
        }
    }

    @Override
    public void complete() {
        printInfo();
        while (!finished) {
            step();
            time++;

        }
    }

    public void printInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ready " + elements.getQueue().size() + " tasks \n");
        if (!elements.getQueue().isEmpty()) {
            for (Task task : elements.getQueue()) {
                sb.append(task.getPostion() + ": " + task.getLeft() + task.getValue() + task.getRight() + "\n");
            }
        }

        sb.append("\n");
        sb.append("Don't ready " + elements.getWaiting().size() + " tasks \n");
        if (!elements.getWaiting().isEmpty()) {
            for (Task task : elements.getWaiting()) {
                sb.append(task.getPostion() + ": " + task.getLeft() + task.getValue() + task.getRight() + "\n");
            }
        }

        sb.append("\n");
        sb.append("On pipeline now " + elements.getWorking().size() + " tasks \n");
        if (!elements.getWorking().isEmpty()) {
            for (Task task : elements.getWorking()) {
                sb.append(task.getPostion() + ": " + task.getLeft() + task.getValue() + task.getRight() + "\n");
            }
        }

        sb.append("\n");
        sb.append("Completed " + elements.getCompleted().size() + " tasks \n");
        if (!elements.getCompleted().isEmpty()) {
            for (Task task : elements.getCompleted()) {
                sb.append(task.getPostion() + ": " + task.getLeft() + task.getValue() + task.getRight() + "\n");
            }
        }

        System.out.println(sb);
    }

    public void printModel() {
        for (StringBuilder sb : info) {
            System.out.println(sb);
        }
        System.out.println();
        System.out.println("Full time on linear processor =" + fullTime);
        System.out.println("Full time on linear processor with static tact =" + number * count * maxTau);
        System.out.println("Full time of Pipeline with static tact =" + (time - 1)*maxTau );
        System.out.println("Time loss =" + ((double) fullTime/(time - 1)/maxTau));
        System.out.println("Time loss with static tact=" + ((double) (number * count * maxTau)/(time - 1)/maxTau));

        System.out.println("Proccess time =" + times.get(0)*maxTau);
        System.out.println("Percent of work =" + ((double) times.get(0) / (time - 1 )));

    }
}
