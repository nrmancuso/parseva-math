import java.lang.reflect.Method;

class MethodNode implements ExpressionNode {
    private Method function;
    private ExpressionNode arguement;

    public Method getFunction() {
        return function;
    }

    public void setFunction(Method function) {
        this.function = function;
    }

    public ExpressionNode getArguement() {
        return arguement;
    }

    public void setArguement(ExpressionNode arguement) {
        this.arguement = arguement;
    }
}
