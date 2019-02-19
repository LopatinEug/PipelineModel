package Model;

import Model.Task;

import java.util.ArrayList;
import java.util.HashSet;

public class ProcessorElements {
    private ArrayList<Task> queue;
    private HashSet<Task> waiting;
    private HashSet<Task> completed;
    private HashSet<Task> working;

    public HashSet<Task> getWorking() {
        return working;
    }

    public ProcessorElements() {
        queue = new ArrayList<>();
        waiting = new HashSet<>();
        completed = new HashSet<>();
        working = new HashSet<>();
    }

    public void add(Task task) {
        if (task.getLeftPosition() == -1 && task.getRightPosition() == -1) {
            if (queue.size() == 0) {
                queue.add(task);
            } else {
                for (int i = 0; i < queue.size(); i++) {
                    if (task.getLength() > queue.get(i).getLength()) {
                        queue.add(i, task);
                        break;
                    }
                }
                if (!queue.contains(task)) {
                    queue.add(task);
                }
            }
        } else {
            waiting.add(task);
        }
    }

    public void setCompleted(Task task) {
        working.remove(task);
        completed.add(task);

        if (waiting.size() > 0) {
            int completed = task.getPostion();
            for (Task t : waiting) {
                if (completed == t.getLeftPosition()){
                    t.setLeftPosition(-1);
                    waiting.remove(t);
                    add(t);
                    break;
                }else if(completed == t.getRightPosition()){
                    t.setRightPosition(-1);
                    waiting.remove(t);
                    add(t);
                    break;
                }
            }
        }


    }

    public boolean isReady(){
        if (queue.size()==0){
            return false;
        }else {
            return true;
        }
    }

    public boolean isFinished(){
        if(!isReady()&&waiting.size()==0){
            return true;
        }
        else {
            return false;
        }
    }


    public Task getNext() {
        Task task = queue.get(0);
        working.add(task);
        queue.remove(0);
        return task;
    }


    public ArrayList<Task> getQueue() {
        return queue;
    }

    public HashSet<Task> getWaiting() {
        return waiting;
    }

    public HashSet<Task> getCompleted() {
        return completed;
    }
}
