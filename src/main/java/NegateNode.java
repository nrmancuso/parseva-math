class NegateNode extends InfixExpressionNode {
    private ExpressionNode innerNode;

    public ExpressionNode getInnerNode() {
        return innerNode;
    }

    public void setInnerNode(ExpressionNode innerNode) {
        this.innerNode = innerNode;
    }
}
