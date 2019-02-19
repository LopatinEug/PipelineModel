package Model;

public class Task {
    private int length;
    private int leftPosition;
    private int rightPosition;
    private int postion;
    private String left;
    private String right;
    private String value;

    public Task(int length, int leftPosition, int rightPosition, int postion, String left, String right, String value) {
        this.length = length;
        this.leftPosition = leftPosition;
        this.rightPosition = rightPosition;
        this.postion = postion;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public int getLength() {
        return length;
    }

    public int getLeftPosition() {
        return leftPosition;
    }

    public int getRightPosition() {
        return rightPosition;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public String getValue() {
        return value;
    }


    public void setLength(int length) {
        this.length = length;
    }

    public void setLeftPosition(int leftPosition) {
        this.leftPosition = leftPosition;
    }

    public void setRightPosition(int rightPosition) {
        this.rightPosition = rightPosition;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public void setRight(String right) {
        this.right = right;
    }

}
