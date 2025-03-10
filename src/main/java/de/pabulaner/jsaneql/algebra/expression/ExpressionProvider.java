package de.pabulaner.jsaneql.algebra.expression;

import de.pabulaner.jsaneql.algebra.IU;
import de.pabulaner.jsaneql.schema.Value;
import de.pabulaner.jsaneql.schema.ValueType;

import java.util.List;

public interface ExpressionProvider<TExpression extends Expression> {

    TExpression between(TExpression value, TExpression lower, TExpression upper);

    TExpression binary(TExpression left, TExpression right, BinaryExpression.Operation operation, ValueType type);

    TExpression cast(TExpression value, ValueType type);

    TExpression empty();

    TExpression extract(TExpression value, ExtractExpression.Part part);

    TExpression in(TExpression value, List<TExpression> values);

    TExpression ref(IU iu);

    TExpression substr(TExpression value, TExpression from, TExpression length);

    TExpression unary(TExpression value, UnaryExpression.Operation operation);

    TExpression value(String value, ValueType type);
}
