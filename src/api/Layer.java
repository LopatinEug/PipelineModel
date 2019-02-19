package api;

import Model.Task;

public interface Layer {

    public void in(int task);
    public Task out();

}
