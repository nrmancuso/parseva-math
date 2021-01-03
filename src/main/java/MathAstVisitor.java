public abstract class MathAstVisitor<T>  {
    abstract T visit(AdditionNode node);
    abstract T visit(SubtractionNode node);
    abstract T visit(MultiplicationNode node);
    abstract T visit(DivisionNode node);
    abstract T visit(NegateNode node);
    abstract T visit(MethodNode node);
    abstract T visit(NumberNode node);

    public T visit(ExpressionNode node) {
        return switch (node.getClass().getSimpleName()) {
            case "AdditionNode" -> visit((AdditionNode) node);
            case "SubtractionNode" -> visit((SubtractionNode) node);
            case "MultiplicationNode" -> visit((MultiplicationNode) node);
            case "DivisionNode" -> visit((DivisionNode) node);
            case "NegateNode" -> visit((NegateNode) node);
            case "MethodNode" -> visit((MethodNode) node);
            case "NumberNode" -> visit((NumberNode) node);
            default -> throw new IllegalStateException("Unexpected value: " + node.getClass());
        };
    }
}
