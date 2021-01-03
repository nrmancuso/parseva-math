import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EvaluateExpressionVisitor extends MathAstVisitor<Double>{

    @Override
    Double visit(AdditionNode node) {
        return visit(node.getLeft()) + visit(node.getRight());
    }

    @Override
    Double visit(SubtractionNode node) {
        return visit(node.getLeft()) - visit(node.getRight());
    }

    @Override
    Double visit(MultiplicationNode node) {
        return visit(node.getLeft()) * visit(node.getRight());
    }

    @Override
    Double visit(DivisionNode node) {
        return visit(node.getLeft()) / visit(node.getRight());
    }

    @Override
    Double visit(NegateNode node) {
        return - visit(node.getInnerNode());
    }

    @Override
    Double visit(MethodNode node) {
        Method mathMethod = node.getFunction();
        Double returnVal = 0.0;
        try {
            returnVal = (Double) mathMethod.invoke(mathMethod.getClass(), visit(node.getArguement()));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    @Override
    Double visit(NumberNode node) {
        return node.getValue();
    }
}
