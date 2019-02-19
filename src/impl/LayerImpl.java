package impl;

import Model.Task;
import api.Layer;

public class LayerImpl implements Layer {
    //0 - free
    //-1 - finished working
    //>0 - number of task
    private int status;
    private int number;
    private Task task;

    public LayerImpl(int number) {
        status=0;
        this.number=number;
        task=null;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    private void removeTask(){
        task =null;
    }

    @Override
    public void in(int task) {
        status = task;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public Task out() {
        status=0;
        Task task=this.task;
        removeTask();
        return task;
    }
}
